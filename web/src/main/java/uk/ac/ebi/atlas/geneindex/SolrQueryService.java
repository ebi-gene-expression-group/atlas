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
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
public class SolrQueryService {

    private static final Logger LOGGER = Logger.getLogger(SolrQueryService.class);
    private static final int PROPERTY_VALUES_LIMIT = 1000;
    // changed from 2000
    private static final int CONNECTION_TIMEOUT = 60000;
    private static final int MAX_RETRIES = 1;
    private static final int DEFAULT_LIMIT = 15;
    private static final String CONFIG_SPLIT_REGEX = ",";
    public static final String PROPERTY_LOWER_FIELD = "property_lower";
    public static final String IDENTIFIER_FIELD = "identifier";
    private static final String SPECIES_FIELD = "species";
    private static final String PROPERTY_FIELD = "property";
    // changed from 100000
    private static final int MAX_GENE_IDS_TO_FETCH = 1000000;

    private static final String BIOENTITY_TYPE_GENE = "ensgene";

    private static final String BIOENTITY_TYPE_MIRNA = "mirna";

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
        // defaults to 0.  > 1 not recommended.
        solrServer.setMaxRetries(MAX_RETRIES);
        // 5 seconds to establish TCP
        solrServer.setConnectionTimeout(CONNECTION_TIMEOUT);
    }

    public SortedSetMultimap<String, String> fetchProperties(String identifier, List<String> propertyTypes) {

        String queryString = buildCompositeQueryIdentifier(identifier, propertyTypes);

        return querySolrForProperties(queryString, PROPERTY_VALUES_LIMIT);
    }

    public Set<String> getGeneIds(String geneQuery, boolean exactMatch, String species) {

        String queryString = buildGeneQuery(geneQuery, exactMatch, species, BIOENTITY_TYPE_GENE, BIOENTITY_TYPE_MIRNA);

        return fetchGeneIdentifiersFromSolr(queryString);
    }

    public List<String> getGeneIdSuggestionsInName(String geneName, String species) {

        String[] propertyTypes = namePropertyTypes.trim().split(CONFIG_SPLIT_REGEX);

        String queryString = buildCompositeQuery(geneName, species, propertyTypes, BIOENTITY_TYPE_GENE, BIOENTITY_TYPE_MIRNA);

        return getSolrResultsForQuery(queryString, PROPERTY_LOWER_FIELD, DEFAULT_LIMIT);
    }

    public List<String> getGeneIdSuggestionsInSynonym(String geneName, String species) {

        String[] propertyTypes = synonymPropertyTypes.trim().split(CONFIG_SPLIT_REGEX);

        String queryString = buildCompositeQuery(geneName, species, propertyTypes, BIOENTITY_TYPE_GENE, BIOENTITY_TYPE_MIRNA);

        return getSolrResultsForQuery(queryString, PROPERTY_LOWER_FIELD, DEFAULT_LIMIT);
    }

    public List<String> getGeneIdSuggestionsInIdentifier(String geneName, String species) {

        String[] propertyTypes = identifierPropertyTypes.trim().split(CONFIG_SPLIT_REGEX);

        String queryString = buildCompositeQuery(geneName, species, propertyTypes, BIOENTITY_TYPE_GENE, BIOENTITY_TYPE_MIRNA);

        return getSolrResultsForQuery(queryString, PROPERTY_LOWER_FIELD, DEFAULT_LIMIT);
    }

    public String getSpeciesForIdentifier(String identifier) {

        SolrQuery query = new SolrQuery("identifier:" + identifier);
        Collection<String> species = extractAllSpecies(query);
        if (species.size() == 1) {
            return species.iterator().next();
        }
        throw new IllegalStateException("Found more than one specie for identifier: " + identifier);

    }

    public Collection<String> getSpeciesForPropertyValue(String propertyValue) {

        SolrQuery query = new SolrQuery(PROPERTY_LOWER_FIELD + ":" + propertyValue);
        return extractAllSpecies(query);

    }

    public Collection<String> getSpeciesForPropertyValue(String value, String type) {
        if (StringUtils.isEmpty(type)) {
            return getSpeciesForPropertyValue(value);
        }

        SolrQuery query = new SolrQuery(type + ":" + value);
        return extractAllSpecies(query);
    }

    public List<String> getPropertyValuesForIdentifier(String identifier, String propertyType) {

        List<String> results = Lists.newArrayList();

        SolrQuery query = new SolrQuery("identifier:" + identifier + " AND property_type:" + propertyType);
        query.setFields(PROPERTY_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        QueryResponse solrResponse = executeSolrQuery(query);
        for (SolrDocument doc : solrResponse.getResults()) {
            results.add(doc.getFieldValue(PROPERTY_FIELD).toString());
        }

        return results;
    }

    private Collection<String> extractAllSpecies(SolrQuery query) {
        Collection<String> species = Sets.newHashSet();

        query.setFields(SPECIES_FIELD);
        query.setRows(100);

        QueryResponse solrResponse = executeSolrQuery(query);
        SolrDocumentList results = solrResponse.getResults();
        for (SolrDocument result : results) {
            species.add(result.getFieldValue(SPECIES_FIELD).toString());
        }

        return species;
    }

    Set<String> fetchGeneIdentifiersFromSolr(String queryString) {
        Set<String> results = Sets.newHashSet();

        SolrQuery solrQuery = new SolrQuery(queryString);
        solrQuery.setFields(IDENTIFIER_FIELD);
        solrQuery.setParam("group", true);
        solrQuery.setParam("group.field", IDENTIFIER_FIELD);
        solrQuery.setParam("group.main", true);
        solrQuery.setRows(MAX_GENE_IDS_TO_FETCH);

        LOGGER.debug("<fetchGeneIdentifiersFromSolr> processing solr query: " + solrQuery.toString());

        QueryResponse solrResponse = executeSolrQuery(solrQuery);
        for (SolrDocument doc : solrResponse.getResults()) {
            String uppercaseGeneId = doc.getFieldValue(IDENTIFIER_FIELD).toString().toUpperCase();
            results.add(uppercaseGeneId);
        }

        return results;
    }

    QueryResponse executeSolrQuery(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = solrServer.query(solrQuery);
            LOGGER.info("<executeSolrQuery> Solr query time: " + queryResponse.getQTime() + " status code: " + queryResponse.getStatus());
            return queryResponse;
        } catch (SolrServerException e) {
            LOGGER.error("<executeSolrQuery> error querying solr service", e);
            throw new IllegalStateException(e);
        }
    }

    SortedSetMultimap<String, String> querySolrForProperties(String queryString, int limitResults) {
        SolrQuery solrQuery = new SolrQuery(queryString);
        solrQuery.setRows(limitResults);
        solrQuery.setFields(PROPERTY_FIELD, "property_type");

        LOGGER.debug("<querySolrForProperties> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = executeSolrQuery(solrQuery);

        SortedSetMultimap<String, String> results = TreeMultimap.create();
        for (SolrDocument document : solrResponse.getResults()) {
            String key = document.getFieldValue("property_type").toString();
            String value = document.getFieldValue(PROPERTY_FIELD).toString();
            results.put(key, value);
        }

        return results;
    }

    List<String> getSolrResultsForQuery(String queryString, String resultField, int limitResults) {
        SolrQuery solrQuery = buildSolrQuery(queryString, resultField, limitResults);

        LOGGER.debug("<getSolrResultsForQuery> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = executeSolrQuery(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
    }

    String buildGeneQuery(String query, boolean exactMatch, String species, String... bioEntityTypes) {
        String propertyName = exactMatch ? PROPERTY_LOWER_FIELD : "property_search";

        String escapedGeneQuery = customEscape(query);

        StringBuilder sb =
                new StringBuilder()
                        .append("{!lucene q.op=OR df=" + propertyName + "} ")
                        .append("(" + propertyName + ":").append(escapedGeneQuery).append(")");

        appendSpecies(sb, species);

        appendBioEntityTypes(sb, bioEntityTypes);

        return sb.toString();
    }

    String buildCompositeQueryIdentifier(String identifier, List<String> propertyTypes) {

        StringBuilder query = new StringBuilder();
        query.append("identifier:\"");
        query.append(identifier);
        query.append("\" AND (");
        for (int i = 0; i < propertyTypes.size(); i++) {
            query.append("property_type:\"");
            query.append(propertyTypes.get(i));
            if (i < propertyTypes.size() - 1) {
                query.append("\" OR ");
            } else {
                query.append("\"");
            }
        }
        query.append(")");
        return query.toString();
    }

    String buildCompositeQuery(String geneName, String species, String[] propertyTypes, String... bioentityTypes) {

        StringBuilder query = new StringBuilder();
        query.append("property_edgengram:\"");
        query.append(geneName);
        query.append("\"");
        appendSpecies(query, species);
        appendBioEntityTypes(query, bioentityTypes);
        query.append(" AND (");
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

    private void appendSpecies(StringBuilder sb, String species) {
        sb.append(" AND species:\"")
                .append(species)
                .append("\"");
    }

    private void appendBioEntityTypes(StringBuilder sb, String[] bioEntityTypes) {
        sb.append(" AND (");
        for (String bioEntityType : bioEntityTypes) {
            sb.append("type:\"")
                    .append(bioEntityType)
                    .append("\" OR ");
        }
        int indexOfOR = sb.lastIndexOf(" OR ");
        sb.delete(indexOfOR, indexOfOR + 4);
        sb.append(")");
    }
}