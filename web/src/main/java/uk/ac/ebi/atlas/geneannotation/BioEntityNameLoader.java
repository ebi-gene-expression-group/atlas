package uk.ac.ebi.atlas.geneannotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityNameLoader extends H2AnnotationLoader {

    @Value("#{configuration['gene.mapping.gxa.server.url']}")
    private String serverURL;

    private BioEntityAnnotationDao bioEntityAnnotationDao;


    @Inject
    public BioEntityNameLoader(BioEntityAnnotationDao bioEntityAnnotationDao) {
        this.bioEntityAnnotationDao = bioEntityAnnotationDao;
    }

    @Override
    protected String getAnnotationServerUrl() {
        return serverURL;
    }

    @Override
    protected void clean(String annotatedSubject) {

    }

    @Override
    protected void saveMappings(Map<String, String> mappings, String annotatedSubject) {
        bioEntityAnnotationDao.saveAnnotations(mappings, annotatedSubject);

    }
}
