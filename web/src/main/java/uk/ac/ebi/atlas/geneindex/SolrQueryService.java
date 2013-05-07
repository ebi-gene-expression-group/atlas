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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.List;

@Named
public class SolrQueryService {

    private static final Logger LOGGER = Logger.getLogger(SolrQueryService.class);
    private static final int PROPERTY_VALUES_LIMIT = 1000;
    private static final int CONNECTION_TIMEOUT = 2000;
    private static final int MAX_RETRIES = 1;
    private static final int DEFAULT_LIMIT = 15;

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    @Value("#{configuration['index.types.name']}")
    private String namePropertyTypes;

    @Value("#{configuration['index.types.synonym']}")
    private String synonymPropertyTypes;

    @Value("#{configuration['index.types.identifier']}")
    private String identifierPropertyTypes;

    @Value("#{configuration['index.types.description']}")
    private String descriptionPropertyTypes;

    private HttpSolrServer solrServer;

    @PostConstruct
    private void initServer() {
        solrServer = new HttpSolrServer(serverURL);
        solrServer.setMaxRetries(MAX_RETRIES); // defaults to 0.  > 1 not recommended.
        solrServer.setConnectionTimeout(CONNECTION_TIMEOUT); // 5 seconds to establish TCP
    }

    public Multimap<String, String> fetchProperties(String identifier, String[] propertyTypes) throws SolrServerException {

        String queryString = buildCompositeQueryIdentifier(identifier, propertyTypes);

        return querySolrForProperties(queryString, PROPERTY_VALUES_LIMIT);
    }

    public List<String> getGeneIds(String geneQuery, boolean exactMatch, String species) throws SolrServerException {

        String queryString = buildGeneQuery(geneQuery, exactMatch, species);

        return fetchGeneIdentifiersFromSolr(queryString);
    }

    public List<String> getGeneIdSuggestionsInName(String geneName, String species) throws SolrServerException {

        String[] propertyTypes = namePropertyTypes.trim().split(",");

        String queryString = buildCompositeQuery(geneName, species, propertyTypes);

        return getSolrResultsForQuery(queryString, "property_lower", DEFAULT_LIMIT);
    }

    public List<String> getGeneIdSuggestionsInSynonym(String geneName, String species) throws SolrServerException {

        String[] propertyTypes = synonymPropertyTypes.trim().split(",");

        String queryString = buildCompositeQuery(geneName, species, propertyTypes);

        return getSolrResultsForQuery(queryString, "property_lower", DEFAULT_LIMIT);
    }

    public List<String> getGeneIdSuggestionsInIdentifier(String geneName, String species) throws SolrServerException {

        String[] propertyTypes = identifierPropertyTypes.trim().split(",");

        String queryString = buildCompositeQuery(geneName, species, propertyTypes);

        return getSolrResultsForQuery(queryString, "property_lower", DEFAULT_LIMIT);
    }

    public String getSpeciesForIdentifier(String identifier) throws SolrServerException {

        String species = null;

        SolrQuery query = new SolrQuery("identifier:" + identifier);
        query.setFields("species");
        query.setRows(1);

        QueryResponse solrResponse = solrServer.query(query);
        for (SolrDocument doc : solrResponse.getResults()) {
            species = doc.getFieldValue("species").toString();
        }

        return species;
    }

    List<String> fetchGeneIdentifiersFromSolr(String queryString) throws SolrServerException {
        List<String> results = Lists.newArrayList();

        SolrQuery query = new SolrQuery(queryString);
        query.setFields("identifier");
        query.setParam("group", true);
        query.setParam("group.field", "identifier");
        query.setParam("group.main", true);
        query.setRows(100000);

        LOGGER.debug("<fetchGeneIdentifiersFromSolr> processing solr query: " + query.toString());

        QueryResponse solrResponse = solrServer.query(query);
        for (SolrDocument doc : solrResponse.getResults()) {
            results.add(doc.getFieldValue("identifier").toString());
        }

        return results;
    }

    Multimap<String, String> querySolrForProperties(String queryString, int limitResults) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery(queryString);
        solrQuery.setRows(limitResults);
        solrQuery.setFields("property", "property_type");

        LOGGER.debug("<querySolrForProperties> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = solrServer.query(solrQuery);

        Multimap<String, String> results = HashMultimap.create();
        for (SolrDocument document : solrResponse.getResults()) {
            String key = document.getFieldValue("property_type").toString();
            String value = document.getFieldValue("property").toString();
            results.put(key, value);
        }

        return results;
    }

    List<String> getSolrResultsForQuery(String queryString, String resultField, int limitResults) throws SolrServerException {
        SolrQuery solrQuery = buildSolrQuery(queryString, resultField, limitResults);

        LOGGER.debug("<getSolrResultsForQuery> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
    }

    String buildGeneQuery(String query, boolean exactMatch, String species) {
        String propertyName = exactMatch ? "property_lower" : "property_search";

        String escapedGeneQuery = customEscape(query);

        StringBuilder sb =
                new StringBuilder()
                        .append("{!lucene q.op=OR df=" + propertyName + "} ")
                        .append("(" + propertyName + ":").append(escapedGeneQuery).append(")")
                        .append(" AND species:\"")
                        .append(species)
                        .append("\"");
        return sb.toString();

    }

    String buildCompositeQueryIdentifier(String identifier, String[] propertyTypes) {

        StringBuilder query = new StringBuilder();
        query.append("identifier:\"");
        query.append(identifier);
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

    String buildCompositeQuery(String geneName, String species, String[] propertyTypes) {

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

    SolrQuery buildSolrQuery(String queryString, String facedField, int facetLimit) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(facedField);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(facetLimit);
        solrQuery.setFacetMinCount(1);
        return solrQuery;
    }


    String customEscape(String searchText) {
        return searchText.replace(":", "\\:");
    }
}
