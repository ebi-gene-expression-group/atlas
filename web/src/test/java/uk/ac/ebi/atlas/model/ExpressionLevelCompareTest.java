package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class ExpressionLevelCompareTest {

    private ExpressionLevel subject;

    @Before
    public void initializeSubject(){
        subject = new ExpressionLevel("id", 100).addFactorValue("f1", "v1")
                                                 .addFactorValue("f2", "v2");
    }

    @Test
    public void compareToShouldReturn0WhenObjectsAreEquals() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 100).addFactorValue("f1", "v1")
                                                              .addFactorValue("f2", "v2");
        //given
        assertThat(subject.compareTo(other), is(0));
        //and
        assertThat(other.compareTo(subject), is(0));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenRPKMIsLowerThanOther() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 101).addFactorValue("f1", "v1")
                                                              .addFactorValue("f2", "v2");
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));
    }

    @Test
    public void compareToShouldReturnPositiveIntegerWhenRPKMIsGreaterThanOther() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 99).addFactorValue("f1", "v1")
                                                             .addFactorValue("f2", "v2");
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void givenEqualRpkmCompareToShouldBeCoherentWithAlphabeticOrderOfId() {

        //given
        ExpressionLevel other = new ExpressionLevel("zzId", 100).addFactorValue("f1", "v1")
                                                               .addFactorValue("f2", "v2");
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new ExpressionLevel("aaId", 100).addFactorValue("f1", "v1")
                                                .addFactorValue("f2", "v2");
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }
}
