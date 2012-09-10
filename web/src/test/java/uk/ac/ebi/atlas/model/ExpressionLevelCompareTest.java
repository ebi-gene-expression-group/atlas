package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class ExpressionLevelCompareTest {

    private ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION");
    private TranscriptExpression subject;

    @Before
    public void initializeSubject() {
        subject = new TranscriptExpression("id", 100.0000, new ExperimentRun("RUN_ACCESSION").addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2"));
    }

    @Test
    public void compareToShouldReturn0WhenObjectsAreEquals() {
        //given
        TranscriptExpression other = new TranscriptExpression("id", 100, experimentRun);
        //given
        assertThat(subject.compareTo(other), is(0));
        //and
        assertThat(other.compareTo(subject), is(0));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenRPKMIsLowerThanOther() {
        //given
        TranscriptExpression other = new TranscriptExpression("id", 100.000001, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));
    }


    @Test
    public void compareToShouldReturnPositiveIntegerWhenRPKMIsGreaterThanOther() {
        //given
        TranscriptExpression other = new TranscriptExpression("id", 99.999999, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void givenEqualRpkmCompareToShouldBeCoherentWithAlphabeticOrderOfId() {

        //given
        TranscriptExpression other = new TranscriptExpression("zzId", 100, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new TranscriptExpression("aaId", 100, experimentRun);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }

    @Test
    public void givenEqualRpkmAndEqualTrasncriptIdCompareToShouldBeBasedOnExperimentRun() {
        //given
        TranscriptExpression other = new TranscriptExpression("id", 100, new ExperimentRun("RUN_ACCESSION_Z"));
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new TranscriptExpression("id", 100, new ExperimentRun("A_RUN_ACCESSION"));
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }
}
