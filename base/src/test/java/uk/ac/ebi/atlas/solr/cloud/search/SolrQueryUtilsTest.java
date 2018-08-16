package uk.ac.ebi.atlas.solr.cloud.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.joining;
import static org.apache.solr.client.solrj.util.ClientUtils.escapeQueryChars;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SolrQueryUtilsTest {
    private static final class DummySchemaField extends SchemaField<CollectionProxy> {
        private DummySchemaField(String fieldName) {
            super(fieldName);
        }
    }

    private static final DummySchemaField FIELD1 = new DummySchemaField("field1");

    @Test
    @DisplayName("Blank field values return an empty clause")
    void testEmptyValuesInBooleanQuery() {
        assertThat(SolrQueryUtils.createOrBooleanQuery(FIELD1))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, ""))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, " "))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, " ", "\t"))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, " ", "\t", "\n"))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, " ", "\t", "\n", "\r"))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, emptySet()))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, singleton(" \t\n  ")))
                .isEmpty();
    }

    @Test
    @DisplayName("Field values are deduped")
    void testDuplicatedValuesInBooleanQuery() {
        assertThat(SolrQueryUtils.createOrBooleanQuery(FIELD1, "value1", "value1", "value2", "value2"))
                .isEqualTo(SolrQueryUtils.createOrBooleanQuery(FIELD1, "value1", "value2"))
                .containsOnlyOnce("value1")
                .containsOnlyOnce("value2");
    }

    @Test
    @DisplayName("Field values are normalized")
    void testEscapeBooleanQueryCharacters() {
        String value1 = "   value1   ";
        String value2 = ") value2+\"";

        assertThat(SolrQueryUtils.createOrBooleanQuery(FIELD1, value1, value2))
                .containsOnlyOnce("\"" + escapeQueryChars(value1.trim()) + "\"")
                .containsOnlyOnce("\"" + escapeQueryChars(value2.trim()) + "\"");
    }

    @Test
    @DisplayName("Boolean query is formatted properly")
    void testBooleanQueryFormat() {
        String value1 = "value1";
        String value2 = "value2";
        String[] splittedOrQuery = SolrQueryUtils.createOrBooleanQuery(FIELD1, value1, value2).split(":");

        assertThat(splittedOrQuery)
                .hasSize(2)
                .containsExactly(
                        FIELD1.name(),
                        "(" + Stream.of("\"" + value1 + "\"", "\"" + value2 + "\"").collect(joining(" OR ")) + ")");
    }

    @ParameterizedTest
    @MethodSource("randomDoublesProvider")
    @DisplayName("Lower bound range query is formatted properly")
    void testLowerBoundRangeQuery(double min) {
        String[] splittedRangeQuery = SolrQueryUtils.createLowerBoundRangeQuery(FIELD1, min).split(":");

        assertThat(splittedRangeQuery)
                .hasSize(2)
                .containsExactly(FIELD1.name(), "[" + Double.toString(min) + " TO *]");
    }

    @ParameterizedTest
    @MethodSource("randomDoublesProvider")
    @DisplayName("Upper bound range query is formatted properly")
    void testUpperBoundRangeQuery(double max) {
        String[] splittedRangeQuery = SolrQueryUtils.createUpperBoundRangeQuery(FIELD1, max).split(":");

        assertThat(splittedRangeQuery)
                .hasSize(2)
                .containsExactly(FIELD1.name(), "[* TO " + Double.toString(max) + "]");
    }

    @ParameterizedTest
    @MethodSource("randomDoublesProvider")
    @DisplayName("Double bound range query is formatted properly")
    void testDoubleBoundRangeQuery(double min, double max) {
        String[] splittedRangeQuery = SolrQueryUtils.createDoubleBoundRangeQuery(FIELD1, min, max).split(":");

        assertThat(splittedRangeQuery)
                .hasSize(2)
                .containsExactly(FIELD1.name(), "[" + Double.toString(min)+ " TO " + Double.toString(max) + "]");
    }

    private static Stream<Arguments> randomDoublesProvider() {
        Supplier<Double> randomDoubleSupplier = () ->  ThreadLocalRandom.current().nextDouble();
        return Stream.of(Arguments.of(randomDoubleSupplier.get(), randomDoubleSupplier.get()));
    }
}
