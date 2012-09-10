package uk.ac.ebi.atlas.streams;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptProfile;
import utils.ExperimentRunsBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

    private TranscriptProfilesInputStream subject;

    @Before
    public void initSubject() throws Exception {
        URL dataFileURL = ExpressionLevelsCsvReaderIT.class.getResource("testCSVReader-data.tab");

        Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

        EXPERIMENT_RUNS = new ExperimentRunsBuilder().buildExperimentRuns(RUN_ACCESSION2,
                RUN_ACCESSION3, RUN_ACCESSION1);

        subject = new TranscriptProfilesInputStream(dataFileReader, EXPERIMENT_RUNS);
    }

//    @Test
//    public void readNextShouldReturnNextExpressionLevel() throws Exception {
//        //given
//        TranscriptExpression transcriptExpressionLevel = subject.readNext();
//        //then
//        assertThat(transcriptExpressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_1));
//        assertThat(transcriptExpressionLevel.getRunAccession(), is(RUN_ACCESSION1));
//        assertThat(transcriptExpressionLevel.getRpkm(), is(RPKM_1_1));
//
//        //given
//        transcriptExpressionLevel = subject.readNext();
//        //then
//        assertThat(transcriptExpressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_1));
//        assertThat(transcriptExpressionLevel.getRunAccession(), is(RUN_ACCESSION2));
//        assertThat(transcriptExpressionLevel.getRpkm(), is(RPKM_1_2));
//
//        //given we poll twice more
//        subject.readNext();
//        transcriptExpressionLevel = subject.readNext();
//        //then we expect to be on the second line of expression levels
//        assertThat(transcriptExpressionLevel.getTranscriptId(), is(TRANSCRIPT_ID_2));
//        assertThat(transcriptExpressionLevel.getRunAccession(), is(RUN_ACCESSION1));
//        assertThat(transcriptExpressionLevel.getRpkm(), is(RPKM_2_1));
//    }

    @Test
    public void readNextShouldReturnNextTranscriptProfile() throws Exception {
        //given
        TranscriptProfile transcriptProfile = subject.readNext();
        //then
        assertThat(transcriptProfile.getTranscriptId(), is(TRANSCRIPT_ID_1));
        assertThat(transcriptProfile.getTranscriptSpecificity(), is(3));
        assertThat(transcriptProfile.iterator(), notNullValue());


    }

//
//    @Test
//    public void readNextShouldReturnNullGivenAllRpkmsHaveBeenRead() throws Exception {
//        TranscriptExpression transcriptExpressionLevel;
//        for (int i = 0; i < 9; i++) {
//            //given
//            transcriptExpressionLevel = subject.readNext();
//            //then
//            assertThat(transcriptExpressionLevel, is(notNullValue()));
//        }
//        //given
//        transcriptExpressionLevel = subject.readNext();
//        //then
//        assertThat(transcriptExpressionLevel, is(nullValue()));
//    }

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
