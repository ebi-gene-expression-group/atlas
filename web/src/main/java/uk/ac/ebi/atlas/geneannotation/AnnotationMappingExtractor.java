package uk.ac.ebi.atlas.geneannotation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.Map;

@Named("mappingExtractor")
public class AnnotationMappingExtractor {

    private RestTemplate restTemplate;

    @Inject
    public AnnotationMappingExtractor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> extractAnnotationsMap(String serverUrl, String... urlVariables) {

        String jsonString = restTemplate.getForObject(serverUrl, String.class, urlVariables);

        return convertJson(jsonString);
    }

    protected Map<String, String> convertJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> allMap = gson.fromJson(jsonString, mapType);
        return gson.fromJson(allMap.get("exportText"), mapType);
    }

}
