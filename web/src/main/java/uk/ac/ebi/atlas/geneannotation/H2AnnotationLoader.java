package uk.ac.ebi.atlas.geneannotation;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

public abstract class H2AnnotationLoader {


    private AnnotationMappingExtractor annotationMappingExtractor;


    @Inject
    public void setAnnotationMappingExtractor(AnnotationMappingExtractor annotationMappingExtractor) {
        this.annotationMappingExtractor = annotationMappingExtractor;
    }

    public void loadMappings(Collection<String> annotatedSubjects) {

        for (String annotatedSubject : annotatedSubjects) {
            loadMappings(annotatedSubject);
        }

    }

    public void loadMappings(String annotatedSubject) {

        clean(annotatedSubject);

        Map<String, String> annotations = annotationMappingExtractor.extractAnnotationsMap(getAnnotationServerUrl(), annotatedSubject);


        saveMappings(annotations, annotatedSubject);

    }

    protected abstract String getAnnotationServerUrl();

    protected abstract void clean(String annotatedSubject);

    protected abstract void saveMappings(Map<String, String> mappings, String annotatedSubject);
}
