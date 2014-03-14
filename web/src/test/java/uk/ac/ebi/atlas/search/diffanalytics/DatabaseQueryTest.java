package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.collect.ImmutableList;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.Is;
import org.junit.Test;

public class DatabaseQueryTest {

    @Test
    public void substituteQuestionMarksForParameters1() {
        MatcherAssert.assertThat(DatabaseQuery.substituteQuestionMarksForParameters("one ? two ?", ImmutableList.of("a", "b")), Is.is("one a two b"));
    }

    @Test
    public void substituteQuestionMarksForParameters2() {
        MatcherAssert.assertThat(DatabaseQuery.substituteQuestionMarksForParameters("one ? two ? three", ImmutableList.of("a", "b")), Is.is("one a two b three"));
    }

    @Test
    public void substituteQuestionMarksForParameters3() {
        MatcherAssert.assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a", "b")), Is.is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersMissingParam() {
        MatcherAssert.assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a")), Is.is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersExtraParam() {
        MatcherAssert.assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a", "b", "c")), Is.is("a two b"));
    }

    @Test
    public void quote() {
        MatcherAssert.assertThat(DatabaseQuery.quote(ImmutableList.of("a", "b", "c")), IsIterableContainingInOrder.contains("'a'", "'b'", "'c'"));
    }

    @Test
    public void expand() {
        DatabaseQuery<String> databaseQuery = new DatabaseQuery<>();
        databaseQuery.appendToQueryString("SELECT * FROM MyTable WHERE EXPERIMENT = ? AND CONTRAST = ?");
        databaseQuery.addParameter("exp1");
        databaseQuery.addParameter("contrast1");
        MatcherAssert.assertThat(databaseQuery.expand(), Is.is("SELECT * FROM MyTable WHERE EXPERIMENT = 'exp1' AND CONTRAST = 'contrast1'"));
    }

}
