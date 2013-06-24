package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.RestAnnotationMappingExtractor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class DesignElementMappingLoader{

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String serverURL;

    private ArrayDesignDao arrayDesignDao;

    private RestAnnotationMappingExtractor annotationMappingExtractor;

    @Inject
    public DesignElementMappingLoader(ArrayDesignDao arrayDesignDao, RestAnnotationMappingExtractor annotationMappingExtractor) {
        this.arrayDesignDao = arrayDesignDao;
        this.annotationMappingExtractor = annotationMappingExtractor;
    }

    public void loadMappings(String annotatedSubject, String type) {

        clean(annotatedSubject);

        Map<String, String> annotations = annotationMappingExtractor.extractAnnotationsMap(serverURL, annotatedSubject);

        saveMappings(annotations, annotatedSubject, type);

    }

    protected void clean(String annotatedSubject) {
        arrayDesignDao.deleteMappings(annotatedSubject);
    }

    protected void saveMappings(Map<String, String> mappings, String annotatedSubject, String type) {
        arrayDesignDao.saveMappings(mappings, annotatedSubject, type);
    }
}
