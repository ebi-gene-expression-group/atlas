package uk.ac.ebi.atlas.experimentimport.sdrf;

import org.springframework.util.StringUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
    public Map<String, Set<String>> parseHeader(String experimentAccession) {
        try (TsvStreamer sdrfStreamer = dataFileHub.getExperimentFiles(experimentAccession).sdrf.get()) {
            Pattern pattern = Pattern.compile("(.*?)(\\[)(.*?)(].*)");

            Map<String, Set<String>> headers = new HashMap<>();
            Arrays.stream(
                    sdrfStreamer
                            .get()
                            .findFirst()
                            .orElse(new String[0]))
                    .filter(x -> x.toLowerCase().contains("characteristic") || x.toLowerCase().contains("factor"))
                    .forEach(header -> {
                        Matcher matcher = pattern.matcher(header);
                        if (matcher.matches()) {
                            String headerType = StringUtils.trimAllWhitespace(matcher.group(1)).toLowerCase(); // is the header a characteristic or a factor value?
                            String headerValue = matcher.group(3).trim().toLowerCase(); // what is the title of the header? e.g. organism, age, etc
                            if (headers.containsKey(headerType)) {
                                headers.get(headerType).add(headerValue);
                            } else {
                                Set<String> headerValues = new LinkedHashSet<>();
                                headerValues.add(headerValue);
                                headers.put(headerType, headerValues);
                            }
                        }
                    });
            return headers;
        }
    }
}
