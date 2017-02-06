package uk.ac.ebi.atlas.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Named
public class GeneSetEnrichmentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSetEnrichmentClient.class);


    private final RestTemplate restTemplate;
    private static final String urlPattern = "http://www.ebi.ac.uk/fg/gsa/api/tsv/getOverlappingComparisons/{0}/{1}";
    private static final String [] expectedHeader =("EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED " +
            "P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL").split("\t");



    @Inject
    public GeneSetEnrichmentClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    List<String[]> fetchResponse(Species species, Collection<String>
            bioentityIdentifiers) {
        return new TsvReaderImpl(
                new StringReader(
                        restTemplate.getForObject(
                                MessageFormat.format(urlPattern,
                                        species.getEnsemblName().toLowerCase(),
                                        Joiner.on(" ").join(bioentityIdentifiers)), String.class))).readAll();
    }

    private boolean linesHaveCorrectDimensions(List<String[]> lines){
        for(String[] line: lines){
            if(line.length != expectedHeader.length &&  line.length +1 != expectedHeader.length){
                //missing COMPARISON_TITLE from data lines, we're working around it
                return false;
            }
        }
        return true;
    }
    //either error message or result
    Pair<Optional<String>, Optional<JsonArray>> formatResponse(List<String[]> lines){
        if(lines.isEmpty()){
            return Pair.of(Optional.of("Result empty!"),
                    Optional.<JsonArray>absent());
        }
        else if (lines.size() == 1){
            return Pair.of(Optional.of("Error: "+Joiner.on("\t").join(lines.get(0))),
                    Optional.<JsonArray>absent());
        }
        else if(!Arrays.deepEquals(expectedHeader, lines.get(0))){
            return Pair.of(Optional.of("Header not as expected: "+Joiner.on("\t").join(lines.get(0))),
                    Optional.<JsonArray>absent());
        }
        else if(! linesHaveCorrectDimensions(lines)){
            return Pair.of(Optional.of("Data malformed, expected a matrix" ),
                    Optional.<JsonArray>absent());
        }
        else {
            return Pair.of(Optional.<String>absent(),
                    Optional.of(formatLines(lines.subList(1, lines.size()))));
        }
    }

    JsonArray formatLines(List<String[]> lines){
        JsonArray result = new JsonArray();
        for(String [] line: lines){
            result.add(formatLine(line));
        }
        return result;
    }

    //This is the format required by the data tables UI
    JsonObject formatLine(String[] line){
        JsonObject result = new JsonObject();

        result.addProperty("experiment_accession",
                line[0]);
        result.addProperty("comparison_id",
                line[1]);
        result.addProperty("p-value",
                Double.parseDouble(line[2]));
        result.addProperty("observed",
                Double.parseDouble(line[3]));
        result.addProperty("expected",
                Double.parseDouble(line[4]));
        result.addProperty("adjusted p-value",
                Double.parseDouble(line[5]));
        result.addProperty("effect size",
                Double.parseDouble(line[6]));
        result.addProperty("comparison_title", ""); // enriched later
        result.add("experiment", new JsonObject()); // enriched later

        // line 7 offered by the API is the url - we reconstruct that later
        return result;
    }

    Optional<String> validateInput(Species species, Collection<String>
            bioentityIdentifiers){
        String errorMessage = "";
        if(species.isUnknown()){
            errorMessage += "Unknown species: "+species.getName();
        }
        if(bioentityIdentifiers.size() < 10){
            errorMessage += "Too few identifiers: "+Joiner.on(" ").join(bioentityIdentifiers);
        }

        return errorMessage == "" ? Optional.<String>absent() : Optional.of(errorMessage);
    }

    public Pair<Optional<String>, Optional<JsonArray>> fetchEnrichedGenes(Species species, Collection<String>
            bioentityIdentifiers){
        try{
            Optional<String> maybeError = validateInput(species,bioentityIdentifiers);
            return maybeError.isPresent()?
                    Pair.of(maybeError, Optional.<JsonArray>absent()) :
                    formatResponse(fetchResponse(species, bioentityIdentifiers)) ;

        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return Pair.of(Optional.of("Exception occurred: \n"+e.getMessage()),
                    Optional.<JsonArray>absent());
        }
    }

    public static boolean isSuccess(Pair<Optional<String>, Optional<JsonArray>> result){
        return !result.getLeft().isPresent();
    }

}
