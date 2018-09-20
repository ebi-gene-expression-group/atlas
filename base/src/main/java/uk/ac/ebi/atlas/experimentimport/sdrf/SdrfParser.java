package uk.ac.ebi.atlas.experimentimport.sdrf;

import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
public class SdrfParser {

    private final DataFileHub dataFileHub;

    @Inject
    public SdrfParser(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    // Returns a map containing the header values for characteristics and factors
    public Map<String, List<String>> parseHeader(String experimentAccession) {
        try (TsvStreamer sdrfStreamer = dataFileHub.getExperimentFiles(experimentAccession).sdrf.get()) {
            Pattern pattern = Pattern.compile("(.*?)(\\[)(.*?)(].*)");

            Map<String, List<String>> headers = new HashMap<>();
            Arrays.stream(
                    sdrfStreamer
                            .get()
                            .findFirst()
                            .orElse(new String[0]))
                    .filter(x -> x.toLowerCase().contains("characteristic") || x.toLowerCase().contains("factor"))
                    .forEach(header -> {
                        Matcher matcher = pattern.matcher(header);
                        if (matcher.matches()) {
                            String headerType = matcher.group(1).trim().toLowerCase(); // is the header a characteristic or a factor value?
                            String headerValue = matcher.group(3); // what is the title of the header? e.g. organism, age, etc
                            if (headers.containsKey(headerType)) {
                                headers.get(headerType).add(headerValue);
                            } else {
                                List<String> headerValues = new ArrayList<>();
                                headerValues.add(headerValue);
                                headers.put(headerType, headerValues);
                            }
                        }
                    });
            return headers;
        }
    }
}
