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

package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

@Named
@Scope("singleton")
public class GxaSolrClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GxaSolrClient.class);

    public static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    private SolrClient solrClient;

    @Inject
    public GxaSolrClient(SolrClient solrClient){
        this.solrClient = solrClient;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);

            LOGGER.debug("<query> Solr query time: {} ms, status code: {}", queryResponse.getStatus(), queryResponse.getQTime() );

            return queryResponse;
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    public Set<String> query(SolrQuery solrQuery, String returnedField, boolean returnUppercaseValues){

        QueryResponse queryResponse = query(solrQuery);

        Set<String> results = Sets.newHashSet();

        for (SolrDocument doc : queryResponse.getResults()) {
            String propertyValue = doc.getFieldValue(returnedField).toString();
            if (returnUppercaseValues){
                propertyValue = propertyValue.toUpperCase();
            }
            results.add(propertyValue);
        }
        return results;
    }

    public SortedSetMultimap<String, String> queryForProperties(SolrQuery solrQuery){

        QueryResponse queryResponse = query(solrQuery);

        SortedSetMultimap<String, String> results = TreeMultimap.create();
        for (SolrDocument document : queryResponse.getResults()) {
            String key = document.getFieldValue(PROPERTY_NAME_FIELD).toString();
            String value = document.getFieldValue(PROPERTY_VALUE_FIELD).toString();
            results.put(key, value);
        }

        return results;

    }


}
