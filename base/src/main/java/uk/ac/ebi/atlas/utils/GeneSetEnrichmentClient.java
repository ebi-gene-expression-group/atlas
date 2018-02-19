package uk.ac.ebi.atlas.utils;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class GeneSetEnrichmentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSetEnrichmentClient.class);

    private final RestTemplate restTemplate;
    private static final String urlPattern = "https://www.ebi.ac.uk/fg/gsa/api/tsv/getOverlappingComparisons/{0}/{1}";
    private static final String [] expectedHeader =("EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\t" +
            "ADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL").split("\t");

    @Inject
    public GeneSetEnrichmentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pair<Optional<String>, Optional<JsonArray>> fetchEnrichedGenes(Species species,
                                                                          Collection<String> bioentityIdentifiers) {
        try {
            Optional<String> maybeError = validateInput(species, bioentityIdentifiers);
            return maybeError.isPresent() ?
                    Pair.of(maybeError, Optional.empty()) :
                    formatResponse(fetchResponse(species, bioentityIdentifiers));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Pair.of(Optional.of("Exception occurred:\n" + e.getMessage()), Optional.empty());
        }
    }

    public static boolean isSuccess(Pair<Optional<String>, Optional<JsonArray>> result) {
        return !result.getLeft().isPresent();
    }

    //either error message or result
    public Pair<Optional<String>, Optional<JsonArray>> formatResponse(List<String[]> lines) {
        if(lines.isEmpty()) {
            return Pair.of(Optional.of("Result empty!"),
                    Optional.empty());
        }
        else if (lines.size() == 1){
            return Pair.of(Optional.of("Error: "+Joiner.on("\t").join(lines.get(0))),
                    Optional.empty());
        }
        else if(!Arrays.deepEquals(expectedHeader, lines.get(0))){
            return Pair.of(Optional.of("Header not as expected: " + Joiner.on("\t").join(lines.get(0))),
                    Optional.empty());
        }
        else if(! linesHaveCorrectDimensions(lines)){
            return Pair.of(Optional.of("Data malformed, expected a matrix" ),
                    Optional.empty());
        }
        else {
            return Pair.of(Optional.empty(),
                    Optional.of(formatLines(lines.subList(1, lines.size()))));
        }
    }

    private List<String[]> fetchResponse(Species species, Collection<String> bioentityIdentifiers) {
        Reader responseStringReader = new StringReader(
                restTemplate.getForObject(
                        MessageFormat.format(
                                urlPattern,
                                species.getEnsemblName().toLowerCase(),
                                Joiner.on(" ").join(bioentityIdentifiers)),
                        String.class));

        try (TsvStreamer tsvStreamer = new TsvStreamer(responseStringReader)) {
            return tsvStreamer.get().collect(Collectors.toList());
        }
    }

    private boolean linesHaveCorrectDimensions(List<String[]> lines) {
        for (String[] line : lines) {
            if(line.length != expectedHeader.length && line.length +1 != expectedHeader.length) {
                //missing COMPARISON_TITLE from data lines, we're working around it
                return false;
            }
        }
        return true;
    }

    private JsonArray formatLines(List<String[]> lines) {
        JsonArray result = new JsonArray();
        for (String [] line : lines) {
            result.add(formatLine(line));
        }
        return result;
    }

    //This is the format required by the data tables UI
    private JsonObject formatLine(String[] line) {
        JsonObject result = new JsonObject();

        result.addProperty("experiment_accession", line[0]);
        result.addProperty("comparison_id", line[1]);
        result.addProperty("p-value", Double.parseDouble(line[2]));
        result.addProperty("observed", Integer.parseInt(line[3]));
        result.addProperty("expected", Double.parseDouble(line[4]));
        result.addProperty("adjusted p-value", Double.parseDouble(line[5]));
        result.addProperty("effect size", parseOrNaN(line[6]));
        result.add("comparison_title", new JsonObject()); // enriched later
        result.addProperty("experiment", ""); // enriched later

        // line 7 offered by the API is the url - we reconstruct that later
        return result;
    }

    //Effect size is observed/expected, and R says 1/0 is Inf
    private Double parseOrNaN(String s){
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e){
            return Double.NaN;
        }
    }

    private Optional<String> validateInput(Species species, Collection<String> bioentityIdentifiers) {
        Set<String> errors = new HashSet<>();
        if (species.isUnknown()) {
            errors.add("Unknown species: " + species.getName());
        }
        if (bioentityIdentifiers.size() < 10) {
            errors.add("Please use at least 10 gene identifiers, was: " + bioentityIdentifiers.size());
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(Joiner.on("\n").join(errors));
    }

}
