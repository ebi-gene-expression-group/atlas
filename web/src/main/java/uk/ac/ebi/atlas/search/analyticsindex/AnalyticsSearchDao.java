package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.solr.SolrUtil;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsSearchDao {
    public static final String ABOVE_CUTOFF = "((experimentType:rnaseq_mrna_baseline OR experimentType:proteomics_baseline) AND expressionLevel:[0.5 TO *]) OR ((experimentType:rnaseq_mrna_differential OR experimentType:microarray_1colour_mrna_differential or experimentType:microarray_2colour_mrna_differential or experimentType:microarray_1colour_microrna_differential) AND foldChange:[1.0 TO *])";

    private static final Logger LOGGER = Logger.getLogger(AnalyticsSearchDao.class);

    private SolrServer analyticsSolrServer;

    @Inject
    public AnalyticsSearchDao(@Qualifier("analyticsSolrServer") SolrServer analyticsSolrServer) {
        this.analyticsSolrServer = analyticsSolrServer;
    }


    public ImmutableSet<String> fetchExperimentTypes(String geneQuery) {

        QueryResponse queryResponse = query(buildQuery(geneQuery));

        ImmutableSet<String> experimentTypes = SolrUtil.extractFirstFacetValues(queryResponse);

        return experimentTypes;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = analyticsSolrServer.query(solrQuery);
            //LOGGER.debug("<query> Solr query time: " + queryResponse.getQTime() + "ms, status code: " + queryResponse.getStatus());
            return queryResponse;
        } catch (SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        } catch (SolrException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private SolrQuery buildQuery(String geneQuery) {
        SolrQuery solrQuery = new SolrQuery("identifierSearch:" + geneQuery);
        solrQuery.setRows(0);
        solrQuery.setFilterQueries(ABOVE_CUTOFF);
        solrQuery.setFacet(true);
        solrQuery.addFacetField("experimentType");
        solrQuery.setFacetMinCount(1);
        return solrQuery;
    }

}
