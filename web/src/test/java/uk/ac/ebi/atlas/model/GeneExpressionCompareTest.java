package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class GeneExpressionCompareTest {

    private ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION");
    private GeneExpression subject;

    @Before
    public void initializeSubject() {
        subject = new GeneExpression("id", 100.0000, new ExperimentRun("RUN_ACCESSION").addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2"));
    }

    @Test
    public void compareToShouldReturn0WhenObjectsAreEquals() {
        //given
        GeneExpression other = new GeneExpression("id", 100, experimentRun);
        //given
        assertThat(subject.compareTo(other), is(0));
        //and
        assertThat(other.compareTo(subject), is(0));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenExpressionLevelIsLowerThanOther() {
        //given
        GeneExpression other = new GeneExpression("id", 100.000001, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));
    }


    @Test
    public void compareToShouldReturnPositiveIntegerWhenExpressionLevelIsGreaterThanOther() {
        //given
        GeneExpression other = new GeneExpression("id", 99.999999, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void givenEqualExpressionLevelCompareToShouldBeCoherentWithAlphabeticOrderOfId() {

        //given
        GeneExpression other = new GeneExpression("zzId", 100, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new GeneExpression("aaId", 100, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }

    @Test
    public void givenEqualExpressionLevelAndEqualTrasncriptIdCompareToShouldBeBasedOnExperimentRun() {
        //given
        GeneExpression other = new GeneExpression("id", 100, new ExperimentRun("RUN_ACCESSION_Z"));
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new GeneExpression("id", 100, new ExperimentRun("A_RUN_ACCESSION"));
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }
}
