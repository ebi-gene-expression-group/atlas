package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
import java.util.concurrent.TimeUnit;

@Named
public class BaselineAnalyticsSearchDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsSearchDao.class);

    public static final double DEFAULT_BASELINE_CUT_OFF = 0.5;
    public static final double DEFAULT_PROTEOMICS_CUT_OFF = 0;
    private final RestTemplate restTemplate;

    private final String solrBaseUrl;
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private static final String FQ_TEMPLATE = "&fq=" +
            "(experimentType:rnaseq_mrna_baseline AND expressionLevel:[{0} TO *]) " +
            "OR (experimentType:proteomics_baseline AND expressionLevel:[{1} TO *])";
    private final String baselineHeatmapPivotQuery;

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, String baselineHeatmapPivotQuery) {
        this.restTemplate = restTemplate;   // settings of restTemplate in applicationContext.xml
        this.solrBaseUrl = solrBaseUrl;
        this.baselineHeatmapPivotQuery = "&json.facet=" + encodeQueryParam(baselineHeatmapPivotQuery.replaceAll("\\s+",""));
    }


    public String fetchFacetsThatHaveExpression(GeneQuery geneQuery) {
        //if needed, could improve performance by getting counts only, and not sum(expressionLevel) or unique(bioentityIdentifier)
        return fetchExpressionLevelFaceted(geneQuery);
    }


    String fetchExpressionLevelFaceted(GeneQuery geneQuery) {
        String identifierSearch = buildGeneIdentifierQuery(geneQuery);
        return fetchFacets(identifierSearch, DEFAULT_BASELINE_CUT_OFF, DEFAULT_PROTEOMICS_CUT_OFF);
    }


    String buildGeneIdentifierQuery(GeneQuery geneQuery) {
        return geneQuery.isEmpty() ? "" : String.format("identifierSearch:(\"%s\")", StringUtils.join(geneQuery.terms(), "\" OR \""));
    }


    public String fetchExpressionLevelFaceted(GeneQuery geneQuery, String defaultQueryFactorType) {
        String identifierSearch = buildGeneIdentifierQuery(geneQuery);
        return fetchFacets(String.format("%s AND defaultQueryFactorType:%s", identifierSearch, defaultQueryFactorType), DEFAULT_BASELINE_CUT_OFF, DEFAULT_PROTEOMICS_CUT_OFF);
    }


    String fetchFacets(String q, double baselineCutOff, double proteomicsCutOff) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildQueryUrl(q, baselineCutOff, proteomicsCutOff));

        stopwatch.stop();

        LOGGER.debug("fetchFacets q={} baselineCutOff={} proteomicsCutOff={} took {} seconds", q, baselineCutOff, proteomicsCutOff, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;

    }


    String buildQueryUrl(String q, double baselineCutOff, double proteomicsCutOff) {
        String query = q.isEmpty() ? "*:*" : q;
        return solrBaseUrl + buildQueryParameters(query, baselineCutOff, proteomicsCutOff) + baselineHeatmapPivotQuery;
    }


    String buildQueryParameters(String q, double baselineCutOff, double proteomicsCutOff) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q)) + encodeQuery(MessageFormat.format(FQ_TEMPLATE, baselineCutOff, proteomicsCutOff));
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
