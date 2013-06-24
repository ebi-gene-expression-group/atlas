package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public abstract class AnnotationLoader {

    private AnnotationEnvironment annotationEnvironment;

    private RestAnnotationMappingExtractor annotationMappingExtractor;

    public AnnotationLoader(AnnotationEnvironment annotationEnvironment, RestAnnotationMappingExtractor annotationMappingExtractor) {
        this.annotationEnvironment = annotationEnvironment;
        this.annotationMappingExtractor = annotationMappingExtractor;
    }

    public void loadMappings(Collection<String> annotatedSubjects) {
        turnOffReadonly();

        ConcurrentMap<String, String> destinationMap = getDestinationMap(annotationEnvironment);
        destinationMap.clear();

        for (String annotatedSubject : annotatedSubjects) {
            loadMappingsToBerkeley(annotatedSubject);
        }

    }

    public void loadMappings(String annotatedSubject) {

        loadMappingsToBerkeley(annotatedSubject);

    }

    private void turnOffReadonly() {
        annotationEnvironment.close();
        annotationEnvironment.initBerkeleyDatabase(false);
    }

    private void turnOnReadOnly() {
        annotationEnvironment.close();
        annotationEnvironment.initBerkeleyReadonly();
    }

    protected void loadMappingsToBerkeley(String annotatedSubject) {

        Map<String, String> annotations = annotationMappingExtractor.extractAnnotationsMap(getAnnotationServerUrl(), annotatedSubject);

        turnOffReadonly();

        ConcurrentMap<String, String> destinationMap = getDestinationMap(annotationEnvironment);

        TransactionWorker transactionWorker = getTransactionWorker(destinationMap, annotations, annotatedSubject);

        loadAnnotations(transactionWorker);

        turnOnReadOnly();

    }

    void loadAnnotations(TransactionWorker transactionWorker) {

        TransactionRunner transactionRunner = annotationEnvironment.getTransactionRunner();
        try {

            transactionRunner.run(transactionWorker);

        } catch (Exception e) {
            throw new IllegalStateException("Exception while loading annotations.", e);
        }
    }

    protected abstract String getAnnotationServerUrl();

    protected abstract ConcurrentMap<String, String> getDestinationMap(AnnotationEnvironment annotationEnvironment);

    protected abstract MapTransactionWorker getTransactionWorker(ConcurrentMap<String, String> dest, Map<String, String> src, String annotatedSubject);
}
