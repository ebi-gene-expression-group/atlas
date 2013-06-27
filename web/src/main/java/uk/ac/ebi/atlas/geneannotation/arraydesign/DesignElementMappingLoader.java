package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.FileAnnotationMappingExtractor;
import uk.ac.ebi.atlas.geneannotation.RestAnnotationMappingExtractor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class DesignElementMappingLoader {

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String gxaMappingUrl;

    @Value("#{configuration['de.mirna.mapping.path.template']}")
    private String micrornaMappingPath;


    private ArrayDesignDao arrayDesignDao;

    private RestAnnotationMappingExtractor restAnnotationMappingExtractor;
    private FileAnnotationMappingExtractor fileAnnotationMappingExtractor;

    @Inject
    public DesignElementMappingLoader(ArrayDesignDao arrayDesignDao, RestAnnotationMappingExtractor restAnnotationMappingExtractor, FileAnnotationMappingExtractor fileAnnotationMappingExtractor) {
        this.arrayDesignDao = arrayDesignDao;
        this.restAnnotationMappingExtractor = restAnnotationMappingExtractor;
        this.fileAnnotationMappingExtractor = fileAnnotationMappingExtractor;
    }

    public void loadMappings(String annotatedSubject, ArrayDesignType type) {

        clean(annotatedSubject);

        Map<String, String> annotations;
        if (type.equals(ArrayDesignType.MICRO_ARRAY)) {
            annotations = restAnnotationMappingExtractor.extractAnnotationsMap(gxaMappingUrl, annotatedSubject);
        } else {
            annotations = fileAnnotationMappingExtractor.extractAnnotationsMap(micrornaMappingPath, annotatedSubject);
        }

        saveMappings(annotations, annotatedSubject, type.getName());

    }

    protected void clean(String annotatedSubject) {
        arrayDesignDao.deleteMappings(annotatedSubject);
    }

    protected void saveMappings(Map<String, String> mappings, String annotatedSubject, String type) {
        arrayDesignDao.saveMappings(mappings, annotatedSubject, type);
    }
}
