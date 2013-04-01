package uk.ac.ebi.atlas.geneannotation.arraydesign;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sleepycat.collections.TransactionRunner;
import org.apache.commons.lang.StringUtils;
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

    public void loadMappings(String arrayDesign) {

        String jsonString = restTemplate.getForObject(serverURL, String.class, arrayDesign);

        Map<String, String> designElements = convertJson(jsonString);


        turnOffReadonly();

        ObjectValueTransactionWorker<String, Map.Entry<String, String>> transactionWorker = getStringEntryObjectValueTransactionWorker();


        loadAnnotations(designElements, transactionWorker);


        turnOnReadOnly();

    }

    protected ObjectValueTransactionWorker<String, Map.Entry<String, String>> getStringEntryObjectValueTransactionWorker() {
        return new ObjectValueTransactionWorker<String, Map.Entry<String, String>>() {
                @Override
                protected String getValue() {
                    return getRow().getValue();
                }

                @Override
                protected String getKey() {
                    return getRow().getKey();
                }

                @Override
                protected boolean isEmptyValue(String value) {
                    return StringUtils.isBlank(value);
                }
            };
    }

    protected Map<String, String> convertJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();
        return gson.fromJson(jsonString, mapType);
    }


}
