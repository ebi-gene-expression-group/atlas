package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient.Field.*;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.OR;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsQueryBuilderTest {

    private final String HOMO_SAPIENS = "homo sapiens";
    private final SemanticQuery IDENTIFIER_QUERY = SemanticQuery.create(SemanticQueryTerm.create("PIM1", "symbol"), SemanticQueryTerm.create("GO:0008150", "go"));
    private final SemanticQuery CONDITIONS_QUERY = SemanticQuery.create("liver");


    @Mock
    Resource  resource;

    @Mock
    RestTemplate restTemplate;


    private AnalyticsQueryClient analyticsQueryClient;

    private AnalyticsQueryClient.Builder subject;


    @Before
    public void setUp(){
        when(restTemplate.getForObject(Matchers.any(URI.class), Matchers.eq(String.class))).thenReturn("{response:{numFound:17}}");
        analyticsQueryClient = new AnalyticsQueryClient(restTemplate,"", resource,resource,resource,resource);
        subject = analyticsQueryClient.queryBuilder();
    }

    @Test
    public void defaultQuery() {

        subject.fetch();
        assertThat(subject.solrQuery.getQuery(), is("*:*"));
    }

    @Test
    public void queryIdentifierSearchOnly() {

        subject.queryIdentifierSearch(IDENTIFIER_QUERY).fetch();
        assertThat(subject.solrQuery.getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(),
                IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void queryConditionsSearchOnly() {
        subject.queryConditionsSearch(CONDITIONS_QUERY).fetch();

        assertThat(subject.solrQuery.getQuery(), is(new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(),
                CONDITIONS_QUERY).toString()));
    }

    @Test
    public void querySpeciesOnly() {
        subject.ofSpecies(HOMO_SAPIENS).fetch();

        assertThat(subject.solrQuery.getQuery(), is(new AnalyticsSolrQueryTree(SPECIES.toString(), SemanticQuery.create
                (HOMO_SAPIENS)).toString()));
    }

    @Test
    public void fullQuery() {
        subject.queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(CONDITIONS_QUERY).ofSpecies
                (HOMO_SAPIENS).fetch();
        assertThat(
                subject.solrQuery.getQuery(),
                is(
                        new AnalyticsSolrQueryTree(
                                AND,
                                new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY),
                                new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(), CONDITIONS_QUERY),
                                new AnalyticsSolrQueryTree(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS))
                        ).toString()
                )
        );
    }

    @Test
    public void fullQueryWithOr() {
        subject.queryIdentifierOrConditionsSearch(IDENTIFIER_QUERY).ofSpecies(HOMO_SAPIENS).fetch();
        assertThat(
                subject.solrQuery.getQuery(),
                is(
                        new AnalyticsSolrQueryTree(
                                AND,
                                new AnalyticsSolrQueryTree(
                                        OR,
                                        new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY),
                                        new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(), IDENTIFIER_QUERY)
                                ),
                                new AnalyticsSolrQueryTree(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS))
                        ).toString()
                )
        );
    }

    @Test
    public void omitEmptyClause() {
        subject.queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(SemanticQuery.create()).fetch();
        assertThat(subject.solrQuery.getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(),
                IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void omitBlankSpecies() {
        subject.queryIdentifierSearch(IDENTIFIER_QUERY).ofSpecies("").fetch();
        assertThat(subject.solrQuery.getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(),
                IDENTIFIER_QUERY).toString()));
    }

}
