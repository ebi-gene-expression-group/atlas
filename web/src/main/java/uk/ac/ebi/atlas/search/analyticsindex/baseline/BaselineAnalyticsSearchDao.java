package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.base.Stopwatch;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Named
public class BaselineAnalyticsSearchDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsSearchDao.class);

    public static final double DEFAULT_BASELINE_CUT_OFF = 0.5;
    public static final double DEFAULT_PROTEOMICS_CUT_OFF = 0;
    private final RestTemplate restTemplate;

    private final String solrBaseUrl;
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private static final String FQ_TEMPLATE = "&fq=experimentType:(rnaseq_mrna_baseline OR proteomics_baseline)";

    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[?(@.val=='%s')].defaultQueryFactorType.buckets[?(@.val=='%s')].experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";


    private final String baselineHeatmapPivotQuery;

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, String baselineHeatmapPivotQuery) {
        this.restTemplate = restTemplate;   // settings of restTemplate in applicationContext.xml
        this.solrBaseUrl = solrBaseUrl;
        this.baselineHeatmapPivotQuery = "&json.facet=" + encodeQueryParam(baselineHeatmapPivotQuery.replaceAll("\\s+",""));
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(GeneQuery geneQuery) {
        String response = fetchFacets(buildGeneIdentifierQuery(geneQuery));
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    String buildGeneIdentifierQuery(GeneQuery geneQuery) {
        return geneQuery.isEmpty() ? "" : String.format("identifierSearch:(%s)", geneQuery.asSolr1DNF());
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(GeneQuery geneQuery, String species, String defaultQueryFactorType) {
        String identifierSearch = buildGeneIdentifierQuery(geneQuery);
        String response = fetchFacets(String.format("%s AND defaultQueryFactorType:%s", identifierSearch,
                defaultQueryFactorType));

        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType));
    }

    private String fetchFacets(String q) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildQueryUrl(q));

        stopwatch.stop();

        LOGGER.debug("fetchFacets q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;

    }

    String buildQueryUrl(String q) {
        String query = q.isEmpty() ? "*:*" : q;
        return solrBaseUrl + buildQueryParameters(query) + baselineHeatmapPivotQuery;
    }

    String buildQueryParameters(String q) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q)) + encodeQuery(FQ_TEMPLATE);
    }

    String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new BaselineAnalyticsSearchDaoException(e);
        }
    }

    private static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaselineAnalyticsSearchDaoException(e);
        }
    }

    private static String encodeQuery(String s) {
        // doesn't encode =
        try {
            return UriUtils.encodeQuery(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaselineAnalyticsSearchDaoException(e);
        }
    }

    private static class BaselineAnalyticsSearchDaoException extends RuntimeException {
        public BaselineAnalyticsSearchDaoException(Exception e) {
            super(e);
        }
    }

}
