package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.Is;
import org.junit.Test;

public class DatabaseQueryPrinterTest {

    @Test
    public void substituteQuestionMarksForParameters1() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.substituteQuestionMarksForParameters("one ? two ?", ImmutableList.of("a", "b")), Is.is("one a two b"));
    }

    @Test
    public void substituteQuestionMarksForParameters2() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.substituteQuestionMarksForParameters("one ? two ? three", ImmutableList.of("a", "b")), Is.is("one a two b three"));
    }

    @Test
    public void substituteQuestionMarksForParameters3() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a", "b")), Is.is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersMissingParam() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a")), Is.is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersExtraParam() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a", "b", "c")), Is.is("a two b"));
    }

    @Test
    public void parametersAsString() {
        MatcherAssert.assertThat(DatabaseQueryPrinter.parametersAsSQLExpression(ImmutableList.of("a", "b", "c")), IsIterableContainingInOrder.contains("'a'", "'b'", "'c'"));
    }

    @Test
    public void print() {
        DatabaseQuery<String> databaseQuery = new DatabaseQuery<>();
        databaseQuery.appendToQueryString("SELECT * FROM MyTable WHERE EXPERIMENT = ? AND CONTRAST = ?");
        databaseQuery.addParameter("exp1");
        databaseQuery.addParameter("contrast1");
        MatcherAssert.assertThat(databaseQuery.print(), Is.is("SELECT * FROM MyTable WHERE EXPERIMENT = 'exp1' AND CONTRAST = 'contrast1'"));
    }

}
