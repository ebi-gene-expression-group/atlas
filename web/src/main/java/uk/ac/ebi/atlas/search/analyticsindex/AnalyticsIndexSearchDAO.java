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
public class AnalyticsIndexSearchDAO {

    private AnalyticsClient analyticsClient;

    @Inject
    public AnalyticsIndexSearchDAO(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public ImmutableSet<String> fetchExperimentTypes(GeneQuery geneQuery) {
        SolrQuery solrQuery =
               new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .facetByExperimentType()
                        .filterAboveDefaultCutoff()
                        .setRows(0)
                        .build();
        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

    public ImmutableSet<String> fetchExperimentTypes(String bioentityIdentifier) {
        SolrQuery solrQuery =
                new AnalyticsQueryBuilder()
                        .queryBioentityIdentifier(bioentityIdentifier)
                        .facetByExperimentType()
                        .filterAboveDefaultCutoff()
                        .setRows(0)
                        .build();
        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

}
