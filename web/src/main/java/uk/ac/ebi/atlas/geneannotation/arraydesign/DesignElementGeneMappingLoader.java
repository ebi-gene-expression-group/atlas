package uk.ac.ebi.atlas.geneannotation.arraydesign;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sleepycat.collections.TransactionRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.berkeley.ObjectValueTransactionWorker;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.Map;

@Named("designElementLoader")
public class DesignElementGeneMappingLoader {
    private TransactionRunner transactionRunner;

    private AnnotationEnvironment annotationEnvironment;

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String serverURL;

    private RestTemplate restTemplate;

    @Inject
    public DesignElementGeneMappingLoader(AnnotationEnvironment annotationEnvironment, RestTemplate restTemplate) {
        this.annotationEnvironment = annotationEnvironment;
        this.restTemplate = restTemplate;
    }

    private void turnOffReadonly() {
        this.annotationEnvironment.close();
        this.annotationEnvironment.initBerkeleyDatabase(false);
    }

    private void turnOnReadOnly() {
        this.annotationEnvironment.close();
        this.annotationEnvironment.initBerkeleyReadonly();
    }


    protected void loadAnnotations(Map<String, String> designElements,
                                   ObjectValueTransactionWorker transactionWorker) {

        String[] row;

        transactionRunner = annotationEnvironment.getTransactionRunner();

        for (Map.Entry<String, String> stringStringEntry : designElements.entrySet()) {
            row = stringStringEntry.
        }

        try {
            while ((row = annotationsInputStream.readNext()) != null) {
                transactionRunner.run(transactionWorker.setRow(row));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Exception while loading annotations.", e);
        }
    }

    public void loadMappings(String arrayDesign) {

        String jsonString = restTemplate.getForObject(serverURL, String.class, arrayDesign);

        Map<String, String> designElements = convertJson(jsonString);


        turnOffReadonly();

        ObjectValueTransactionWorker<String> transactionWorker = new ObjectValueTransactionWorker<String>(annotationEnvironment.geneDesignElementsToGeneNames()) {

            @Override
            protected String getValue() {
                return null;
            }

            @Override
            protected String getKey() {
                return null;
            }

            @Override
            protected boolean isEmptyValue(String value) {
                return false;
            }
        };


        loadAnnotations(annotationsInputStream, transactionWorker);


        turnOnReadOnly();

    }

    private Map<String, String> convertJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(jsonString, mapType);
    }


}
