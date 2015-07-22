package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class AnalyticsSearchDao {
    public static final String ABOVE_CUTOFF = "(((experimentType:rnaseq_mrna_baseline OR experimentType:proteomics_baseline) AND expressionLevel:[0.5 TO *]) OR ((experimentType:rnaseq_mrna_differential OR experimentType:microarray_1colour_mrna_differential or experimentType:microarray_2colour_mrna_differential or experimentType:microarray_1colour_microrna_differential) AND foldChange:[1.0 TO *]))";

    private static final Logger LOGGER = Logger.getLogger(AnalyticsSearchDao.class);

    private SolrClient analyticsSolrClient;

    @Inject
    public AnalyticsSearchDao(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient) {
        this.analyticsSolrClient = analyticsSolrClient;
    }


    public ImmutableSet<String> fetchExperimentTypes(GeneQuery geneQuery) {

        QueryResponse queryResponse = query(buildQuery(geneQuery));

        ImmutableSet<String> experimentTypes = SolrUtil.extractFirstFacetValues(queryResponse);

        return experimentTypes;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = analyticsSolrClient.query(solrQuery);
            //LOGGER.debug("<query> Solr query time: " + queryResponse.getQTime() + "ms, status code: " + queryResponse.getStatus());
            return queryResponse;
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    private SolrQuery buildQuery(GeneQuery geneQuery) {
        String identifierSearch = geneQuery.asString(); //TODO: support multiple gene query terms
        SolrQuery solrQuery = new SolrQuery("identifierSearch:" + identifierSearch);
        solrQuery.setRows(0);
        solrQuery.setFilterQueries(ABOVE_CUTOFF);
        solrQuery.setFacet(true);
        solrQuery.addFacetField("experimentType");
        solrQuery.setFacetMinCount(1);
        return solrQuery;
    }

}
