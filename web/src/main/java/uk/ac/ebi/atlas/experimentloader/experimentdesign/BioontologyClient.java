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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
public class BioontologyClient {

    private static final String NUM_HITS_JSON_PATH = "$..numHits";

    @Value("#{configuration['bioontology.query.url']}")
    private String bioontologyServiceEndPoint;

    private RestTemplate restTemplate;

    @Inject
    public BioontologyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValid(String ontologyTerm) {

        String jsonResponse = restTemplate.getForObject(bioontologyServiceEndPoint, String.class, ontologyTerm);

        List<Integer> hitsCount = JsonPath.read(jsonResponse, NUM_HITS_JSON_PATH);

        checkState(hitsCount.size() == 1, "invalid nuber of numHits elements in the bioontology service response: "
                                            + jsonResponse);

        return 0 != hitsCount.get(0);

    }

}
