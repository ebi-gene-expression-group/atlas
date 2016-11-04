package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class SemanticQueryTest {

    private final SemanticQuery GENE_QUERY_BRCA2 = SemanticQuery.create(SemanticQueryTerm.create("BRCA2", "symbol"), SemanticQueryTerm.create("BRCA2B", "synonym"), SemanticQueryTerm.create("BRCA2 repeat"));
    private final String GENE_QUERY_BRCA2_JSON = "[{\"value\":\"BRCA2\",\"category\":\"symbol\"},{\"value\":\"BRCA2B\",\"category\":\"synonym\"},{\"value\":\"BRCA2 repeat\",\"category\":\"\"}]";
    private final String GENE_QUERY_BRCA2_JSON_WITH_REPEATS = "[{\"value\":\"BRCA2\",\"category\":\"symbol\"},{\"value\":\"BRCA2\",\"category\":\"symbol\"},{\"value\":\"BRCA2B\",\"category\":\"synonym\"},{\"value\":\"BRCA2 repeat\",\"category\":\"\"}]";
    private final String GENE_QUERY_BRCA2_ENCODED_JSON = "%5B%7B%22value%22%3A%22BRCA2%22%2C%22category%22%3A%22symbol%22%7D%2C%7B%22value%22%3A%22BRCA2B%22%2C%22category%22%3A%22synonym%22%7D%2C%7B%22value%22%3A%22BRCA2+repeat%22%2C%22category%22%3A%22%22%7D%5D";

    @Test
    public void testEmptyGeneQuery() {
        SemanticQuery subject = SemanticQuery.create(SemanticQueryTerm.create("", "symbol"), SemanticQueryTerm.create(" ", "synonym"), SemanticQueryTerm.create("\t"));
        assertThat(subject.isEmpty(), is(true));
    }

    @Test
    public void testRemovesRepeatedTerms() {
        SemanticQuery subject = SemanticQuery.create(SemanticQueryTerm.create("BRCA2", "symbol"), SemanticQueryTerm.create("BRCA2", "symbol"));
        assertThat(subject.terms(), hasSize(1));
    }

    @Test
    public void testToJson() {
        assertEquals(GENE_QUERY_BRCA2.toJson(), GENE_QUERY_BRCA2_JSON);
    }

    @Test
    public void testToUrlEncodedJson() throws Exception {
        assertThat(GENE_QUERY_BRCA2.toUrlEncodedJson(), is(GENE_QUERY_BRCA2_ENCODED_JSON));
    }

    @Test
    public void fromJson() {
        assertThat(SemanticQuery.fromJson(GENE_QUERY_BRCA2_JSON), is(GENE_QUERY_BRCA2));
    }

    @Test
    public void fromJsonWithRepeats() {
        assertThat(SemanticQuery.fromJson(GENE_QUERY_BRCA2_JSON_WITH_REPEATS), is(GENE_QUERY_BRCA2));
    }

    @Test
    public void fromUrlEncodedJson() throws Exception {
        assertThat(SemanticQuery.fromUrlEncodedJson(GENE_QUERY_BRCA2_ENCODED_JSON), is(GENE_QUERY_BRCA2));
    }

    @Test
    public void differentOrderAndTypeOfInitializingParametersProduceTheSameObject() throws Exception {
        SemanticQueryTerm semanticQueyTerm1 = SemanticQueryTerm.create("value1", "category1");
        SemanticQueryTerm semanticQueyTerm2 = SemanticQueryTerm.create("value2", "category2");
        assertThat(SemanticQuery.create(ImmutableSet.of(semanticQueyTerm1, semanticQueyTerm2)), is(SemanticQuery.create(ImmutableSet.of(semanticQueyTerm2, semanticQueyTerm1))));
        assertThat(SemanticQuery.create(semanticQueyTerm1, semanticQueyTerm2), is(SemanticQuery.create(ImmutableSet.of(semanticQueyTerm2, semanticQueyTerm1))));
    }

    @Test
    public void parsesJsonWithoutCategories() throws Exception {
        SemanticQuery subject = SemanticQuery.fromJson("[{\"value\":\"zinc finger\"},{\"value\":\"BRCA2B\"}]");
        assertThat(subject.isNotEmpty(), is(true));
        assertThat(subject.size(), is(2));
    }

    @Test
    public void turnsIntoRightBitOfSolrQuery() {
        SemanticQuery s1 = SemanticQuery.fromJson("[{\"value\":\"zinc finger\"},{\"value\":\"BRCA2B\"}]");
        assertThat(s1.asAnalyticsIndexQueryClause(), equalTo("\"zinc finger\" OR \"BRCA2B\""));

        SemanticQuery s2 = SemanticQuery.fromJson("[{\"value\":\"BRCA2\",\"category\":\"symbol\"}]");
        assertThat(s2.asAnalyticsIndexQueryClause(), equalTo("\"symbol:{BRCA2}\""));

    }
}