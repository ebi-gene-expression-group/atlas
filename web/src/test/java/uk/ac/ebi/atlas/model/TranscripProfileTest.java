package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TranscripProfileTest {

    private TranscriptProfile subject;

    ExpressionLevel expressionLevel = new ExpressionLevel(new ExperimentRun("RUN_ACCESSION_1"), 2.2D);

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void builderAddExpressionLevelTest() {
        TranscriptProfile.Builder builder = TranscriptProfile.forTranscriptId("ENS1");
        builder.withRpkmCutOff(3d);

        ExpressionLevel expressionLevelSmaller = new ExpressionLevel(new ExperimentRun("RUN_ACCESSION_1"), 2.2D);
        builder.addExpressionLevel(expressionLevelSmaller);

        assertThat(builder.containsExpressionLevels(), is(false));

        ExpressionLevel expressionLevelEquals = new ExpressionLevel(new ExperimentRun("RUN_ACCESSION_1"), 3D);
        builder.addExpressionLevel(expressionLevelEquals);

        assertThat(builder.containsExpressionLevels(), is(false));

        ExpressionLevel expressionLevelBigger = new ExpressionLevel(new ExperimentRun("RUN_ACCESSION_1"), 4D);
        builder.addExpressionLevel(expressionLevelBigger);

        assertThat(builder.containsExpressionLevels(), is(true));

    }
}

