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
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("singleton")
public class SolrClient {

    private static final String BIOENTITY_TYPE_GENE = "ensgene";
    private static final String BIOENTITY_TYPE_MIRNA = "mirna";

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    @Value("#{configuration['index.property_names.bioentity_name']}")
    private String[] bioentityNamePropertyNames;

    @Value("#{configuration['index.property_names.synonym']}")
    private String[] synonymPropertyNames;

    @Value("#{configuration['index.property_names.identifier']}")
    private String[] identifierPropertyNames;


    private String[] tooltipPropertyTypes;

    private final SolrQueryService solrQueryService;

    @Inject
    public SolrClient(@Value("#{configuration['index.property_names.tooltip']}") String[] tooltipPropertyTypes,
                      SolrQueryService solrQueryService,
                      BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer,
                      SolrQueryBuilderFactory solrQueryBuilderFactory) {
        this.tooltipPropertyTypes = tooltipPropertyTypes;
        this.solrQueryService = solrQueryService;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
    }

    public SortedSetMultimap<String, String> fetchTooltipProperties(String identifier) {

        return fetchProperties(identifier, tooltipPropertyTypes);

    }

    public SortedSetMultimap<String, String> fetchGenePageProperties(String identifier, String[] propertyTypes) {
        SortedSetMultimap<String, String> propertiesByType = fetchProperties(identifier, propertyTypes);
        if (propertiesByType.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByType;
    }

    SortedSetMultimap<String, String> fetchProperties(String identifier, String[] propertyTypes) {

        return solrQueryService.fetchProperties(identifier, propertyTypes);

    }

    public Set<String> fetchGeneIdentifiersFromSolr(String queryString, String bioentityType, String... propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                                            .forQueryString(queryString, false)
                                            .withBioentityTypes(bioentityType)
                                            .withPropertyNames(propertyNames).build();

        return solrQueryService.fetchGeneIdentifiersFromSolr(solrQuery);
    }

    public String findSpeciesForBioentityId(String identifier) {

        return getSpeciesForPropertyValue(identifier, SolrQueryService.BIOENTITY_IDENTIFIER_FIELD);

    }

    public List<String> findPropertyValuesForGeneId(String identifier, String propertyType) {

        return solrQueryService.getPropertyValuesForIdentifier(identifier, propertyType);

    }

    public String getSpeciesForPropertyValue(String propertyValue) {
        return getSpeciesForPropertyValue(propertyValue, "");
    }

    public String getSpeciesForPropertyValue(String propertyValue, String propertyName) {
        List<String> propertyValueTokens = bioentityPropertyValueTokenizer.split(propertyValue);
        Set<String> species = Sets.newHashSet();
        for (String propertyValueToken : propertyValueTokens) {
            species.addAll(solrQueryService.getSpeciesForPropertyValue(propertyValueToken, propertyName));
        }
        if (species.size() != 1) {
            throw new ResourceNotFoundException("Exact species can't be determined for propertyValue: " + propertyValue + " and propertyName: " + propertyName);
        }
        return species.iterator().next();
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

    String limitSpeciesNameToTwoWords(String species) {

        String[] words = StringUtils.split(species);
        if (ArrayUtils.getLength(words) > 2){
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }

    public BioentityProperty findBioentityType(String bioentityId) {
        return solrQueryService.getBioentity(bioentityId);
    }

    Set<String> getGeneIds(String geneQuery, boolean exactMatch, String species) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forQueryString(geneQuery, true).withExactMatch(exactMatch)
                .withSpecies(species).withBioentityTypes(BIOENTITY_TYPE_GENE, BIOENTITY_TYPE_MIRNA).build();

        return solrQueryService.fetchGeneIdentifiersFromSolr(solrQuery);
    }

}
