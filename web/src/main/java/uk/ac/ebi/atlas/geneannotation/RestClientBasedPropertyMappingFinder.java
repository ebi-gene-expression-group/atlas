/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneannotation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.Map;

@Named("restClientBasedPropertyMappingFinder")
public class RestClientBasedPropertyMappingFinder implements PropertyMappingFinder {

    private RestTemplate restTemplate;

    @Inject
    public RestClientBasedPropertyMappingFinder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> executeQuery(String serverUrl, String... urlVariables) {

        String jsonResponse = restTemplate.getForObject(serverUrl, String.class, urlVariables);

        return fromJson(jsonResponse);
    }

    protected Map<String, String> fromJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> allMap = gson.fromJson(jsonString, mapType);
        return gson.fromJson(allMap.get("exportText"), mapType);
    }

}
