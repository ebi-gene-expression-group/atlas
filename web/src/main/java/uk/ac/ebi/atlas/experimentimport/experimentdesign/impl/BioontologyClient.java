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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.impl;

import com.jayway.jsonpath.JsonPath;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
public class BioontologyClient {

    private static final Logger LOGGER = Logger.getLogger(BioontologyClient.class);

    private static final String NUM_HITS_JSON_PATH = "$..numHits";

    @Value("#{configuration['bioontology.query.url']}")
    private String bioontologyServiceEndPoint;

    private RestTemplate restTemplate;

    @Inject
    public BioontologyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValid(String ontologyTerm) {
        try{
            String jsonResponse = restTemplate.getForObject(bioontologyServiceEndPoint, String.class, ontologyTerm);

            List<Integer> hitsCount = JsonPath.read(jsonResponse, NUM_HITS_JSON_PATH);

            checkState(hitsCount.size() == 1, "invalid nuber of numHits elements in the bioontology service response: "
                                                + jsonResponse);

            return 0 != hitsCount.get(0);

        }catch (RuntimeException e){

            UriTemplate uriTemplate = new UriTemplate(bioontologyServiceEndPoint);
            URI expanded = uriTemplate.expand(ontologyTerm);
            String errorMessage = "Cannot access " + expanded + " " + e.getMessage();

            LOGGER.error(errorMessage, e);
            throw new BioOntologyClientException(errorMessage, e);
        }
    }

    private class BioOntologyClientException extends RuntimeException {

        public BioOntologyClientException(String message, Throwable cause) {
            super(message, cause);
        }

    }

}
