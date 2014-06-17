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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("singleton")
public class GeneIdSuggestionService {

    @Value("#{configuration['index.property_names.bioentity_name']}")
    private String[] bioentityNamePropertyNames;

    @Value("#{configuration['index.property_names.synonym']}")
    private String[] synonymPropertyNames;

    @Value("#{configuration['index.property_names.identifier']}")
    private String[] identifierPropertyNames;

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;

    private GxaSolrServer solrServer;

    @Inject
    public GeneIdSuggestionService(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrServer solrServer) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrServer = solrServer;
    }

    public List<String> fetchGeneIdSuggestionsInName(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"symbol")
        return fetchAutoCompleteSuggestions(geneName, Species.limitSpeciesNameToTwoWords(species), bioentityNamePropertyNames);
    }

    public List<String> fetchGeneIdSuggestionsInSynonym(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"synonym")
        return fetchAutoCompleteSuggestions(geneName, Species.limitSpeciesNameToTwoWords(species), synonymPropertyNames);
    }

    public List<String> fetchGeneIdSuggestionsInIdentifier(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"gene_biotype" OR property_name:"ensfamily" OR property_name:"refseq" OR property_name:"rgd" OR property_name:"design_element" OR property_name:"mirbase_accession" OR property_name:"mirbase_name" OR property_name:"flybase_transcript_id" OR property_name:"unigene" OR property_name:"embl" OR property_name:"interpro" OR property_name:"ensgene" OR property_name:"flybase_gene_id" OR property_name:"pathwayid" OR property_name:"mgi_id" OR property_name:"ensprotein" OR property_name:"mirbase_id" OR property_name:"enstranscript" OR property_name:"entrezgene" OR property_name:"uniprot" OR property_name:"go")
        return fetchAutoCompleteSuggestions(geneName, Species.limitSpeciesNameToTwoWords(species), identifierPropertyNames);
    }

    List<String> fetchAutoCompleteSuggestions(String queryString, String species, String[] propertyNames) {
        SolrQuery solrQuery = solrQueryBuilderFactory.createAutocompleFacetedPropertyValueQueryBuilder()
                .withSpecies(species)
                .withBioentityTypes(BioentityType.getAllSolrAliases())
                .withPropertyNames(propertyNames)
                .buildPropertyValueAutocompleteQuery(queryString);

        return fetchFacetedResults(solrQuery);
    }

    List<String> fetchFacetedResults(SolrQuery solrQuery) {

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        List<FacetField.Count> firstFacetFieldCounts = solrResponse.getFacetFields().get(0).getValues();
        if (firstFacetFieldCounts != null) {
            for (FacetField.Count facetFieldCount : firstFacetFieldCounts) {
                geneNames.add(facetFieldCount.getName());
            }
        }
        return geneNames;
    }

}
