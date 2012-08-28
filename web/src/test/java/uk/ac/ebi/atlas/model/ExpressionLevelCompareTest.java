package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class ExpressionLevelCompareTest {

    private ExpressionLevel subject;

    @Before
    public void initializeSubject() {
        subject = new ExpressionLevel("id", 100.0000, new ExperimentRun("RUN_ACCESSION").addFactorValue("f1", "v1")
                                                                                        .addFactorValue("f2", "v2"));
    }

    @Test
    public void compareToShouldReturn0WhenObjectsAreEquals() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 100, new ExperimentRun("RUN_ACCESSION"));
        //given
        assertThat(subject.compareTo(other), is(0));
        //and
        assertThat(other.compareTo(subject), is(0));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenRPKMIsLowerThanOther() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 100.000001);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));
    }

    @Test
    public void compareToShouldReturnPositiveIntegerWhenRPKMIsGreaterThanOther() {
        //given
        ExpressionLevel other = new ExpressionLevel("id", 99.999999);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void givenEqualRpkmCompareToShouldBeCoherentWithAlphabeticOrderOfId() {

        //given
        ExpressionLevel other = new ExpressionLevel("zzId", 100);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new ExpressionLevel("aaId", 100);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }

    @Test
    public void givenEqualRpkmAndEqualTrasncriptIdCompareToShouldBeBasedOnExperimentRun(){
        //given
        ExpressionLevel other = new ExpressionLevel("id", 100, new ExperimentRun("RUN_ACCESSION_Z"));
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new ExpressionLevel("id", 100, new ExperimentRun("A_RUN_ACCESSION"));
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }
}
