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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("singleton") //can be singleton because HttpSolrServer is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = Logger.getLogger(SolrQueryService.class);

    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String BIOENTITY_TYPE_FIELD = "bioentity_type";
    public static final String SPECIES_FIELD = "species";
    public static final String PROPERTY_NAME_FIELD = "property_name";
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";
    public static final String PROPERTY_EDGENGRAM_FIELD = "property_value_edgengram";

    private static final String BIOENTITY_TYPE_QUERY =
            "(property_name:\"ensgene\"" +
                    "OR property_name:\"mirna\" OR property_name:\"ensprotein\" OR property_name:\"enstranscript\") AND property_value_lower: \"{0}\"";

    private static final int PROPERTY_VALUES_LIMIT = 1000;
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    // changed from 100000


    @Value("#{configuration['index.server.url']}")
    private String serverURL;


    @Value("#{configuration['index.property_names.description']}")
    private String descriptionPropertyNames;

    private SolrServer solrServer;

    private SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Inject
    public SolrQueryService(SolrServer solrServer, SolrQueryBuilderFactory solrQueryBuilderFactory){
        this.solrServer = solrServer;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
    }

    SortedSetMultimap<String, String> fetchProperties(String identifier, String[] propertyTypes) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createPropertyValueQueryBuilder()
                            .withPropertyNames(propertyTypes).buildBioentityQuery(identifier);
        return querySolrForProperties(solrQuery, PROPERTY_VALUES_LIMIT);
    }


    String getSpeciesForIdentifier(String identifier) {

        SolrQuery query = new SolrQuery(BIOENTITY_IDENTIFIER_FIELD + ":" + identifier);
        Collection<String> species = extractAllSpecies(query);
        if (species.size() == 1) {
            return species.iterator().next();
        }
        throw new IllegalStateException("Found more than one specie for identifier: " + identifier);

    }

    Collection<String> getSpeciesForPropertyValue(String propertyValue) {

        SolrQuery query = new SolrQuery(PROPERTY_LOWER_FIELD + ":\"" + propertyValue + "\"");
        return extractAllSpecies(query);

    }

    Collection<String> getSpeciesForPropertyValue(String value, String type) {
        if (StringUtils.isEmpty(type)) {
            return getSpeciesForPropertyValue(value);
        }

        SolrQuery query = new SolrQuery(type + ":" + value);
        return extractAllSpecies(query);
    }

    List<String> getPropertyValuesForIdentifier(String identifier, String propertyType) {

        List<String> results = Lists.newArrayList();

        SolrQuery query = new SolrQuery(BIOENTITY_IDENTIFIER_FIELD + ":" + identifier + " AND " + PROPERTY_NAME_FIELD + ":" + propertyType);
        query.setFields(PROPERTY_VALUE_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        QueryResponse solrResponse = executeSolrQuery(query);
        for (SolrDocument doc : solrResponse.getResults()) {
            results.add(doc.getFieldValue(PROPERTY_VALUE_FIELD).toString());
        }

        return results;
    }

    Collection<String> extractAllSpecies(SolrQuery query) {
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

    Set<String> fetchGeneIdentifiersFromSolr(SolrQuery solrQuery) {
        Set<String> results = Sets.newHashSet();

        LOGGER.debug("<fetchGeneIdentifiersFromSolr> processing solr query: " + solrQuery.toString());

        QueryResponse solrResponse = executeSolrQuery(solrQuery);
        for (SolrDocument doc : solrResponse.getResults()) {
            String uppercaseGeneId = doc.getFieldValue(BIOENTITY_IDENTIFIER_FIELD).toString().toUpperCase();
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

    SortedSetMultimap<String, String> querySolrForProperties(SolrQuery solrQuery, int limitResults) {
        solrQuery.setRows(limitResults);
        solrQuery.setFields(PROPERTY_VALUE_FIELD, PROPERTY_NAME_FIELD);

        LOGGER.debug("<querySolrForProperties> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = executeSolrQuery(solrQuery);

        SortedSetMultimap<String, String> results = TreeMultimap.create();
        for (SolrDocument document : solrResponse.getResults()) {
            String key = document.getFieldValue(PROPERTY_NAME_FIELD).toString();
            String value = document.getFieldValue(PROPERTY_VALUE_FIELD).toString();
            results.put(key, value);
        }

        return results;
    }

    BioentityProperty getBioentity(String bioentityId) {
        String query = MessageFormat.format(BIOENTITY_TYPE_QUERY, bioentityId);
        SolrQuery solrQuery = new SolrQuery(query);
        QueryResponse response = executeSolrQuery(solrQuery);
        SolrDocumentList solrDocuments = response.getResults();
        if (solrDocuments.isEmpty()) {
            throw new ResourceNotFoundException("bioentity not found for identifier: " + bioentityId);
        }
        for (SolrDocument solrDocument: solrDocuments){
            String bioentityIdentifier = (String) solrDocument.get(BIOENTITY_IDENTIFIER_FIELD);
            String propertyValue = (String) solrDocument.get(PROPERTY_VALUE_FIELD);
            if (bioentityIdentifier.equals(propertyValue)){

                String bioentityTypeAlias = (String) solrDocument.get(BIOENTITY_TYPE_FIELD);
                String species = (String) solrDocument.get(SPECIES_FIELD);
                String propertyName = (String) solrDocument.get(PROPERTY_NAME_FIELD);

                return new BioentityProperty(bioentityIdentifier, bioentityTypeAlias, species, propertyName, propertyValue);

            }
        }

        throw new IllegalStateException("Solr index is missing document with property_name set to species and property_value set to identifier for bioentity with id: " + bioentityId);

    }

}