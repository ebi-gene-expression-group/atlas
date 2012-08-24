package uk.ac.ebi.atlas.acceptance.io.magetab.csvreader;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelsCsvReader;
import utils.ExperimentRunsBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExpressionLevelsCsvReaderTest {

    private static final String RUN_ACCESSION1 = "ERR030872";
    private static final String RUN_ACCESSION2 = "ERR030873";
    private static final String RUN_ACCESSION3 = "ERR030874";

    private ExpressionLevelsCsvReader subject;


    @Before
    public void initSubject() throws Exception {
        URL dataFileURL = ExpressionLevelsCsvReaderTest.class.getResource("testCSVReader-data.tab");

        Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

        Map<String, ExperimentRun> experimentRuns = new ExperimentRunsBuilder().buildExperimentRuns(RUN_ACCESSION1,
                RUN_ACCESSION2, RUN_ACCESSION3);

        subject = new ExpressionLevelsCsvReader(dataFileReader, experimentRuns);
    }

    @Test
    public void testReadNext() throws Exception {
        //given
        ExpressionLevel expressionLevel = subject.readNext();

        //then
        assertThat(expressionLevel.getTranscriptId(), is("ENST00000000233"));
    }

    @Test
    public void testClose() throws Exception {

    }
}
