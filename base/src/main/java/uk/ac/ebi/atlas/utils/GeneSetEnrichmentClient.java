package uk.ac.ebi.atlas.utils;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.controllers.NoStatisticalSignificanceException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class GeneSetEnrichmentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSetEnrichmentClient.class);

    private final RestTemplate restTemplate;
    static final String GSA_URL_PATTERN = "https://www.ebi.ac.uk/fg/gsa/api/tsv/getOverlappingComparisons/{0}/{1}";
    private static final String[] EXPECTED_HEADER =
            ("EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\t" +
             "ADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL").split("\t");

    @Inject
    public GeneSetEnrichmentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pair<Optional<Exception>, Optional<JsonArray>> fetchEnrichedGenes(String speciesName,
                                                                             Collection<String> bioentityIdentifiers) {
        try {
            Pair<Optional<String>, Optional<JsonArray>> errorOrResponse =
                    formatResponse(fetchResponse(speciesName, bioentityIdentifiers));
            return errorOrResponse.getLeft().isPresent() ?
                    Pair.of(errorOrResponse.getLeft().map(RuntimeException::new), Optional.empty()) :
                    Pair.of(Optional.empty(), errorOrResponse.getRight());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Pair.of(Optional.of(e), Optional.empty());
        }
    }

    // Either error message or result
    private Pair<Optional<String>, Optional<JsonArray>> formatResponse(List<String[]> lines) {
        if (lines.isEmpty()) {
            return Pair.of(Optional.of("Result empty!"),
                    Optional.empty());
        } else if (lines.size() == 1) {
            return Pair.of(
                    Optional.of("Error: " + Joiner.on("\t").join(lines.get(0))),
                    Optional.empty());
        } else if (!Arrays.deepEquals(EXPECTED_HEADER, lines.get(0))) {
            return Pair.of(
                    Optional.of("Header not as expected: " + Joiner.on("\t").join(lines.get(0))),
                    Optional.empty());
        } else if (!linesHaveCorrectDimensions(lines)) {
            return Pair.of(
                    Optional.of("Data malformed, expected a matrix"),
                    Optional.empty());
        } else {
            return Pair.of(
                    Optional.empty(),
                    Optional.of(formatLines(lines.subList(1, lines.size()))));
        }
    }

    private List<String[]> fetchResponse(String speciesName, Collection<String> bioentityIdentifiers) {
        try {
            String response = restTemplate.getForObject(
                    MessageFormat.format(
                            GSA_URL_PATTERN,
                            speciesName,
                            Joiner.on(" ").join(bioentityIdentifiers)),
                    String.class);

            Reader responseStringReader = new StringReader(response);

            try (TsvStreamer tsvStreamer = new TsvStreamer(responseStringReader)) {
                return tsvStreamer.get().collect(Collectors.toList());
            }
        } catch (RestClientException e) {
            throw new NoStatisticalSignificanceException(
                    String.format(
                            "No significant contrasts found. Try adding more than %d gene identifiers.",
                            bioentityIdentifiers.size()));
        }
    }

    private boolean linesHaveCorrectDimensions(List<String[]> lines) {
        for (String[] line : lines) {
            if (line.length != EXPECTED_HEADER.length && line.length + 1 != EXPECTED_HEADER.length) {
                //missing COMPARISON_TITLE from data lines, we're working around it
                return false;
            }
        }
        return true;
    }

    private JsonArray formatLines(List<String[]> lines) {
        JsonArray result = new JsonArray();
        for (String[] line : lines) {
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
        result.addProperty("adjusted_p-value", Double.parseDouble(line[5]));
        // Handle NaN to produce valid JSON
        if (Double.isNaN(parseOrNaN(line[6]))) {
            result.addProperty("effect_size", "NaN");
        } else {
            result.addProperty("effect_size", parseOrNaN(line[6]));
        }

        result.add("comparison_title", new JsonObject()); // enriched later
        result.addProperty("experiment", ""); // enriched later

        // line 7 offered by the API is the url - we reconstruct that later
        return result;
    }

    //Effect size is observed/expected, and R says 1/0 is Inf
    private Double parseOrNaN(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

}
