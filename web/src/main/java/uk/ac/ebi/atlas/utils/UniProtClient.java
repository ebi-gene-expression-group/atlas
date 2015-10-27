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

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;

@Named
@Scope("prototype")
public class UniProtClient {

    public static final String UNIPROT_URL = "http://www.ebi.uniprot.org/uniprot/?query=accession:{0}&format=tab&columns=id,database(reactome)";

    private static final Logger LOGGER = LogManager.getLogger(UniProtClient.class);

    private RestTemplate restTemplate;

    @Inject
    public UniProtClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<String> fetchReactomeIds(String uniprotId) {
        String url = MessageFormat.format(UNIPROT_URL, uniprotId);

        try {
            String result = restTemplate.getForObject(url, String.class);
            return parseResult(result);
        } catch (RestClientException e) {
            LOGGER.error(e);
            return Lists.newArrayList();
        }
    }

    protected Collection<String> parseResult(String entryString) {

        String ids = StringUtils.substringAfterLast(entryString, "\t");
        if (!StringUtils.isBlank(ids)) {
            return Lists.newArrayList(Splitter.on(";").omitEmptyStrings().trimResults().split(ids));
        } else {
            return Lists.newArrayList();
        }
    }
}
