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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static uk.ac.ebi.atlas.utils.StringUtil.quote;

@Named
public class AnalyticsSearchDao {
    public static final String ABOVE_CUTOFF = "(" +
                                               "(experimentType:(rnaseq_mrna_baseline OR proteomics_baseline) AND expressionLevel:[0.5 TO *]) OR " +
                                               "(experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential) AND foldChange:([1.0 TO *] OR [* TO -1.0]))" +
                                              ")";

    private static final Logger LOGGER = Logger.getLogger(AnalyticsSearchDao.class);

    private SolrClient analyticsSolrClient;

    @Inject
    public AnalyticsSearchDao(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient) {
        this.analyticsSolrClient = analyticsSolrClient;
    }


    public ImmutableSet<String> fetchExperimentTypes(GeneQuery geneQuery) {
        QueryResponse queryResponse = query(buildQuery(geneQuery));
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            return analyticsSolrClient.query(solrQuery);
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    private SolrQuery buildQuery(GeneQuery geneQuery) {

        StringBuilder sb = new StringBuilder("identifierSearch:(");
        if (geneQuery.terms().size() > 0) {
            for (int i = 0 ; i < geneQuery.terms().size() - 1 ; i++) {
                sb.append(quote(geneQuery.terms().get(i))).append(" OR ");
            }
            sb.append(quote(geneQuery.terms().get(geneQuery.terms().size() - 1)));
        }
        sb.append(")");

        SolrQuery solrQuery = new SolrQuery(sb.toString());

        solrQuery.setRows(0);
        solrQuery.setFilterQueries(ABOVE_CUTOFF);
        solrQuery.setFacet(true);
        solrQuery.addFacetField("experimentType");
        solrQuery.setFacetMinCount(1);
        return solrQuery;

    }
}
