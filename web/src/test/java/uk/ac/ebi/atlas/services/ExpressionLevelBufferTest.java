package uk.ac.ebi.atlas.services;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import utils.ExperimentRunsBuilder;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ExpressionLevelBufferTest {

    private static final String RUN_ACCESSION1 = "ERR030872";
    private static final String RUN_ACCESSION2 = "ERR030873";
    private static final String RUN_ACCESSION3 = "ERR030874";
    private static final String TRANSCRIPT_ID = "ENST00000000233";

    private ExpressionLevelsBuffer subject;
    private static Map<String, ExperimentRun> EXPERIMENT_RUNS;

    @Before
    public void initializeSubject() {
        String[] orderedRunAccessions = new String[] {RUN_ACCESSION1,
            RUN_ACCESSION2, RUN_ACCESSION3};

        EXPERIMENT_RUNS = new ExperimentRunsBuilder().buildExperimentRuns(RUN_ACCESSION1,
            RUN_ACCESSION2, RUN_ACCESSION3);

        subject = new ExpressionLevelsBuffer(orderedRunAccessions, EXPERIMENT_RUNS);
        subject.reload(TRANSCRIPT_ID, "0", "42.9134", "0.0001");
    }

    @Test
    public void pollShouldReturnExpressionLevelsInTheRightOrder() throws Exception {
        //given the object was just initialized
        ExpressionLevel expressionLevel = subject.poll();
        //then we expect first expressionLevel
        assertThat(expressionLevel.getTranscriptId(), is(TRANSCRIPT_ID));
        assertThat(expressionLevel.getRpkm(), is(0d));
        assertThat(expressionLevel.getFactorValues(), is(EXPERIMENT_RUNS.get(RUN_ACCESSION1).getFactorValues()));

        //given we poll again
        expressionLevel = subject.poll();
        //then we expect secondExpressionLevel
        assertThat(expressionLevel.getTranscriptId(), is(TRANSCRIPT_ID));
        assertThat(expressionLevel.getRpkm(), is(42.9134d));
        assertThat(expressionLevel.getFactorValues(), is(EXPERIMENT_RUNS.get(RUN_ACCESSION2).getFactorValues()));

    }

    @Test
    public void bufferShouldBeExhaustedAfterThreePolls() throws Exception {
        //given
        subject.poll();
        subject.poll();
        subject.poll();
        //when we poll again a fourth time
        ExpressionLevel expressionLevel = subject.poll();
        //then
        assertThat(expressionLevel, is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfMoreValuesThanRuns() throws Exception {
        //given
        subject.reload(TRANSCRIPT_ID, "0", "42.9134", "0.0001", "666");

    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfLessValuesThanRuns() throws Exception {
        //given
        subject.reload(TRANSCRIPT_ID, "0", "42.9134");

    }

    @Test
    public void reloadShouldResetBufferContent() {
        //given
        subject.reload("T1", "1", "2", "3");
        //when
        ExpressionLevel expressionLevel = subject.poll();
        //then
        assertThat(expressionLevel.getTranscriptId(), is("T1"));
        assertThat(expressionLevel.getRpkm(), is(1d));

    }
}
