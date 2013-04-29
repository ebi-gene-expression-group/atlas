package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public abstract class AnnotationLoader {

    private AnnotationEnvironment annotationEnvironment;

    private AnnotationMappingExtractor annotationMappingExtractor;

    public AnnotationLoader(AnnotationEnvironment annotationEnvironment, AnnotationMappingExtractor annotationMappingExtractor) {
        this.annotationEnvironment = annotationEnvironment;
        this.annotationMappingExtractor = annotationMappingExtractor;
    }

    public void loadMappings(Collection<String> annotatedSubjects) {
        for (String annotatedSubject : annotatedSubjects) {
            loadMappings(annotatedSubject, true);
        }

    }

    public void loadMappings(String annotatedSubject) {

        loadMappings(annotatedSubject, false);

    }

    private void turnOffReadonly() {
        annotationEnvironment.close();
        annotationEnvironment.initBerkeleyDatabase(false);
    }

    private void turnOnReadOnly() {
        annotationEnvironment.close();
        annotationEnvironment.initBerkeleyReadonly();
    }

    protected void loadMappings(String annotatedSubject, boolean removeOldMappings) {

        Map<String, String> annotations = annotationMappingExtractor.extractAnnotationsMap(getAnnotationServerUrl(), annotatedSubject);

        turnOffReadonly();

        ConcurrentMap<String, String> destinationMap = getDestinationMap(annotationEnvironment);

        if (removeOldMappings) {
            destinationMap.clear();
        }

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
