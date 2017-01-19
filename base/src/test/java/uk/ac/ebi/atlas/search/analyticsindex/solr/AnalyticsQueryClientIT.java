package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class AnalyticsQueryClientIT {

    @Qualifier("solrAnalyticsServerURL")
    private String solrBaseUrl;

    @Value("classpath:/solr/conf")
    Resource solrConf;

    @Value("classpath:/solr-queries/baseline.heatmap.pivot.query.json")
    Resource baselineFacetsQueryJSON;

    @Value("classpath:/solr-queries/differential.facets.query.json")
    Resource differentialFacetsQueryJSON;

    @Value("classpath:/solr-queries/experimentType.query.json")
    Resource experimentTypesQueryJson;

    @Value("classpath:/solr-queries/bioentityIdentifier.query.json")
    Resource bioentityIdentifiersQueryJson;

    @Mock
    private RestTemplate restTemplate;

    private AnalyticsQueryClient subject;

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
    public void queryWithNoCategoryButObviouslyAnEnsemblIdDoesABioentityIdentifierQuery() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create(SemanticQueryTerm.create("ENSG00000006432")))
                .fetch();

        assertThat(queryMade, containsString("ENSG00000006432"));
        assertThat(queryMade, not(containsString("keyword_")));
        assertThat(queryMade, not(containsString("identifierSearch")));
        assertThat(queryMade, containsString("bioentityIdentifier"));

    }

    @Test
    public void speciesComeInQuoted() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .ofSpecies("oryza sativa")
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, containsString("\"oryza sativa\""));
    }

    @Test
    public void weGuessThatZincFingerCanNotBeAKeyword() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, not(containsString("keyword_")));
        assertThat(queryMade, containsString("identifierSearch"));
        assertThat(queryMade, containsString("zinc finger"));
        assertThat(queryMade, containsString("E-MTAB-513"));
    }

    @Test
    public void defaultQueryIsTheSolrDefault(){
        String queryMade = subject.queryBuilder().fetch();
        assertThat(queryMade, containsString("q=*:*"));
    }

    @Test
    public void omitEmptyConditionQuery(){
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .queryConditionsSearch(SemanticQuery.create())
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade, not(containsString("keyword_")));
        assertThat(queryMade, containsString("identifierSearch"));
        assertThat(queryMade, containsString("zinc finger"));
        assertThat(queryMade, containsString("E-MTAB-513"));

        assertThat(queryMade, not(containsString("conditionsSearch")));
    }

    @Test
    public void bothConditionQueryAndIdentifierSearchMakeItIntoTheQueryString(){
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .queryConditionsSearch(SemanticQuery.create("liver"))
                .fetch();

        assertThat(queryMade, not(containsString("keyword_")));
        assertThat(queryMade, containsString("identifierSearch"));
        assertThat(queryMade, containsString("zinc finger"));

        assertThat(queryMade, containsString("conditionsSearch"));
        assertThat(queryMade, containsString("liver"));
    }

    @Test
    public void queryConditionSearchOrIdentifierSearchIncludesTheQueryStringTwiceForQueriesWithNoCategory(){
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierOrConditionsSearch(SemanticQuery.create("tasty pancake"))
                .fetch();

        assertThat(queryMade, not(containsString("keyword_"))); //two words so this is not a keyword
        assertThat(queryMade, containsString("identifierSearch"));
        assertThat(queryMade, containsString("conditionsSearch"));
        assertThat(queryMade.split("tasty pancake").length, greaterThan(2));
    }




    class TestableAnalyticsQueryClient extends AnalyticsQueryClient {

        public TestableAnalyticsQueryClient(RestTemplate restTemplate, String solrBaseUrl, Resource baselineFacetsQueryJSON, Resource differentialFacetsQueryJSON,Resource experimentTypesQueryJson, Resource bioentityIdentifiersQueryJson) {
            super(restTemplate, solrBaseUrl, baselineFacetsQueryJSON, differentialFacetsQueryJSON, experimentTypesQueryJson, bioentityIdentifiersQueryJson);
        }

        @Override
        protected String fetchResponseAsString(String url, SolrQuery q) {
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