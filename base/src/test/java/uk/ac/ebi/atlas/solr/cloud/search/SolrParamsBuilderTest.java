package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolrParamsBuilderTest {

    @Test
    public void byDefaultQueryAll() {
        SolrParams solrParams = new SolrParamsBuilder().build();

        assertThat(solrParams.get("fq")).isEmpty();
        assertThat(solrParams.get("q")).isEqualTo("*:*");
    }

    @Test
    public void multipleQueryClausesAreJoinedWithAnd() {
        SolrParams solrParams =
                new SolrParamsBuilder()
                        .addQueryTermsClause("field1", ImmutableSet.of("value1"))
                        .addQueryTermsClause("field2", ImmutableSet.of("value21", "value22"))
                        .addQueryRangeClause("field3", 1D)
                        .build();

        assertThat(solrParams.get("q").split(" AND ")).hasSize(3);
    }

    @Test
    public void multipleFilterClausesAreJoinedWithAnd() {
        SolrParams solrParams =
                new SolrParamsBuilder()
                        .addFilterTermsClause("field1", ImmutableSet.of("value1"))
                        .addFilterTermsClause("field2", ImmutableSet.of("value21", "value22"))
                        .addFilterRangeClause("field3", 1D)
                        .build();

        assertThat(solrParams.get("fq").split(" AND ")).hasSize(3);
    }

    @Test
    public void searchValuesAreDeduped() {
        SolrParams solrParams =
                new SolrParamsBuilder()
                        .addQueryTermsClause(
                                "field1", ImmutableSet.of("value1", "value2", "value2", "value3", "value3", "value3"))
                        .build();

        assertThat(solrParams.get("q")).containsOnlyOnce("value1");
        assertThat(solrParams.get("q")).containsOnlyOnce("value2");
        assertThat(solrParams.get("q")).containsOnlyOnce("value3");
    }

}