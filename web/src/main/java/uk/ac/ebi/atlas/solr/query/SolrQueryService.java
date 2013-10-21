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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.solr.BioentityType.GENE;

@Named
@Scope("singleton") //can be singleton because HttpSolrServer is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = Logger.getLogger(SolrQueryService.class);

    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String BIOENTITY_TYPE_FIELD = "bioentity_type";
    public static final String SPECIES_FIELD = "species";
    public static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";
    public static final String PROPERTY_EDGENGRAM_FIELD = "property_value_edgengram";

    private static final String BIOENTITY_TYPE_QUERY =
            "(property_name:\"ensgene\"" +
                    "OR property_name:\"mirna\" OR property_name:\"ensprotein\" OR property_name:\"enstranscript\") AND property_value_lower: \"{0}\"";

    private static final int PROPERTY_VALUES_LIMIT = 1000;

    @Value("#{configuration['index.property_names.description']}")
    private String descriptionPropertyNames;

    private String[] tooltipPropertyTypes;

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private GxaSolrServer solrServer;

    private SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Inject
    public SolrQueryService(@Value("#{configuration['index.property_names.tooltip']}") String[] tooltipPropertyTypes,
                                BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer,
                                GxaSolrServer solrServer,
                                SolrQueryBuilderFactory solrQueryBuilderFactory){
        this.tooltipPropertyTypes = tooltipPropertyTypes;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.solrServer = solrServer;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
    }

    public BioentityProperty findBioentityProperty(String bioentityId) {
        String query = MessageFormat.format(BIOENTITY_TYPE_QUERY, bioentityId);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(PROPERTY_VALUES_LIMIT);
        QueryResponse response = solrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = response.getBeans(BioentityProperty.class);
        if(bioentityProperties.isEmpty()){
            throw new ResourceNotFoundException("bioentity not found for bioentityIdentifier: " + bioentityId);
        }
        for (BioentityProperty bioentityProperty: bioentityProperties){
            String bioentityIdentifier = bioentityProperty.getBioentityIdentifier();
            String propertyValue = bioentityProperty.getValue();
            if (bioentityIdentifier.equals(propertyValue)){
                return bioentityProperty;
            }
        }
        throw new IllegalStateException("Solr index is missing document with property_name set to species and property_value set to bioentityIdentifier for bioentity with id: " + bioentityId);
    }

    public SortedSetMultimap<String, String> fetchTooltipProperties(String identifier) {
        return fetchProperties(identifier, tooltipPropertyTypes);

    }

    public SortedSetMultimap<String, String> fetchGenePageProperties(String identifier, String[] propertyNames) {
        SortedSetMultimap<String, String> propertiesByName = fetchProperties(identifier, propertyNames);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByName;
    }

    public Set<String> fetchGeneIdentifiersFromSolr(String queryString, String bioentityType, boolean toUppercase, String... propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forQueryString(queryString, false)
                .withBioentityTypes(Sets.newHashSet(bioentityType))
                .withPropertyNames(propertyNames).build();

        return solrServer.query(solrQuery, BIOENTITY_IDENTIFIER_FIELD, toUppercase);
    }

    public String findSpeciesForBioentityId(String identifier) {

        return getSpeciesForPropertyValue(identifier, BIOENTITY_IDENTIFIER_FIELD);

    }

    public String getSpeciesForPropertyValue(String propertyValue) {
        return getSpeciesForPropertyValue(propertyValue, null);
    }

    public String getSpeciesForPropertyValue(String propertyValue, String propertyName) {
        List<String> propertyValueTokens = bioentityPropertyValueTokenizer.split(propertyValue);
        for (String propertyValueToken : propertyValueTokens) {
            Collection<String> species = executeSpeciesQuery(propertyValueToken, propertyName);
            if (!species.isEmpty()){
                return species.iterator().next();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for propertyValue: " + propertyValue + " and propertyName: " + propertyName);
    }

    public Set<String> expandIdentifiersToMatureRNAIds(Set<String> geneIdentifiers){
        Set<String> expandedIdentifiers = Sets.newHashSet();
        expandedIdentifiers.addAll(geneIdentifiers);

        for (String geneIdentifier: geneIdentifiers){

            Set<String> mirbaseIds = findPropertyValuesForGeneId(geneIdentifier, "mirbase_id");
            if (mirbaseIds.size() > 0) {
                geneIdentifier = mirbaseIds.iterator().next();
            }
            Set<String> matureRNAIds = fetchGeneIdentifiersFromSolr(geneIdentifier, "mirna", false, "hairpin_id");
            if (matureRNAIds.size() > 0) {
                expandedIdentifiers.addAll(matureRNAIds);
            } else {
                expandedIdentifiers.add(geneIdentifier);
            }

        }
        return expandedIdentifiers;

    }

    public Set<String> findGenesFromMirBaseIDs(Collection<String> identifiers) {
        Set<String> ensemblIDs = Sets.newHashSet();
        for (String identifier : identifiers) {
            ensemblIDs.addAll(fetchGeneIdentifiersFromSolr(identifier, "ensgene", true, "mirbase_id"));
        }
        return ensemblIDs;
    }

    public Set<String> findPropertyValuesForGeneId(String identifier, String propertyName) {

        SolrQuery query = solrQueryBuilderFactory.createPropertyValueQueryBuilder()
                .withPropertyNames(propertyName).buildBioentityQuery(identifier);
        query.setFields(PROPERTY_VALUE_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        return solrServer.query(query, PROPERTY_VALUE_FIELD, false);

    }

    public GeneQueryResponse findGeneIdsOrSets(String geneQuery, boolean exactMatch, String species, boolean tokenizeQuery) throws GenesNotFoundException {

        checkArgument(StringUtils.isNotBlank(geneQuery));

        species = limitSpeciesNameToTwoWords(species);

        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();

        if (tokenizeQuery) {
            for (String queryToken : bioentityPropertyValueTokenizer.split(geneQuery)) {
                Set<String> geneIds = getGeneIds(queryToken, exactMatch, species);
                geneQueryResponse.addGeneIds(queryToken, geneIds);
            }
        } else {
            Set<String> geneIds = getGeneIds(geneQuery, exactMatch, species);
            geneQueryResponse.addGeneIds(geneQuery, geneIds);
        }
        return geneQueryResponse;

    }

    Set<String> getGeneIds(String geneQuery, boolean exactMatch, String species) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forQueryString(geneQuery, true).withExactMatch(exactMatch)
                .withSpecies(species).withBioentityTypes(GENE.getSolrAliases()).build();

        return solrServer.query(solrQuery, BIOENTITY_IDENTIFIER_FIELD, true);

    }

    String limitSpeciesNameToTwoWords(String species) {

        String[] words = StringUtils.split(species);
        if (ArrayUtils.getLength(words) > 2){
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }

    SortedSetMultimap<String, String> fetchProperties(String bioentityIdentifier, String[] propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createPropertyValueQueryBuilder()
                            .withPropertyNames(propertyNames).buildBioentityQuery(bioentityIdentifier);

        solrQuery.setRows(PROPERTY_VALUES_LIMIT);
        solrQuery.setFields(PROPERTY_VALUE_FIELD, PROPERTY_NAME_FIELD);

        LOGGER.debug("<querySolrForProperties> processing solr query: " + solrQuery.getQuery());

        return solrServer.queryForProperties(solrQuery);

    }

    Collection<String> executeSpeciesQuery(String propertyValue, String propertyName) {
        SolrQuery query;
        if (StringUtils.isBlank(propertyName)) {
            query = new SolrQuery(PROPERTY_LOWER_FIELD + ":\"" + propertyValue + "\"");
        } else {
            query = new SolrQuery(propertyName + ":" + propertyValue);
        }

        query.setFields(SPECIES_FIELD);
        query.setRows(100);

        return solrServer.query(query, SPECIES_FIELD, false);
    }

}