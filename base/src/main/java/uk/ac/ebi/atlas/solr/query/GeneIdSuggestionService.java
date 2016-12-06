package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class GeneIdSuggestionService {

    @Value("#{configuration['index.property_names.bioentity_name']}")
    private String[] bioentityNamePropertyNames;

    @Value("#{configuration['index.property_names.synonym']}")
    private String[] synonymPropertyNames;

    @Value("#{configuration['index.property_names.identifier']}")
    private String[] identifierPropertyNames;

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;
    private final GxaSolrClient solrServer;
    private final SpeciesPropertiesTrader speciesTrader;

    @Inject
    public GeneIdSuggestionService(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrClient solrServer,
                                   SpeciesPropertiesTrader speciesTrader) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrServer = solrServer;
        this.speciesTrader = speciesTrader;
    }

    public List<SemanticQueryTerm> fetchGeneIdSuggestionsInName(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"symbol")
        return fetchAutoCompleteSuggestions(geneName, species, bioentityNamePropertyNames);
    }

    public List<SemanticQueryTerm> fetchGeneIdSuggestionsInSynonym(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"synonym")
        return fetchAutoCompleteSuggestions(geneName, species, synonymPropertyNames);
    }

    public List<SemanticQueryTerm> fetchGeneIdSuggestionsInIdentifier(String geneName, String species) {
        // ie: property_value_edgengram:"<geneName>" AND (bioentity_type:"ensgene" OR bioentity_type:"mirna" OR bioentity_type:"ensprotein" OR bioentity_type:"enstranscript") AND (property_name:"gene_biotype" OR property_name:"ensfamily" OR property_name:"refseq" OR property_name:"rgd" OR property_name:"design_element" OR property_name:"mirbase_accession" OR property_name:"mirbase_name" OR property_name:"flybase_transcript_id" OR property_name:"unigene" OR property_name:"embl" OR property_name:"interpro" OR property_name:"ensgene" OR property_name:"flybase_gene_id" OR property_name:"pathwayid" OR property_name:"mgi_id" OR property_name:"ensprotein" OR property_name:"mirbase_id" OR property_name:"enstranscript" OR property_name:"entrezgene" OR property_name:"uniprot" OR property_name:"go")
        return fetchAutoCompleteSuggestions(geneName, species, identifierPropertyNames);
    }

    List<SemanticQueryTerm> fetchAutoCompleteSuggestions(String queryString, String species, String[] propertyNames) {
        SolrQuery solrQuery = solrQueryBuilderFactory.createAutocompleteGroupedPropertyValueQueryBuilder()
                .withSpecies(speciesTrader.getByName(species).name().replaceAll("_", " "))
                .withBioentityTypes(BioentityType.getAllSolrAliases())
                .withPropertyNames(propertyNames)
                .build(queryString);

        return fetchGroupedFacetedResults(solrQuery);
    }

    //TODO: replace with SolrUtil.extractFirstFacetValues
    private List<SemanticQueryTerm> fetchGroupedFacetedResults(SolrQuery solrQuery) {
        QueryResponse queryResponse = solrServer.query(solrQuery);

        List<SemanticQueryTerm> geneNames = Lists.newArrayList();

        SolrDocumentList results = queryResponse.getResults();
        if(results != null) {
            for (SolrDocument doc : results) {
                String propertyValue = doc.getFieldValue("property_value").toString();
                String propertyName = doc.getFieldValue("property_name").toString();    //TODO: create mapping between property_name and source to show in the UI

                geneNames.add(SemanticQueryTerm.create(propertyValue, propertyName));
            }
        }

        return geneNames;
    }

}
