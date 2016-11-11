package uk.ac.ebi.atlas.profiles;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class BaselineExpressionsKryoReaderIT {

    private static final String E_MTAB_1733 = "E-MTAB-513";
    private static final int GENE_ID_INDEX = 0;
    private static final int GENE_NAME_INDEX = 1;
    private static final int FIRST_LEVEL_INDEX = 2;

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String baselineExperimentSerializedDataFileUrlTemplate;

    @Inject
    private CsvReaderFactory csvReaderFactory;

    @Inject
    private KryoReaderFactory kryoReaderFactory;

    private BaselineExpressionsKryoReader subject;

    @Before
    public void initializeKryo() {
        String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, E_MTAB_1733);
        subject = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
    }

    @Test
    public void serializedFilesAreEqualToTsvFiles() throws IOException {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, E_MTAB_1733);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        // Read header
        String[] tsvLine = csvReader.readNext();
        List<String> assaysFromTsv = Arrays.asList(tsvLine).subList(FIRST_LEVEL_INDEX, tsvLine.length);

        List<String> assaysFromSerializedFile = Arrays.asList(subject.rewindAndReadAssays());

        assertThat(assaysFromSerializedFile, containsInAnyOrder(assaysFromTsv.toArray()));

        while (subject.readLine()) {
            tsvLine = csvReader.readNext();

            assertThat(subject.getGeneId(), is(tsvLine[GENE_ID_INDEX]));
            assertThat(subject.getGeneName(), is(tsvLine[GENE_NAME_INDEX]));

            List<String> expressionLevelStrings = new ArrayList<>();
            for (BaselineExpression expression : subject.getExpressions()) {
                expressionLevelStrings.add(expression.getLevelAsString());
            }

            for (String expression : Arrays.asList(tsvLine).subList(FIRST_LEVEL_INDEX, tsvLine.length)) {
                assertTrue(expressionLevelStrings.containsAll(Arrays.asList(expression.split(","))));
            }
        }
    }

}