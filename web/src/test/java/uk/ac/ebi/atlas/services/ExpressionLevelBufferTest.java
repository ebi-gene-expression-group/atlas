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

    private ExpressionLevelBuffer subject;
    private static Map<Integer, ExperimentRun> EXPERIMENT_RUNS;

    @Before
    public void initializeSubject() {

        EXPERIMENT_RUNS = new ExperimentRunsBuilder().buildIndexedExperimentRuns(RUN_ACCESSION1,
                RUN_ACCESSION2, RUN_ACCESSION3);

        subject = new ExpressionLevelBuffer(EXPERIMENT_RUNS);
        subject.reload(TRANSCRIPT_ID, "0", "42.9134", "0.0001");
    }

    @Test
    public void testPoll() throws Exception {

        ExpressionLevel expressionLevel1 = subject.poll();
        assertThat(expressionLevel1.getIdentifier(), is(TRANSCRIPT_ID));
        assertThat(expressionLevel1.getRpkm(), is(0d));
        assertThat(expressionLevel1.getFactorValues(), is(EXPERIMENT_RUNS.get(1).getFactorValues()));

    }

    @Test
    public void testBefferShouldBeExhaustedAfterThreePolls() throws Exception {

        subject.poll();
        subject.poll();
        subject.poll();

        ExpressionLevel expressionLevel = subject.poll();
        assertThat(expressionLevel, is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfMoreValuesThanRuns() throws Exception {
        subject.reload(TRANSCRIPT_ID, "0", "42.9134", "0.0001", "666");
    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfLessValuesThanRuns() throws Exception {
        subject.reload(TRANSCRIPT_ID, "0", "42.9134");
    }

    @Test
    public void reloadShouldResetBufferContent() {
        subject.reload("T1", "1", "2", "3");
        ExpressionLevel expressionLevel = subject.poll();

        assertThat(expressionLevel.getIdentifier(), is("T1"));
        assertThat(expressionLevel.getRpkm(), is(1d));
    }
}
