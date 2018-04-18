package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.SOLR_MAX_ROWS;

// Correctness of query syntax tested in SolrQueryUtilsTest
public class SolrQueryBuilderTest {

    public static class DummySchemaField extends SchemaField<CollectionProxy> {
        private DummySchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final DummySchemaField FIELD1 = new DummySchemaField("field1");
    public static final DummySchemaField FIELD2 = new DummySchemaField("field2");

    @Test
    void byDefaultQueryAllAndRetrieveAllFields() {
        SolrQuery solrQuery = new SolrQueryBuilder().build();

        assertThat(solrQuery.getFilterQueries()).isEmpty();
        assertThat(solrQuery.getQuery()).isEqualTo("*:*");
        assertThat(solrQuery.getFields()).isEqualTo("*");
    }

    @Test
    void multipleQueryClausesAreJoinedWithAnd() {
        SolrQuery solrQuery =
                new SolrQueryBuilder()
                        .addQueryFieldByTerm(FIELD1, "value1")
                        .addQueryFieldByTerm(FIELD2, "value21", "value22")
                        .build();

        assertThat(solrQuery.getQuery().split(" AND ")).hasSize(2);
    }

    @Test
    void setsOrVarargs() {
        assertThat(new SolrQueryBuilder().addQueryFieldByTerm(FIELD1).build().getQuery())
                .isEqualTo(new SolrQueryBuilder().addQueryFieldByTerm(FIELD1, emptySet()).build().getQuery());

        assertThat(new SolrQueryBuilder().addQueryFieldByTerm(FIELD1, "value1").build().getQuery())
                .isEqualTo(new SolrQueryBuilder().addQueryFieldByTerm(FIELD1, singleton("value1")).build().getQuery());

        assertThat(new SolrQueryBuilder().addQueryFieldByTerm(FIELD1, "value1", "value2").build().getQuery())
                .isEqualTo(
                        new SolrQueryBuilder()
                                .addQueryFieldByTerm(FIELD1, ImmutableSet.of("value1", "value2")).build().getQuery());
    }

    @Test
    void testFilterQueries() {
        SolrQuery solrQuery =
                new SolrQueryBuilder()
                        .addFilterFieldByTerm(FIELD1, "value1")
                        .addFilterFieldByRangeMin(FIELD2, 0.0)
                        .addFilterFieldByRangeMax(FIELD2, 0.0)
                        .addFilterFieldByRangeMinMax(FIELD2, 0.0, 0.0)
                        .build();

        assertThat(solrQuery.getFilterQueries())
                .hasSize(4)
                .allMatch(str -> str.matches("field[12]:(.+)"));
    }

    @Test
    void testSetFieldList() {
        assertThat(new SolrQueryBuilder().setFieldList(FIELD1, FIELD2).build().getFields().split(","))
                .containsExactlyInAnyOrder(FIELD1.name(), FIELD2.name());
    }

    @Test
    @DisplayName("Default number of rows is “big enough”, whatever that means")
    void defaultNumberOfRows() {
        assertThat(new SolrQueryBuilder().build().getRows()).isGreaterThanOrEqualTo(1000000);
    }

    @Test
    void testSetRows() {
        int randomRows = ThreadLocalRandom.current().nextInt(1, SOLR_MAX_ROWS + 1);
        assertThat(new SolrQueryBuilder().setRows(randomRows).build().getRows()).isEqualTo(randomRows);
    }

    @Test
    void searchValuesAreDeduped() {
        SolrQuery solrQuery =
                new SolrQueryBuilder()
                        .addQueryFieldByTerm(FIELD1, "value1", "value2", "value2", "value3", "value3", "value3")
                        .build();

        assertThat(solrQuery.getQuery())
                .containsOnlyOnce("value1")
                .containsOnlyOnce("value2")
                .containsOnlyOnce("value3");
    }

}