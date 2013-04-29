package uk.ac.ebi.atlas.geneannotation;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Named("geneNameLoader")
public class GeneNamesLoader extends AnnotationLoader{

    @Value("#{configuration['gene.mapping.gxa.server.url']}")
    private String serverURL;

    @Inject
    public GeneNamesLoader(AnnotationEnvironment annotationEnvironment, AnnotationMappingExtractor annotationMappingExtractor) {
        super(annotationEnvironment, annotationMappingExtractor);

    }

    @Override
    protected String getAnnotationServerUrl() {
        return serverURL;
    }

    @Override
    protected ConcurrentMap<String, String> getDestinationMap(AnnotationEnvironment annotationEnvironment) {
        return annotationEnvironment.geneNames();
    }

    @Override
    protected MapTransactionWorker getTransactionWorker(ConcurrentMap<String, String> dest, Map<String, String> src, String annotatedSubject) {
        return new MapTransactionWorker(src, dest);
    }

}
