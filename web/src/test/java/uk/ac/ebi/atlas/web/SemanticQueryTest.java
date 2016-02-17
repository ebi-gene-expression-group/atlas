package uk.ac.ebi.atlas.web;

import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 12/02/2016.
 */
public class SemanticQueryTest {

    private final SemanticQuery GENE_QUERY_BRCA2 = new SemanticQuery(SemanticQueryTerm.create("BRCA2", "symbol"), SemanticQueryTerm.create("BRCA2B", "synonym"), SemanticQueryTerm.create("BRCA2 repeat"));
    private final String GENE_QUERY_BRCA2_JSON = "[{\"value\":\"BRCA2\",\"source\":\"symbol\"},{\"value\":\"BRCA2B\",\"source\":\"synonym\"},{\"value\":\"BRCA2 repeat\",\"source\":\"\"}]";
    private final String GENE_QUERY_BRCA2_JSON_WITH_REPEATS = "[{\"value\":\"BRCA2\",\"source\":\"symbol\"},{\"value\":\"BRCA2\",\"source\":\"symbol\"},{\"value\":\"BRCA2B\",\"source\":\"synonym\"},{\"value\":\"BRCA2 repeat\",\"source\":\"\"}]";
    private final String GENE_QUERY_BRCA2_ENCODED_JSON = "%5B%7B%22value%22%3A%22BRCA2%22%2C%22source%22%3A%22symbol%22%7D%2C%7B%22value%22%3A%22BRCA2B%22%2C%22source%22%3A%22synonym%22%7D%2C%7B%22value%22%3A%22BRCA2+repeat%22%2C%22source%22%3A%22%22%7D%5D";

    @Test
    public void testEmptyGeneQuery() {
        SemanticQuery subject = new SemanticQuery(SemanticQueryTerm.create("", "symbol"), SemanticQueryTerm.create(" ", "synonym"), SemanticQueryTerm.create("\t"));
        assertThat(subject.isEmpty(), is(true));
    }

    @Test
    public void testRemovesRepeatedTerms() {
        SemanticQuery subject = new SemanticQuery(SemanticQueryTerm.create("BRCA2", "symbol"), SemanticQueryTerm.create("BRCA2", "symbol"));
        assertThat(subject.terms(), hasSize(1));
    }


    @Test
    public void testToJson() {
        assertEquals(GENE_QUERY_BRCA2.toJson(), GENE_QUERY_BRCA2_JSON);
    }

    @Test
    public void testToUrlEncodedJson() throws Exception {
        assertEquals(GENE_QUERY_BRCA2.toUrlEncodedJson(), GENE_QUERY_BRCA2_ENCODED_JSON);
    }

    @Test
    public void fromJson() {
        assertEquals(SemanticQuery.fromJson(GENE_QUERY_BRCA2_JSON), GENE_QUERY_BRCA2);
    }

    @Test
    public void fromJsonWithRepeats() {
        assertEquals(SemanticQuery.fromJson(GENE_QUERY_BRCA2_JSON_WITH_REPEATS), GENE_QUERY_BRCA2);
    }


    @Test
    public void fromUrlEncodedJson() throws Exception {
        assertEquals(SemanticQuery.fromUrlEncodedJson(GENE_QUERY_BRCA2_ENCODED_JSON), GENE_QUERY_BRCA2);
    }
}