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

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class ReactomeBiomartClient {

    public static final String REACTOME_BIOMART_URL = "http://reactomerelease.oicr.on.ca:5555/biomart/martservice?query=" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE Query>\n" +
            "<Query  virtualSchemaName = \"default\" formatter = \"TSV\" header = \"0\" uniqueRows = \"0\" count = \"\" datasetConfigVersion = \"0.6\" >\n" +
            "<Dataset name = \"pathway\" interface = \"default\" >\n" +
            "<Filter name = \"pathway_id_list\" value = \"{0}\"/>\n" +
            "<Attribute name = \"stableidentifier_identifier\" />\n" +
            "<Attribute name = \"_displayname\" />\n" +
            "</Dataset>\n" +
            "</Query>";

    private RestTemplate restTemplate;

    @Inject
    public ReactomeBiomartClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchPathwayName(String reactomeId) {
        String url = MessageFormat.format(REACTOME_BIOMART_URL, reactomeId);
        String result = restTemplate.getForObject(url, String.class);

        return StringUtils.trimToNull(StringUtils.substringAfterLast(result, "\t"));
    }

}
