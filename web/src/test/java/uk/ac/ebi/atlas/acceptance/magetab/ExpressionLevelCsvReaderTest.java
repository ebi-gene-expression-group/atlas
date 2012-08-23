package uk.ac.ebi.atlas.acceptance.magetab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelCsvReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExpressionLevelCsvReaderTest {

    private static final String RUN_ACCESSION1 = "ERR030892";
    private static final String RUN_ACCESSION2 = "ERR030893";
    private ExpressionLevelCsvReader subject;

    private Map<String, ExperimentRun> experimentRuns;

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;

    @Before
    public void initSubject() throws Exception {
        URL dataFileURL = ExpressionLevelCsvReaderTest.class.getResource("test-data.tab");

        Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

        experimentRun1 = new ExperimentRun(RUN_ACCESSION1);
        experimentRun2 = new ExperimentRun(RUN_ACCESSION2);

        experimentRuns = new HashMap<>();
        experimentRuns.put(RUN_ACCESSION1, experimentRun1);
        experimentRuns.put(RUN_ACCESSION2, experimentRun2);

        subject = new ExpressionLevelCsvReader(dataFileReader, experimentRuns);
    }

    @Test
    public void testReadNext() throws Exception {
        //given
        ExpressionLevel expressionLevel = subject.readNext();

        //then
        assertThat(expressionLevel.
    }

    @Test
    public void testClose() throws Exception {

    }
}
