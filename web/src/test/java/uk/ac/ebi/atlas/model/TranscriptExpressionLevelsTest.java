package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TranscriptExpressionLevelsTest {

    private TranscriptProfile subject;

    @Before
    public void setUp() throws Exception {
        ExpressionLevel expressionLevel = new ExpressionLevel(new ExperimentRun("RUN_ACCESSION_1"), 2.2D);
        subject = TranscriptProfile.forTranscriptId("EMBL-1")
                .addExpressionLevel(expressionLevel).create();
    }

    @Test
    public void testGetTranscriptSpecificity() throws Exception {
        assertThat(subject.getTranscriptSpecificity(), is(1));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(subject.iterator().next(), is(notNullValue()));
    }
}
