package uk.ac.ebi.atlas.search;

import org.junit.Test;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConditionQueryTest {

    @Test
    public void containsIgnoreCase() {
        ConditionQuery subject = ConditionQuery.create("foo and bar");
        assertThat(subject.containsIgnoreCase("and"), is(true));
        assertThat(subject.containsIgnoreCase("AND"), is(true));
        assertThat(subject.containsIgnoreCase("foobar"), is(false));
    }

    @Test
    public void quotedPhrase() {
        ConditionQuery subject = ConditionQuery.create("\"adipose thymus\"");
        assertThat(subject, contains("adipose thymus"));
    }

}