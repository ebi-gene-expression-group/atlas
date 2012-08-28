package uk.ac.ebi.atlas.acceptance.io.magetab.csvreader;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelsInputStream;
import utils.ExperimentRunsBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExpressionLevelsCsvReaderIT {

    private static final String RUN_ACCESSION1 = "ERR030872";
    private static final String RUN_ACCESSION2 = "ERR030873";
    private static final String RUN_ACCESSION3 = "ERR030874";
    private static final String TRANSCRIPT_ID_1 = "ENST00000000233";
    private static final String TRANSCRIPT_ID_2 = "ENST00000000412";
    private static final double RPKM_1_1 = 0d;
    private static final double RPKM_1_2 = 42.9134d;
    private static final double RPKM_2_1 = 29.0613d;

    private static List<ExperimentRun> EXPERIMENT_RUNS;

    private ExpressionLevelsInputStream subject;

    @Before
    public void initSubject() throws Exception {
        URL dataFileURL = ExpressionLevelsCsvReaderIT.class.getResource("testCSVReader-data.tab");

        Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

        EXPERIMENT_RUNS = new ExperimentRunsBuilder().buildExperimentRuns(RUN_ACCESSION2,
                RUN_ACCESSION3, RUN_ACCESSION1);

        subject = new ExpressionLevelsInputStream(dataFileReader, EXPERIMENT_RUNS);
    }

    @Test
    public void readNextShouldReturnNextExpressionLevel() throws Exception {
        //given
        ExpressionLevel expressionLevel = subject.readNext();
        //then
        assertThat(expressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_1));
        assertThat(expressionLevel.getRunAccession(), is(RUN_ACCESSION1));
        assertThat(expressionLevel.getRpkm(), is(RPKM_1_1));

        //given
        expressionLevel = subject.readNext();
        //then
        assertThat(expressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_1));
        assertThat(expressionLevel.getRunAccession(), is(RUN_ACCESSION2));
        assertThat(expressionLevel.getRpkm(), is(RPKM_1_2));

        //given we poll twice more
        subject.readNext();
        expressionLevel = subject.readNext();
        //then we expect to be on the second line of expression levels
        assertThat(expressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_2));
        assertThat(expressionLevel.getRunAccession(), is(RUN_ACCESSION1));
        assertThat(expressionLevel.getRpkm(), is(RPKM_2_1));
    }

    @Test
    public void readNextShouldReturnNullGivenAllRpkmsHaveBeenRead() throws Exception {
        ExpressionLevel expressionLevel;
        for(int i=0; i<9; i++){
            //given
            expressionLevel = subject.readNext();
            //then
            assertThat(expressionLevel, is(notNullValue()));
        }
        //given
        expressionLevel = subject.readNext();
        //then
        assertThat(expressionLevel, is(nullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        //given
        subject.close();
        //when
        subject.close();
    }

}
