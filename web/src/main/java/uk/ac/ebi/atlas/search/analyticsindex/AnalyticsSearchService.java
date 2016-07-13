package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsSearchService {

    private AnalyticsClient analyticsClient;

    @Inject
    public AnalyticsSearchService(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public ImmutableSet<String> searchBioentityIdentifiers(GeneQuery geneQuery, String species) {
        QueryResponse queryResponse = analyticsClient.query(new AnalyticsQueryBuilder().queryIdentifierSearch(geneQuery).ofSpecies(species).setRows(0).facetByBioentityIdentifier().build());
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }
}
