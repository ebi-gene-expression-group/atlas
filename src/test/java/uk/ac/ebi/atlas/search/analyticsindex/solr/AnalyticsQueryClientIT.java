package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Throwables;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})


public class AnalyticsQueryClientIT {

    @Qualifier("solrAnalyticsServerURL") String solrBaseUrl;
    @Value("classpath:/solr/baseline.heatmap.pivot.query.json")
    Resource baselineFacetsQueryJSON;
    @Value("classpath:/solr/differential.facets.query.json") Resource differentialFacetsQueryJSON;
    @Value("classpath:/solr/experimentType.query.json") Resource experimentTypesQueryJson;
    @Value("classpath:/solr/bioentityIdentifier.query.json") Resource bioentityIdentifiersQueryJson;

    @Mock
    RestTemplate restTemplate;

    AnalyticsQueryClient subject;

    @Before
    public void setUp(){
        subject = new TestableAnalyticsQueryClient(restTemplate, solrBaseUrl, baselineFacetsQueryJSON,
                differentialFacetsQueryJSON,experimentTypesQueryJson,bioentityIdentifiersQueryJson);
    }

    @Test
    public void queryWithCategory() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create(SemanticQueryTerm.create("GO:1234567","go")))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, containsString("keyword_go"));
        assertThat(queryMade, containsString("\"GO:1234567\""));
        assertThat(queryMade, containsString("E-MTAB-513"));
    }

    @Test
    public void queryWithNoCategoryAlsoPicksUpAKeywordTerm() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create(SemanticQueryTerm.create("GO:1234567")))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, containsString("keyword_go"));
        assertThat(queryMade, containsString("\"GO:1234567\""));
        assertThat(queryMade, containsString("E-MTAB-513"));
    }

    @Test
    public void weGuessThatZincFingerCanNotBeAKeyword() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, not(containsString("keyword_")));
        assertThat(queryMade, containsString("zinc finger"));
        assertThat(queryMade, containsString("E-MTAB-513"));
    }



    class TestableAnalyticsQueryClient extends AnalyticsQueryClient{

        public TestableAnalyticsQueryClient(RestTemplate restTemplate, String solrBaseUrl, Resource baselineFacetsQueryJSON, Resource differentialFacetsQueryJSON,Resource experimentTypesQueryJson, Resource bioentityIdentifiersQueryJson) {
            super(restTemplate, solrBaseUrl, baselineFacetsQueryJSON, differentialFacetsQueryJSON, experimentTypesQueryJson, bioentityIdentifiersQueryJson);
        }

        @Override
        String fetchResponseAsString(String url) {
            try {
                return URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }

        @Override
        boolean responseNonEmpty(String jsonFromSolr){
            return true;
        }
    }



}