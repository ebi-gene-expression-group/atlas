package uk.ac.ebi.atlas.geneannotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class DesignElementMappingLoader extends H2AnnotationLoader {

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String serverURL;

    private ArrayDesignDao arrayDesignDao;


    @Inject
    public DesignElementMappingLoader(ArrayDesignDao arrayDesignDao) {
        this.arrayDesignDao = arrayDesignDao;
    }

    @Override
    protected String getAnnotationServerUrl() {
        return serverURL;
    }

    @Override
    protected void clean(String annotatedSubject) {
        arrayDesignDao.deleteMappings(annotatedSubject);
    }

    @Override
    protected void saveMappings(Map<String, String> mappings, String annotatedSubject) {
        arrayDesignDao.saveMappings(mappings, annotatedSubject);
    }
}
