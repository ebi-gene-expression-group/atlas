package uk.ac.ebi.atlas.dao.diffexpression;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;

public class DatabaseQueryTest {

    @Test
    public void substituteQuestionMarksForParameters1() {
        assertThat(DatabaseQuery.substituteQuestionMarksForParameters("one ? two ?", ImmutableList.of("a", "b")), is("one a two b"));
    }

    @Test
    public void substituteQuestionMarksForParameters2() {
        assertThat(DatabaseQuery.substituteQuestionMarksForParameters("one ? two ? three", ImmutableList.of("a", "b")), is("one a two b three"));
    }

    @Test
    public void substituteQuestionMarksForParameters3() {
        assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a", "b")), is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersMissingParam() {
        assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a")), is("a two b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void substituteQuestionMarksForParametersExtraParam() {
        assertThat(DatabaseQuery.substituteQuestionMarksForParameters("? two ?", ImmutableList.of("a","b","c")), is("a two b"));
    }

    @Test
    public void quote() {
        assertThat(DatabaseQuery.quote(ImmutableList.of("a" ,"b" ,"c")), contains("'a'", "'b'", "'c'"));
    }

    @Test
    public void expand() {
        DatabaseQuery<String> databaseQuery = new DatabaseQuery<>();
        databaseQuery.appendToQueryString("SELECT * FROM MyTable WHERE EXPERIMENT = ? AND CONTRAST = ?");
        databaseQuery.addParameter("exp1");
        databaseQuery.addParameter("contrast1");
        assertThat(databaseQuery.expand(), is("SELECT * FROM MyTable WHERE EXPERIMENT = 'exp1' AND CONTRAST = 'contrast1'"));
    }

}
