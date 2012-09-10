package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class ExpressionLevelCompareTest {

    private TranscriptExpressionLevel subject;

    @Before
    public void initializeSubject() {
        subject = new TranscriptExpressionLevel("id", 100.0000, new ExperimentRun("RUN_ACCESSION").addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2"));
    }

    @Test
    public void compareToShouldReturn0WhenObjectsAreEquals() {
        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("id", 100, new ExperimentRun("RUN_ACCESSION"));
        //given
        assertThat(subject.compareTo(other), is(0));
        //and
        assertThat(other.compareTo(subject), is(0));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenRPKMIsLowerThanOther() {
        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("id", 100.000001);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));
    }

    @Test
    public void compareToShouldReturnNegativeIntegerWhenOtherObjectRPKMIsNaN() {
        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("id", Double.NaN);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void compareToShouldReturnPositiveIntegerWhenRPKMIsGreaterThanOther() {
        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("id", 99.999999);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }


    @Test
    public void givenEqualRpkmCompareToShouldBeCoherentWithAlphabeticOrderOfId() {

        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("zzId", 100);
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new TranscriptExpressionLevel("aaId", 100);
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }

    @Test
    public void givenEqualRpkmAndEqualTrasncriptIdCompareToShouldBeBasedOnExperimentRun() {
        //given
        TranscriptExpressionLevel other = new TranscriptExpressionLevel("id", 100, new ExperimentRun("RUN_ACCESSION_Z"));
        //then
        assertThat(subject.compareTo(other), is(lessThan(0)));

        //given
        other = new TranscriptExpressionLevel("id", 100, new ExperimentRun("A_RUN_ACCESSION"));
        //then
        assertThat(subject.compareTo(other), is(greaterThan(0)));
    }
}
