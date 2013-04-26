package uk.ac.ebi.atlas.geneannotation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public abstract class AnnotationLoader {
    private TransactionRunner transactionRunner;

       private AnnotationEnvironment annotationEnvironment;

       @Value("#{configuration['de.mapping.gxa.server.url']}")
       private String serverURL;

       private RestTemplate restTemplate;

       @Inject
       public AnnotationLoader(AnnotationEnvironment annotationEnvironment) {
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


       void loadAnnotations(TransactionWorker transactionWorker) {

           transactionRunner = annotationEnvironment.getTransactionRunner();
           try {

               transactionRunner.run(transactionWorker);

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

           TransactionWorker transactionWorker = new MapTransactionWorker(annotationEnvironment.geneDesignElementsToGeneNames(),
                   designElements);


           loadAnnotations(transactionWorker);


           turnOnReadOnly();

       }

       protected Map<String, String> convertJson(String jsonString) {
           Gson gson = new Gson();
           Type mapType = new TypeToken<Map<String, String>>() {
           }.getType();
           Map<String, String> allMap = gson.fromJson(jsonString, mapType);
           return gson.fromJson(allMap.get("exportText"), mapType);
       }


}
