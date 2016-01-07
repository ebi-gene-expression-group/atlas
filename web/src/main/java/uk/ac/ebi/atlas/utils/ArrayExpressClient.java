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

package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.StringReader;

@Named
@Scope("prototype")
public class ArrayExpressClient {

    private static final String EXPERIMENT_NAME_XPATH = "//experiment/name/text()";

    private RestTemplate restTemplate;
    private ApplicationProperties applicationProperties;

    @Inject
    public ArrayExpressClient(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    public String fetchExperimentName(String experimentAccession) {

        try {
            String experimentXML = restTemplate.getForObject(applicationProperties.getArrayExpressRestURL(experimentAccession), String.class);
            return parseExperimentName(experimentXML);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Unable to fetch experiment name from ArrayExpress for " + experimentAccession, e);
        } catch (ParsingException | IOException e) {
            throw new IllegalStateException("Cannot access ArrayExpress", e);
        }
    }

    private String parseExperimentName(String experimentXML) throws ParsingException, IOException {
        Builder parser = new Builder();
        Document doc = parser.build(new StringReader(experimentXML));
        return parseValue(doc);
    }

    private String parseValue(Document doc) throws IllegalStateException {
        Nodes nodes = doc.query(EXPERIMENT_NAME_XPATH);
        Preconditions.checkState(nodes.size() == 1, "Experiment name cannot be fetched from ArrayExpress");

        return nodes.get(0).getValue();
    }

}
