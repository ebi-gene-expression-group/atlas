package uk.ac.ebi.atlas.acceptance.magetab.csvreader;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionsCSVReader;
import utils.ExperimentRunsBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

public class ExpressionLevelCsvReaderTest {

    private static final String RUN_ACCESSION1 = "ERR030872";
    private static final String RUN_ACCESSION2 = "ERR030873";
    private static final String RUN_ACCESSION3 = "ERR030874";
    private static final String RUN_ACCESSION4 = "ERR030875";
    private static final String RUN_ACCESSION5 = "ERR030876";
    private ExpressionsCSVReader subject;


    @Before
    public void initSubject() throws Exception {
        URL dataFileURL = ExpressionLevelCsvReaderTest.class.getResource("testCSVReader-data.tab");

        Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

        Map<String, ExperimentRun> experimentRuns = new ExperimentRunsBuilder().buildExperimentRuns(RUN_ACCESSION1,
                RUN_ACCESSION2, RUN_ACCESSION3, RUN_ACCESSION4, RUN_ACCESSION5);

        subject = new ExpressionsCSVReader(dataFileReader, experimentRuns);
    }

    @Test
    public void testReadNext() throws Exception {
        //given
        ExpressionLevel expressionLevel = subject.readNext();

        //then
        //assertThat(expressionLevel.
    }

    @Test
    public void testClose() throws Exception {

    }
}
