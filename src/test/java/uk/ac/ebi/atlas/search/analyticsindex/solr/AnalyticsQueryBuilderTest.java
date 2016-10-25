package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.OR;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory.Field.CONDITIONS_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory.Field.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory.Field.SPECIES;

public class AnalyticsQueryBuilderTest {

    private final String HOMO_SAPIENS = "homo sapiens";
    private final SemanticQuery IDENTIFIER_QUERY = SemanticQuery.create(SemanticQueryTerm.create("PIM1", "symbol"), SemanticQueryTerm.create("GO:0008150", "go"));
    private final SemanticQuery CONDITIONS_QUERY = SemanticQuery.create("liver");


    @Mock
    Resource  baselineFacetsQueryJSON;

    @Mock
    Resource differentialFacetsQueryJSON;

    private AnalyticsQueryFactory analyticsQueryFactory;

    private AnalyticsQueryFactory.Builder subject;


    @Before
    public void setUp(){
        analyticsQueryFactory = new AnalyticsQueryFactory(baselineFacetsQueryJSON,differentialFacetsQueryJSON);
    }

    @Test
    public void defaultQuery() {
        subject = analyticsQueryFactory.builder();
        assertThat(subject.build().getQuery(), is("*:*"));
    }

    @Test
    public void queryIdentifierSearchOnly() {
        subject = analyticsQueryFactory.builder().queryIdentifierSearch(IDENTIFIER_QUERY);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void queryConditionsSearchOnly() {
        subject = analyticsQueryFactory.builder().queryConditionsSearch(CONDITIONS_QUERY);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(), CONDITIONS_QUERY).toString()));
    }

    @Test
    public void querySpeciesOnly() {
        subject = analyticsQueryFactory.builder().ofSpecies(HOMO_SAPIENS);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQueryTree(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS)).toString()));
    }

    @Test
    public void fullQuery() {
        subject = analyticsQueryFactory.builder().queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(CONDITIONS_QUERY).ofSpecies(HOMO_SAPIENS);
        assertThat(
                subject.build().getQuery(),
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
        subject = analyticsQueryFactory.builder().queryIdentifierOrConditionsSearch(IDENTIFIER_QUERY).ofSpecies(HOMO_SAPIENS);
        assertThat(
                subject.build().getQuery(),
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
        subject = analyticsQueryFactory.builder().queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(SemanticQuery.create());
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void omitBlankSpecies() {
        subject = analyticsQueryFactory.builder().queryIdentifierSearch(IDENTIFIER_QUERY).ofSpecies("");
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

}
