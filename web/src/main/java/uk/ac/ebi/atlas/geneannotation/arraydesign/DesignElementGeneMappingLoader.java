package uk.ac.ebi.atlas.geneannotation.arraydesign;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sleepycat.collections.TransactionRunner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.berkeley.ObjectValueTransactionWorker;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.utils.DesignElementKeyGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.Collection;
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
                                   ObjectValueTransactionWorker<String, Map.Entry<String, String>> transactionWorker) {

        transactionRunner = annotationEnvironment.getTransactionRunner();
        try {
            for (Map.Entry<String, String> deName : designElements.entrySet()) {
                transactionRunner.run(transactionWorker.setRow(deName));
            }

        } catch (Exception e) {
            throw new IllegalStateException("Exception while loading annotations.", e);
        }
    }


    public void loadMappings(String arrayDesignAccession) {

       loadMappings(arrayDesignAccession, false);

    }

    public void loadMappings(Collection<String> arrayDesignAccessions) {
        for (String arrayDesignAccession : arrayDesignAccessions) {
            loadMappings(arrayDesignAccession, true);
        }

    }

    public void loadMappings(String arrayDesignAccession, boolean removeOldMappings) {

        String jsonString = restTemplate.getForObject(serverURL, String.class, arrayDesignAccession);

        Map<String, String> designElements = convertJson(jsonString);

        turnOffReadonly();

        if (removeOldMappings) {
            annotationEnvironment.geneDesignElementsToGeneNames().clear();
        }

        ObjectValueTransactionWorker<String, Map.Entry<String, String>> transactionWorker = getTransactionWorker(arrayDesignAccession);


        loadAnnotations(designElements, transactionWorker);


        turnOnReadOnly();

    }

    protected ObjectValueTransactionWorker<String, Map.Entry<String, String>> getTransactionWorker(final String arrayDesignAccession) {
        return new ObjectValueTransactionWorker<String, Map.Entry<String, String>>(annotationEnvironment.geneDesignElementsToGeneNames()) {
            @Override
            protected String getValue() {
                return getRow().getValue();
            }

            @Override
            protected String getKey() {
                return DesignElementKeyGenerator.getKey(arrayDesignAccession, getRow().getKey());
            }

            @Override
            protected boolean isEmptyValue(String value) {
                return StringUtils.isBlank(value);
            }
        };
    }

    protected Map<String, String> convertJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> allMap = gson.fromJson(jsonString, mapType);
        return gson.fromJson(allMap.get("exportText"), mapType);
    }


}
