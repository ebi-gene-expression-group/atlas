package uk.ac.ebi.atlas.experimentimport.sdrf;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.StringUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named
public class SdrfParser {

    private final DataFileHub dataFileHub;

    @Inject
    public SdrfParser(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    /**
     * Returns a map containing the header values for characteristics and factors, maintaining the same order in
     * which they appear in the sdrf file.
     */
    public Map<String, Set<String>> parseHeader(String experimentAccession) {
        try (TsvStreamer sdrfStreamer = dataFileHub.getExperimentFiles(experimentAccession).sdrf.get()) {
            // Headers of interest are of the form HeaderType[HeaderValue]
            Pattern pattern = Pattern.compile("(.*?)(\\[)(.*?)(].*)");

            return Arrays.stream(
                    sdrfStreamer
                            .get()
                            .findFirst()
                            .orElse(new String[0]))
                    .filter(x -> x.toLowerCase().contains("characteristic") || x.toLowerCase().contains("factor"))
                    .filter(x -> x.matches(pattern.toString()))
                    .map(header -> {
                        Matcher matcher = pattern.matcher(header);
                        String headerType = "";
                        String headerValue = "";
                        if (matcher.matches()) {
                            // The header is either a characteristic or a factor value
                            headerType = StringUtils.trimAllWhitespace(matcher.group(1)).toLowerCase();
                            // The title of the header is a type of metadata, such as organism, age, etc
                            headerValue = matcher.group(3).trim().toLowerCase();
                        }
                        // The headerType and headerValue will never be empty so there is no need to handle empty values
                        return new ImmutablePair<>(headerType, headerValue);
                    })
                    .collect(
                            Collectors.groupingBy(
                                    ImmutablePair::getLeft,
                                    Collectors.mapping(
                                            ImmutablePair::getRight,
                                            Collectors.toCollection(LinkedHashSet::new))));
        }
    }
}
