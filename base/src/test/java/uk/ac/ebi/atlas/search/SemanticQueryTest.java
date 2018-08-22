package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SemanticQueryTest {

    private SemanticQuery subject =
            SemanticQuery.create(
                    SemanticQueryTerm.create("BRCA2", "symbol"),
                    SemanticQueryTerm.create("BRCA2B", "synonym"),
                    SemanticQueryTerm.create("BRCA2 repeat"));

    @Test
    public void basicProperties() {
        assertThat(SemanticQuery.create().size()).isEqualTo(0);
        assertThat(SemanticQuery.create().isEmpty()).isTrue();
        assertThat(SemanticQuery.create().isNotEmpty()).isFalse();

        assertThat(subject.size()).isEqualTo(3);
        assertThat(subject.isEmpty()).isFalse();
        assertThat(subject.isNotEmpty()).isTrue();
    }

    @Test
    public void createWithASingleValue() {
        assertThat(SemanticQuery.create("Crocubot"))
                .isEqualTo(SemanticQuery.create(SemanticQueryTerm.create("Crocubot")));
    }

    @Test
    public void isIterable() {
        for (SemanticQueryTerm semanticQueryTerm : subject) {
            assertThat(semanticQueryTerm).hasFieldOrProperty("value");
        }
    }

    @Test
    public void testEmptyGeneQuery() {
        subject =
                SemanticQuery.create(
                        SemanticQueryTerm.create("", "symbol"),
                        SemanticQueryTerm.create(" ", "synonym"),
                        SemanticQueryTerm.create("\t"));
        assertThat(subject.isEmpty()).isTrue();
    }

    @Test
    public void testRemovesRepeatedTerms() {
        subject =
                SemanticQuery.create(
                        SemanticQueryTerm.create("BRCA2", "symbol"),
                        SemanticQueryTerm.create("BRCA2", "symbol"));
        assertThat(subject.terms()).hasSize(1);
    }

    @Test
    public void jsonSerialization() {
        String json = subject.toJson();
        ReadContext ctx = JsonPath.parse(json);

        assertThat(ctx.<List<Map<String, String>>>read("$")).hasSize(3);
        assertThat(ctx.<List<Map<String, String>>>read("$"))
                .extracting("category").containsExactlyInAnyOrder("symbol", "synonym", null);
        assertThat(ctx.<List<Map<String, String>>>read("$"))
                .extracting("value").containsExactlyInAnyOrder("BRCA2", "BRCA2B", "BRCA2 repeat");

        assertThat(SemanticQuery.fromJson(subject.toJson()))
                .isEqualTo(subject);
    }

    @Test
    public void urlEncodedJsonSerialization() throws UnsupportedEncodingException {
        assertThat(SemanticQuery.fromUrlEncodedJson(subject.toUrlEncodedJson()))
                .isEqualTo(subject);

    }

    @Test
    public void fromJson() throws UnsupportedEncodingException {
        assertThat(SemanticQuery.fromJson(""))
                .isEqualTo(SemanticQuery.fromUrlEncodedJson(""))
                .isEqualTo(SemanticQuery.create());
    }

    @Test
    public void fromJsonWithRepeats() {
        // This can come from the search page if a user enters repeated terms
        String geneQueryBrca2WithRepeats =
                "[" +
                        "{\"value\":\"BRCA2\",\"category\":\"symbol\"}, " +
                        "{\"value\":\"BRCA2\",\"category\":\"symbol\"}, " +
                        "{\"value\":\"BRCA2B\",\"category\":\"synonym\"}, " +
                        "{\"value\":\"BRCA2B\",\"category\":\"synonym\"}, " +
                        "{\"value\":\"BRCA2 repeat\",\"category\":\"\"}, " +
                        "{\"value\":\"BRCA2 repeat\",\"category\":\"\"}" +
                "]";

        assertThat(SemanticQuery.fromJson(geneQueryBrca2WithRepeats))
                .isEqualTo(subject);
    }

    @Test
    public void deserializeLenientOrNotReallyJson() throws UnsupportedEncodingException {
        assertThat(SemanticQuery.fromUrlEncodedJson("BRCA2A"))
                .isEqualTo(SemanticQuery.fromUrlEncodedJson("BRCA2A BRCA2A"))
                .isEqualTo(SemanticQuery.create("BRCA2A"));

        assertThat(SemanticQuery.fromUrlEncodedJson("BRCA2A BRCA2B"))
                .isEqualTo(
                        SemanticQuery.create(
                                SemanticQueryTerm.create("BRCA2A"),
                                SemanticQueryTerm.create("BRCA2B")));
    }

    @Test
    public void groupingByCategory() {
        subject =
                SemanticQuery.create(
                        SemanticQueryTerm.create("BRCA2", "symbol"),
                        SemanticQueryTerm.create("BRCA2B", "synonym"),
                        SemanticQueryTerm.create("BRCA2A", "synonym"),
                        SemanticQueryTerm.create("BRCA2 repeat"));

        assertThat(subject.groupValuesByCategory())
                .containsAllEntriesOf(
                        ImmutableMap.of(
                                "symbol", ImmutableSet.of("BRCA2"),
                                "synonym", ImmutableSet.of("BRCA2B", "BRCA2A"),
                                "", ImmutableSet.of("BRCA2 repeat")));
    }

    @Test
    public void descriptionForUsers() {
        assertThat(subject.description().split(" OR ")).hasSize(3);
    }


//    @Test
//    public void unsupportedEncodingExceptionsAreWrapped() {
//        assertThatExceptionOfType(UncheckedIOException.class)
//                .isThrownBy(() ->
//                );
//    }

}
