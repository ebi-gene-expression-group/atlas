package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialAnalyticsSearchDao {

    private static final Logger LOGGER = Logger.getLogger(DifferentialAnalyticsSearchDao.class);

    public static final String DIFFERENTIAL_ONLY = "experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential)";
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private final RestTemplate restTemplate;

    private SolrServer analyticsSolrServer;
    private String solrBaseUrl;
    private final String differentialGenePivotQuery;

    @Inject
    // TODO Move buildQuery to json.facet and implement using restTemplate to remove analyticsSolrServer
    public DifferentialAnalyticsSearchDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, @Qualifier("analyticsSolrServer") SolrServer analyticsSolrServer, String differentialGenePivotQuery) {
        this.restTemplate = restTemplate;
        this.analyticsSolrServer = analyticsSolrServer;
        this.solrBaseUrl = solrBaseUrl;
        this.differentialGenePivotQuery = "&json.facet=" + encodeQueryParam(differentialGenePivotQuery);
    }


    public Multimap<String, NameValue> fetchFacets(GeneQuery geneQuery) {
        QueryResponse queryResponse = query(buildQuery(geneQuery));
        return extractFacetValues(queryResponse);
    }

    private ImmutableMultimap<String, NameValue> extractFacetValues(QueryResponse queryResponse) {
        ImmutableMultimap.Builder<String, NameValue> builder = ImmutableMultimap.builder();

        for (FacetField facetField : queryResponse.getFacetFields()) {
            String name = facetField.getName();

            for (FacetField.Count facetFieldCount : facetField.getValues()) {
                builder.put(name, NameValue.create(facetFieldCount.getName(), facetFieldCount.getName()));
            }
        }
        return builder.build();

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

    // TODO implement using restTemplate
    private SolrQuery buildQuery(GeneQuery geneQuery) {

        String identifierSearch = geneQuery.asString(); //TODO support multiple gene queries

        //eg: http://localhost:8983/solr/analytics/select?q=*%3A*&rows=0&fq=experimentType%3Arnaseq_mrna_differential+OR+experimentType%3Amicroarray_1colour_mrna_differential+or+experimentType%3Amicroarray_2colour_mrna_differential+or+experimentType%3Amicroarray_1colour_microrna_differential&wt=json&indent=true&facet=true&facet.field=kingdom&facet.field=species&facet.field=experimentType&facet.field=factors&facet.field=numReplicates&facet.field=regulation&facet.mincount=1&facet.limit=100
        SolrQuery solrQuery = new SolrQuery("identifierSearch:" + identifierSearch);
        solrQuery.setRows(0);
        solrQuery.setFilterQueries(DIFFERENTIAL_ONLY);
        solrQuery.setFacet(true);
        solrQuery.addFacetField("kingdom");
        solrQuery.addFacetField("species");
        solrQuery.addFacetField("experimentType");
        solrQuery.addFacetField("factors");
        solrQuery.addFacetField("numReplicates");
        solrQuery.addFacetField("regulation");
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(100);
        return solrQuery;
    }

    String fetchDifferentialGeneQueryResults(GeneQuery geneQuery) {
        return fetchDifferentialResults(buildGeneIdentifierQuery(geneQuery));
    }

    String buildGeneIdentifierQuery(GeneQuery geneQuery) {
        return geneQuery.isEmpty() ? "" : String.format("identifierSearch:(\"%s\")", StringUtils.join(geneQuery.terms(), "\" OR \""));
    }

    private String fetchDifferentialResults(String q) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialOnlyQueryUrl(q));

        stopwatch.stop();

        LOGGER.debug(String.format("fetchDifferentialGeneResults q=%s took %.2f seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
        return result;
    }

    String buildDifferentialOnlyQueryUrl(String q) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query) + differentialGenePivotQuery;
    }

    String buildQueryParameters(String q) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q));
    }

    private static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new DifferentialAnalyticsSearchDaoException(e);
        }
    }

    String fetchResponseAsString(String url) {
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
