package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 19/10/15.
 */
@Named
@Scope("singleton")
public class AnalyticsSearchDAO {

    private AnalyticsClient analyticsClient;
    private AnalyticsQueryBuilder analyticsQueryBuilder;

    @Inject
    public AnalyticsSearchDAO(AnalyticsClient analyticsClient, AnalyticsQueryBuilder analyticsQueryBuilder) {
        this.analyticsClient = analyticsClient;
        this.analyticsQueryBuilder = analyticsQueryBuilder;
    }

    public ImmutableSet<String> fetchExperimentTypes(GeneQuery geneQuery) {
        SolrQuery solrQuery =
                analyticsQueryBuilder
                        .queryIdentifierSearch(geneQuery)
                        .facetByExperimentType()
                        .filterAboveDefaultCutoff()
                        .setRows(0)
                        .build();
        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }
}
