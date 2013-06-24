package uk.ac.ebi.atlas.geneannotation;

import java.util.Map;

public interface AnnotationMappingExtractor {
    Map<String, String> extractAnnotationsMap(String serverUrl, String... urlVariables);
}
