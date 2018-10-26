package uk.ac.ebi.atlas.solr.analytics.query;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnalyticsQueryClientIT {
    @Value("classpath:/solr-queries/baseline.heatmap.pivot.query.json")
    private Resource baselineFacetsQueryJSON;

    @Value("classpath:/solr-queries/differential.facets.query.json")
    private Resource differentialFacetsQueryJSON;

    @Value("classpath:/solr-queries/experimentType.query.json")
    private Resource experimentTypesQueryJson;

    @Value("classpath:/solr-queries/bioentityIdentifier.query.json")
    private Resource bioentityIdentifiersQueryJson;

    @Mock
    private RestTemplate restTemplate;

    @Inject
    private AnalyticsQueryClient goodSubject;

    private AnalyticsQueryClient subject;

    @BeforeEach
    void setUp() {
        subject =
                new TestableAnalyticsQueryClient(
                        restTemplate,
                        null,
                        null,
                        baselineFacetsQueryJSON,
                        differentialFacetsQueryJSON,
                        experimentTypesQueryJson,
                        bioentityIdentifiersQueryJson);
    }

    @Test
    void queryWithCategory() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create(SemanticQueryTerm.create("GO:1234567", "go")))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade).contains("keyword_go");
        assertThat(queryMade).contains("\"GO:1234567\"");
        assertThat(queryMade).contains("E-MTAB-513");
    }

    @Test
    void queryWithNoCategoryButObviouslyAnEnsemblIdDoesABioentityIdentifierQuery() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create(SemanticQueryTerm.create("ENSG00000006432")))
                .fetch();

        assertThat(queryMade).contains("ENSG00000006432");
        assertThat(queryMade).doesNotContain("keyword_");
        //assertThat(queryMade).doesNotContain("identifier_search");
        assertThat(queryMade).contains("bioentity_identifier");

    }

    @Test
    void speciesComeInQuoted() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .ofSpecies("oryza sativa")
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade).contains("\"oryza sativa\"");
    }

    @Test
    void weGuessThatZincFingerCanNotBeAKeyword() {

        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade).doesNotContain("keyword_");
        assertThat(queryMade).contains("identifier_search");
        assertThat(queryMade).contains("zinc finger");
        assertThat(queryMade).contains("E-MTAB-513");
    }

    @Test
    void defaultQueryIsTheSolrDefault() {
        String queryMade = subject.queryBuilder().fetch();
        assertThat(queryMade).contains("q=*:*");
    }

    @Test
    void omitEmptyConditionQuery() {
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .queryConditionsSearch(SemanticQuery.create())
                .inExperiment("E-MTAB-513")
                .fetch();

        assertThat(queryMade).doesNotContain("keyword_");
        assertThat(queryMade).contains("identifier_search");
        assertThat(queryMade).contains("zinc finger");
        assertThat(queryMade).contains("E-MTAB-513");

        assertThat(queryMade).doesNotContain("conditionsSearch");
    }

    @Test
    void bothConditionQueryAndIdentifierSearchMakeItIntoTheQueryString() {
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierSearch(SemanticQuery.create("zinc finger"))
                .queryConditionsSearch(SemanticQuery.create("liver"))
                .fetch();

        assertThat(queryMade).doesNotContain("keyword_");
        assertThat(queryMade).contains("identifier_search");
        assertThat(queryMade).contains("zinc finger");

        assertThat(queryMade).contains("conditions_search");
        assertThat(queryMade).contains("liver");
    }

    @Test
    void queryConditionSearchOrIdentifierSearchIncludesTheQueryStringTwiceForQueriesWithNoCategory() {
        String queryMade = subject.queryBuilder()
                .bioentityIdentifierFacets(-1)
                .queryIdentifierOrConditionsSearch(SemanticQuery.create("tasty pancake"))
                .fetch();

        assertThat(queryMade).doesNotContain("keyword_"); //two words so this is not a keyword
        assertThat(queryMade).contains("identifier_search");
        assertThat(queryMade).contains("conditions_search");
        assertThat(queryMade.split("tasty pancake").length).isGreaterThan(2);
    }
    
    @Test
    void baselineFacetsOnlyReturnsBaselineExperiments() {
        String queryResponse = goodSubject.queryBuilder()
                .baselineFacets()
                .queryIdentifierOrConditionsSearch(SemanticQuery.create("lung"))
                .fetch();

        List<String> experimentTypes =
                JsonPath.using(Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS))
                        .parse(queryResponse)
                        .read("$.facets.experimentType.buckets[*].val");

        // If we want to go to an extra level of correctness we can get the experiment type from the DB, but if the
        // type isn’t right in Solr our problems are going to be bigger than this test
        assertThat(experimentTypes)
                .isNotEmpty()
                .allMatch(type -> ExperimentType.get(type).isBaseline());
    }

    class TestableAnalyticsQueryClient extends AnalyticsQueryClient {
        TestableAnalyticsQueryClient(RestTemplate restTemplate,
                                     String solrBaseUrl,
                                     String solrPort,
                                     Resource baselineFacetsQueryJSON,
                                     Resource differentialFacetsQueryJSON,
                                     Resource experimentTypesQueryJson,
                                     Resource bioentityIdentifiersQueryJson) {
            super(restTemplate,
                  solrBaseUrl,
                  solrPort,
                  baselineFacetsQueryJSON,
                  differentialFacetsQueryJSON,
                  experimentTypesQueryJson,
                  bioentityIdentifiersQueryJson);
        }

        @Override
        protected String fetchResponseAsString(String url, SolrQuery q) {
            try {
                return URLDecoder.decode(q.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }

        @Override
        protected boolean responseNonEmpty(String jsonFromSolr) {
            return true;
        }
    }
}
