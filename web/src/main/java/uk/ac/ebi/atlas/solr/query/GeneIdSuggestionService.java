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
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
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

    private GxaSolrClient solrServer;

    @Inject
    public GeneIdSuggestionService(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrClient solrServer) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrServer = solrServer;
    }

    public List<TermSourceSuggestion> fetchGeneIdSuggestionsInName(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"symbol")
        return fetchAutoCompleteSuggestions(geneName, species, bioentityNamePropertyNames);
    }

    public List<TermSourceSuggestion> fetchGeneIdSuggestionsInSynonym(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"synonym")
        return fetchAutoCompleteSuggestions(geneName, species, synonymPropertyNames);
    }

    public List<TermSourceSuggestion> fetchGeneIdSuggestionsInIdentifier(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"gene_biotype" OR property_name:"ensfamily" OR property_name:"refseq" OR property_name:"rgd" OR property_name:"design_element" OR property_name:"mirbase_accession" OR property_name:"mirbase_name" OR property_name:"flybase_transcript_id" OR property_name:"unigene" OR property_name:"embl" OR property_name:"interpro" OR property_name:"ensgene" OR property_name:"flybase_gene_id" OR property_name:"pathwayid" OR property_name:"mgi_id" OR property_name:"ensprotein" OR property_name:"mirbase_id" OR property_name:"enstranscript" OR property_name:"entrezgene" OR property_name:"uniprot" OR property_name:"go")
        return fetchAutoCompleteSuggestions(geneName, species, identifierPropertyNames);
    }

    List<TermSourceSuggestion> fetchAutoCompleteSuggestions(String queryString, String species, String[] propertyNames) {
        SolrQuery solrQuery = solrQueryBuilderFactory.createAutocompleteGroupedPropertyValueQueryBuilder()
                .withSpecies(Species.convertToEnsemblSpecies(species))
                .withBioentityTypes(BioentityType.getAllSolrAliases())
                .withPropertyNames(propertyNames)
                .build(queryString);

        return fetchGroupedFacetedResults(solrQuery);
    }

    //TODO: replace with SolrUtil.extractFirstFacetValues

    List<TermSourceSuggestion> fetchGroupedFacetedResults(SolrQuery solrQuery) {
        QueryResponse queryResponse = solrServer.query(solrQuery);

        List<TermSourceSuggestion> geneNames = Lists.newArrayList();

        SolrDocumentList results = queryResponse.getResults();
        if(results != null) {
            for (SolrDocument doc : results) {
                String propertyValue = doc.getFieldValue("property_value").toString();
                String propertyName = doc.getFieldValue("property_name").toString();    //TODO: create mapping between property_name and source to show in the UI

                TermSourceSuggestion termSource = new TermSourceSuggestion(propertyValue, propertyName);
                geneNames.add(termSource);
            }
        }

        return geneNames;
    }

}
