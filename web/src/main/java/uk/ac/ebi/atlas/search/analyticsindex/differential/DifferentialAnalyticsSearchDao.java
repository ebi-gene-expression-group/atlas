package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import uk.ac.ebi.atlas.web.GeneQuery;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialAnalyticsSearchDao {

    private static final Logger LOGGER = Logger.getLogger(DifferentialAnalyticsSearchDao.class);

    public static final String DIFFERENTIAL_ONLY = "experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential)";
    public static final double DEFAULT_FOLD_CHANGE = 1.0;
    private static final String FQ_TEMPLATE = "&fq=foldChange:[{0} TO *]";
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private final RestTemplate restTemplate;

    private String solrBaseUrl;
    private final String differentialGenePivotQuery;
    private final String differentialGeneFacetsQuery;

    @Inject
    // TODO Move buildQuery to json.facet and implement using restTemplate to remove analyticsSolrServer
    public DifferentialAnalyticsSearchDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, String differentialGenePivotQuery, String differentialGeneFacetsQuery) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
        this.differentialGenePivotQuery = "&json.facet=" + encodeQueryParam(differentialGenePivotQuery);
        this.differentialGeneFacetsQuery ="&json.facet=" + encodeQueryParam(differentialGeneFacetsQuery);
    }


    String fetchFacets(GeneQuery geneQuery) {
        return fetchFacetsAboveDefaultFoldChange(geneQuery);
    }


    private String fetchFacetsAboveDefaultFoldChange(GeneQuery geneQuery) {
        String identifierSearch = buildGeneIdentifierQuery(geneQuery);
        return fetchFacetsAboveFoldChange(identifierSearch, DEFAULT_FOLD_CHANGE);
    }


    private String fetchFacetsAboveFoldChange(String q, double foldChange) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialFacetsAboveFoldChangeQueryUrl(q, foldChange));

        stopwatch.stop();
        LOGGER.debug(String.format("fetchFacetsAboveFoldChange q=%s foldChange=%s took %.2f seconds", q, foldChange, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return result;
    }


    private String buildDifferentialFacetsAboveFoldChangeQueryUrl(String q, double foldChange) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query, foldChange) + differentialGeneFacetsQuery;
    }


    String fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(GeneQuery geneQuery) {
        return fetchDifferentialResultsAboveDefaultFoldChange(buildGeneIdentifierQuery(geneQuery));
    }

    String fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdom,
                                                                   List<String> factors, List<Integer> numReplicates, String regulation) {
        String query = buildGeneIdentifierQuery(geneQuery);

        return fetchDifferentialResultsAboveDefaultFoldChange(query, species, experimentType, kingdom, factors, numReplicates, regulation);
    }


    private String fetchDifferentialResultsAboveDefaultFoldChange(String q) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialResultsAboveDefaultFoldChangeUrl(q));

        stopwatch.stop();
        LOGGER.debug(String.format("fetchDifferentialGeneResults q=%s took %.2f seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return result;
    }

    private String fetchDifferentialResultsAboveDefaultFoldChange(String q, List<String> species, List<String> experimentType, List<String> kingdom,
                                                                            List<String> factors, List<Integer> numReplicates, String regulation) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialResultsAboveDefaultFoldChangeUrl(q, species, experimentType, kingdom, factors, numReplicates, regulation));

        stopwatch.stop();
        LOGGER.debug(String.format("fetchDifferentialGeneResults q=%s took %.2f seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return result;
    }


    private String buildGeneIdentifierQuery(GeneQuery geneQuery) {
        return geneQuery.isEmpty() ? "" : String.format("identifierSearch:(\"%s\")", StringUtils.join(geneQuery.terms(), "\" OR \""));
    }

    private String buildParamsQuery(List<String> params, String name) {
        return (params != null && !params.isEmpty()) ? String.format(" AND %s :(\"%s\")", name, StringUtils.join(params, "\" OR \"")) : "";
    }

    private String buildDifferentialResultsAboveDefaultFoldChangeUrl(String q) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;

        return solrBaseUrl + buildQueryParameters(query, DEFAULT_FOLD_CHANGE) + differentialGenePivotQuery;
    }

    private String buildDifferentialResultsAboveDefaultFoldChangeUrl(String q, List<String> species, List<String> experimentTypes, List<String> kingdoms,
                                                                     List<String> factors, List<Integer> numReplicates, String regulation) {
        String query;

        if(q.isEmpty() && experimentTypes == null) {
            query = DIFFERENTIAL_ONLY;
        } else if (!q.isEmpty() && experimentTypes == null) {
            query = q + " AND " + DIFFERENTIAL_ONLY;
        } else {
            query = q + buildParamsQuery(experimentTypes, "experimentType");
        }

        query = query + buildParamsQuery(species, "species");
        query = query + buildParamsQuery(factors, "factors");
        query = query + buildParamsQuery(kingdoms, "kingdom");

        if(numReplicates != null) {
            query = query + String.format(" AND numReplicates:(\"%s\")", StringUtils.join(numReplicates, "\" OR \""));
        }
        if(StringUtils.isNotBlank(regulation)) {
            query = query + " AND regulation:" + regulation;
        }

        return solrBaseUrl + buildQueryParameters(query, DEFAULT_FOLD_CHANGE) + differentialGenePivotQuery;
    }


    private String buildQueryParameters(String q, double foldChange) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q)) + encodeQuery(MessageFormat.format(FQ_TEMPLATE, foldChange));
    }


    private static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new DifferentialAnalyticsSearchDaoException(e);
        }
    }


    private static String encodeQuery(String s) {
        // doesn't encode =
        try {
            return UriUtils.encodeQuery(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new DifferentialAnalyticsSearchDaoException(e);
        }
    }


    private String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new DifferentialAnalyticsSearchDaoException(e);
        }
    }


    private static class DifferentialAnalyticsSearchDaoException extends RuntimeException {
        public DifferentialAnalyticsSearchDaoException(Exception e) {
            super(e);
        }
    }
}
