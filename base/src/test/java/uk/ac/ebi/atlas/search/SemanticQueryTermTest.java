package uk.ac.ebi.atlas.search;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SemanticQueryTermTest {
    @Test
    public void doubleQuotesAreEscaped() throws Exception {
        assertThat(
                SemanticQueryTerm.create("\"this\"").asGxaIndexQueryLiteral(),
                is("property_value:\"\\\"this\\\"\""));
        assertThat(
                SemanticQueryTerm.create("\"this\"", "that").asGxaIndexQueryLiteral(),
                is("property_name:\"that\" AND property_value:\"\\\"this\\\"\""));
    }

}