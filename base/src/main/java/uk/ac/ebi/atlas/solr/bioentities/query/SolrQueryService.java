package uk.ac.ebi.atlas.solr.bioentities.query;

import com.google.common.base.Stopwatch;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.bioentities.query.builders.BioentityIdentifierQueryBuilder;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.asBioentitiesCollectionQuery;

@Named
// Can be singleton because HttpSolrClient is thread safe, do not to add any other non thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrQueryService.class);

    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    private final BioentitiesSolrClient solrClient;

    @Inject
    public SolrQueryService(BioentitiesSolrClient solrClient) {
        this.solrClient = solrClient;
    }

    private GeneQueryResponse fetchGeneIdsGroupedByGeneQueryToken(SemanticQuery geneQuery, Species species) {
        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();
        //associate gene ids with each token in the query string
        for (SemanticQueryTerm queryTerm : geneQuery) {
            geneQueryResponse.addGeneIds(queryTerm.toString(), fetchGeneIds(queryTerm, species));
        }
        return geneQueryResponse;
    }

    private Set<String> fetchGeneIds(SemanticQueryTerm queryTerm, Species species) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        SolrQuery solrQuery = new BioentityIdentifierQueryBuilder()
                .forTerm(queryTerm)
                .withSpecies(species)
                .build();
        Set<String> geneIds = solrClient.query(solrQuery, false, BIOENTITY_IDENTIFIER_FIELD);

        stopwatch.stop();
        LOGGER.debug(
                "Fetched gene ids for {}: returned {} results in {} secs",
                queryTerm.toString(), geneIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return geneIds;
    }

    public GeneQueryResponse fetchResponse(SemanticQuery geneQuery, Species species) {
        if (geneQuery.isEmpty()) {
            return new GeneQueryResponse();
        }
        GeneQueryResponse geneQueryResponse = fetchGeneIdsGroupedByGeneQueryToken(geneQuery, species);

        if (geneQueryResponse.isEmpty()) {
            throw new RuntimeException(
                    MessageFormat.format(
                            "No genes found matching query: = {0}, species = {1}",
                            geneQuery.description(), species.isUnknown() ? "all" : species.getName()));
        }

        return geneQueryResponse;
    }

    public Set<String> fetchSpecies(SemanticQueryTerm geneQueryTerm) {
        SolrQuery query = new SolrQuery();
        query.setRows(1);

        if (!geneQueryTerm.category().isPresent()) {
            query.setQuery(MessageFormat.format("bioentity_identifier:\"{0}\"", geneQueryTerm.value()));
        }

        Set<String> queryResults = solrClient.query(query, false, "species");
        if (!queryResults.isEmpty()) {
            return queryResults;
        }

        query.setQuery(asBioentitiesCollectionQuery(geneQueryTerm));
        return solrClient.query(query, false, "species");
    }
}
