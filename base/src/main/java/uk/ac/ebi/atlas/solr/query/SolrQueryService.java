package uk.ac.ebi.atlas.solr.query;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.BioentityType;
import com.google.common.base.Stopwatch;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;

@Named
// Can be singleton because HttpSolrClient is documented to be thread safe, please be careful not to add any other non
// thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrQueryService.class);

    static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";

    private final BioentitiesSolrClient solrClient;
    private final SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Inject
    public SolrQueryService(BioentitiesSolrClient solrClient,
                            SolrQueryBuilderFactory solrQueryBuilderFactory) {
        this.solrClient = solrClient;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
    }

    private GeneQueryResponse fetchGeneIdsGroupedByGeneQueryToken(SemanticQuery geneQuery,
                                                                  Species species) {
        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();

        //associate gene ids with each token in the query string
        for (SemanticQueryTerm queryTerm : geneQuery) {
            geneQueryResponse.addGeneIds(queryTerm.toString(), fetchGeneIds(queryTerm, species));
        }
        return geneQueryResponse;
    }

    private Set<String> fetchGeneIds(SemanticQueryTerm queryTerm, Species species) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forTerm(queryTerm)
                .withSpecies(species)
                .withBioentityTypes(BioentityType.GENE.getSolrAliases())
                .build();
        Set<String> geneIds = solrClient.query(solrQuery, false, BIOENTITY_IDENTIFIER_FIELD);

        stopwatch.stop();
        LOGGER.debug(
                String.format("Fetched gene ids for %s: returned %s results in %s secs",
                        queryTerm.toString(), geneIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return geneIds;
    }

    public GeneQueryResponse fetchResponse(SemanticQuery geneQuery, Species species) {
        if (isEmpty(geneQuery)) {
            return new GeneQueryResponse();
        }

        GeneQueryResponse geneQueryResponse = fetchGeneIdsGroupedByGeneQueryToken(geneQuery, species);

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException(
                    "No genes found for searchText = " + geneQuery.toJson() + ", species = " + species.getName());
        }

        return geneQueryResponse;
    }
}
