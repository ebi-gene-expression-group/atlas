package uk.ac.ebi.atlas.geneannotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityNameLoader {

    @Value("#{configuration['gene.mapping.gxa.server.url']}")
    private String serverURL;

    private BioEntityAnnotationDao bioEntityAnnotationDao;

    private RestAnnotationMappingExtractor annotationMappingExtractor;

    @Inject
    public BioEntityNameLoader(BioEntityAnnotationDao bioEntityAnnotationDao, RestAnnotationMappingExtractor annotationMappingExtractor) {
        this.bioEntityAnnotationDao = bioEntityAnnotationDao;
        this.annotationMappingExtractor = annotationMappingExtractor;
    }

    public void loadMappings(String annotatedSubject) {
        Map<String, String> annotations = annotationMappingExtractor.extractAnnotationsMap(serverURL, annotatedSubject);
        saveMappings(annotations, annotatedSubject);
    }

    protected void saveMappings(Map<String, String> mappings, String annotatedSubject) {
        bioEntityAnnotationDao.deleteAnnotations(annotatedSubject, "gene");
        bioEntityAnnotationDao.saveAnnotations(mappings, annotatedSubject, "gene");
    }
}
