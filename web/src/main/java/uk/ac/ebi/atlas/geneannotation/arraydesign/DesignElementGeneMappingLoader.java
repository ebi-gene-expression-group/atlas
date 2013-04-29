package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.geneannotation.AnnotationLoader;
import uk.ac.ebi.atlas.geneannotation.AnnotationMappingExtractor;
import uk.ac.ebi.atlas.utils.DesignElementKeyGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Named("designElementLoader")
public class DesignElementGeneMappingLoader extends AnnotationLoader{

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String serverURL;

    @Inject
    public DesignElementGeneMappingLoader(AnnotationEnvironment annotationEnvironment, AnnotationMappingExtractor annotationMappingExtractor) {
        super(annotationEnvironment, annotationMappingExtractor);

    }

    @Override
    protected String getAnnotationServerUrl() {
        return serverURL;
    }

    @Override
    protected ConcurrentMap<String, String> getDestinationMap(AnnotationEnvironment annotationEnvironment) {
        return annotationEnvironment.geneDesignElementsToGeneNames();
    }

    @Override
    protected MapTransactionWorker getTransactionWorker(ConcurrentMap<String, String> dest, Map<String, String> src, final String annotatedSubject) {
        return new MapTransactionWorker(src, dest) {
            @Override
            protected String generateKey(String key) {
                return DesignElementKeyGenerator.getKey(annotatedSubject, key);
            }
        };
    }

}
