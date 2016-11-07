package uk.ac.ebi.atlas.solr.query;

import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.BioentityType;
import com.google.common.base.Stopwatch;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Named
//can be singleton because HttpSolrClient is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrQueryService.class);

    static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    static final String BIOENTITY_TYPE_FIELD = "bioentity_type";
    static final String PROPERTY_NAME_FIELD = "property_name";

    static final String PROPERTY_EDGENGRAM_FIELD = "property_value_edgengram";

    private GxaSolrClient solrServer;

    private SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Inject
    public SolrQueryService(GxaSolrClient solrServer,
                            SolrQueryBuilderFactory solrQueryBuilderFactory) {
        this.solrServer = solrServer;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
    }


    private GeneQueryResponse fetchGeneIdsOrSetsGroupedByGeneQueryToken(SemanticQuery geneQuery, String species) {

        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();

        //associate gene ids with each token in the query string
        for (SemanticQueryTerm queryTerm : geneQuery) {
            geneQueryResponse.addGeneIds(queryTerm.toString(), fetchGeneIds(queryTerm, species));
        }
        return geneQueryResponse;

    }

    private Set<String> fetchGeneIds(SemanticQueryTerm queryTerm, String species) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        //eg: {!lucene q.op=OR df=property_value_lower}(property_value_lower:Q9NHV9) AND (bioentity_type:"mirna" OR bioentity_type:"ensgene")
        // fl=bioentity_identifier&group=true&group.field=bioentity_identifier&group.main=true
        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forTerm(queryTerm)
                .withSpecies(species).withBioentityTypes(BioentityType.GENE.getSolrAliases()).build();

        Set<String> geneIds = solrServer.query(solrQuery, false, BIOENTITY_IDENTIFIER_FIELD);

        stopwatch.stop();
        LOGGER.debug(String.format("Fetched gene ids for %s: returned %s results in %s secs", queryTerm.toString(), geneIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return geneIds;
    }

    public GeneQueryResponse fetchResponseBasedOnRequestContext(RequestContext requestContext, String species)
            throws GenesNotFoundException {
        return fetchResponseBasedOnRequestContext(requestContext.getGeneQuery(), species);
    }

    public GeneQueryResponse fetchResponseBasedOnRequestContext(SemanticQuery geneQuery, String species)
            throws GenesNotFoundException {

        if (geneQuery.isEmpty()) {
            return new GeneQueryResponse();
        }

        GeneQueryResponse geneQueryResponse =
                fetchGeneIdsOrSetsGroupedByGeneQueryToken(geneQuery, SpeciesUtils.convertToEnsemblSpecies(species));

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException("No genes found for searchText = " + geneQuery.toJson() + ", species = " + species);
        }

        return geneQueryResponse;
    }

}