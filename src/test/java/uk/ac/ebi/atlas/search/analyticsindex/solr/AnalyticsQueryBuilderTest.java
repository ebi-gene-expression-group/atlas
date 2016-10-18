package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.OR;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.CONDITIONS_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.SPECIES;

public class AnalyticsQueryBuilderTest {

    private final String HOMO_SAPIENS = "homo sapiens";
    private final SemanticQuery IDENTIFIER_QUERY = SemanticQuery.create(SemanticQueryTerm.create("PIM1", "symbol"), SemanticQueryTerm.create("GO:0008150", "go"));
    private final SemanticQuery CONDITIONS_QUERY = SemanticQuery.create("liver");

    private AnalyticsQueryBuilder subject;

    @Test
    public void defaultQuery() {
        subject = new AnalyticsQueryBuilder();
        assertThat(subject.build().getQuery(), is("*:*"));
    }

    @Test
    public void queryIdentifierSearchOnly() {
        subject = new AnalyticsQueryBuilder().queryIdentifierSearch(IDENTIFIER_QUERY);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void queryConditionsSearchOnly() {
        subject = new AnalyticsQueryBuilder().queryConditionsSearch(CONDITIONS_QUERY);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQuery(CONDITIONS_SEARCH.toString(), CONDITIONS_QUERY).toString()));
    }

    @Test
    public void querySpeciesOnly() {
        subject = new AnalyticsQueryBuilder().ofSpecies(HOMO_SAPIENS);
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQuery(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS)).toString()));
    }

    @Test
    public void fullQuery() {
        subject = new AnalyticsQueryBuilder().queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(CONDITIONS_QUERY).ofSpecies(HOMO_SAPIENS);
        assertThat(
                subject.build().getQuery(),
                is(
                        new AnalyticsSolrQuery(
                                AND,
                                new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY),
                                new AnalyticsSolrQuery(CONDITIONS_SEARCH.toString(), CONDITIONS_QUERY),
                                new AnalyticsSolrQuery(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS))
                        ).toString()
                )
        );
    }

    @Test
    public void fullQueryWithOr() {
        subject = new AnalyticsQueryBuilder().queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(CONDITIONS_QUERY).ofSpecies(HOMO_SAPIENS).useOr();
        assertThat(
                subject.build().getQuery(),
                is(
                        new AnalyticsSolrQuery(
                                OR,
                                new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY),
                                new AnalyticsSolrQuery(CONDITIONS_SEARCH.toString(), CONDITIONS_QUERY),
                                new AnalyticsSolrQuery(SPECIES.toString(), SemanticQuery.create(HOMO_SAPIENS))
                        ).toString()
                )
        );
    }

    @Test
    public void omitEmptyClause() {
        subject = new AnalyticsQueryBuilder().queryIdentifierSearch(IDENTIFIER_QUERY).queryConditionsSearch(SemanticQuery.create());
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

    @Test
    public void omitBlankSpecies() {
        subject = new AnalyticsQueryBuilder().queryIdentifierSearch(IDENTIFIER_QUERY).ofSpecies("");
        assertThat(subject.build().getQuery(), is(new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), IDENTIFIER_QUERY).toString()));
    }

}
