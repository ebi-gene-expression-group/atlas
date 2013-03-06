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

package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.List;

@Named
public class SolrQueryService {

    private static final Logger LOGGER = Logger.getLogger(SolrQueryService.class);

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    @Value("#{configuration['index.identifier.types']}")
    private String identifierPropertyTypes;

    private HttpSolrServer solrServer;

    @PostConstruct
    private void initServer() {
        solrServer = new HttpSolrServer(serverURL);
        solrServer.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        solrServer.setConnectionTimeout(2000); // 5 seconds to establish TCP
    }

    public List<String> getGeneIds(String geneQuery, String species) throws SolrServerException {

        String queryString = "{!lucene q.op=OR df=property_search} " + geneQuery + " AND species:\"" + species + "\"";

        SolrQuery solrQuery = buildSolrQuery(queryString, "identifier");

        LOGGER.debug("<getGeneIds> processing solr query: " + solrQuery.toString());

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
    }

    public List<String> getGeneIdSuggestions(String geneName, String species) throws SolrServerException {

        String queryString = buildCompositeIdentifierQuery(geneName, species);

        SolrQuery solrQuery = buildSolrQuery(queryString, "property");

        // only top 10 gene name suggestions
        solrQuery.setFacetLimit(10);

        LOGGER.debug("<getGeneIdSuggestions> processing solr query: " + solrQuery.toString());

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
    }

    private String buildCompositeIdentifierQuery(String geneName, String species) {
        String[] propertyTypes = identifierPropertyTypes.trim().split(",");

        StringBuilder query = new StringBuilder();
        query.append("property_edgengram:\"");
        query.append(geneName);
        query.append("\" AND species:\"");
        query.append(species);
        query.append("\" AND (");
        for (int i = 0; i < propertyTypes.length; i++) {
            query.append("property_type:\"");
            query.append(propertyTypes[i]);
            if (i < propertyTypes.length - 1) {
                query.append("\" OR ");
            } else {
                query.append("\"");
            }
        }
        query.append(")");
        return query.toString();
    }

    SolrQuery buildSolrQuery(String queryString, String facedField) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(facedField);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(-1);
        solrQuery.setFacetMinCount(1);
        return solrQuery;
    }


}
