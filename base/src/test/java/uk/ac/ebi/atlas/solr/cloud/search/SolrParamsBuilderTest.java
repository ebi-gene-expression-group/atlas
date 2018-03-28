package uk.ac.ebi.atlas.solr.cloud.search;

import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolrParamsBuilderTest {

    @Test
    public void byDefaultQueryAll() {
        SolrParams solrParams = new SolrQueryBuilder().build();

        assertThat(solrParams.get("fq")).isEmpty();
        assertThat(solrParams.get("q")).isEqualTo("*:*");
    }

    @Test
    public void multipleQueryClausesAreJoinedWithAnd() {
        SolrParams solrParams =
                new SolrQueryBuilder()
                        .addQueryTermsClause("field1", "value1")
                        .addQueryTermsClause("field2", "value21", "value22")
                        .addQueryLowerRangeClause("field3", 1D)
                        .addQueryUpperRangeClause("field4", 1D)
                        .build();

        assertThat(solrParams.get("q").split(" AND ")).hasSize(4);
    }

    @Test
    public void multipleFilterClausesAreJoinedWithAnd() {
        SolrParams solrParams =
                new SolrQueryBuilder()
                        .addFilterTermsClause("field1", "value1")
                        .addFilterTermsClause("field2", "value21", "value22")
                        .addFilterLowerRangeClause("field3", 1D)
                        .addFilterUpperRangeClause("field4", 1D)
                        .build();

        assertThat(solrParams.get("fq").split(" AND ")).hasSize(4);
    }

    @Test
    public void doubleRangeFiltersAreJoinedWithOr() {
        SolrParams solrParams =
                new SolrQueryBuilder()
                        .addFilterDoubleRangeClause("field", -1D, 1D)
                        .build();

        assertThat(solrParams.get("fq").split(" OR ")).hasSize(2);
    }

    @Test
    public void doubleRangeQueriesAreJoinedWithOr() {
        SolrParams solrParams =
                new SolrQueryBuilder()
                        .addQueryDoubleRangeClause("field", -1D, 1D)
                        .build();

        assertThat(solrParams.get("q").split(" OR ")).hasSize(2);
    }

    @Test
    public void searchValuesAreDeduped() {
        SolrParams solrParams =
                new SolrQueryBuilder()
                        .addQueryTermsClause(
                                "field1", "value1", "value2", "value2", "value3", "value3", "value3")
                        .build();

        assertThat(solrParams.get("q")).containsOnlyOnce("value1");
        assertThat(solrParams.get("q")).containsOnlyOnce("value2");
        assertThat(solrParams.get("q")).containsOnlyOnce("value3");
    }

}