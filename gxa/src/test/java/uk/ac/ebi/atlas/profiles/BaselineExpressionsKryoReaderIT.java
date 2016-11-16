package uk.ac.ebi.atlas.profiles;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.resource.DataFileHub;
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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class BaselineExpressionsKryoReaderIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final int GENE_ID_INDEX = 0;
    private static final int GENE_NAME_INDEX = 1;
    private static final int FIRST_LEVEL_INDEX = 2;

    @Inject
    private ExpressionSerializerService expressionSerializerService;

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String baselineExperimentSerializedDataFileUrlTemplate;

    @Inject
    private KryoReaderFactory kryoReaderFactory;

    @Inject
    private DataFileHub dataFileHub;

    private BaselineExpressionsKryoReader subject;

    @Before
    public void setUp() {
        expressionSerializerService.kryoSerializeExpressionData(E_MTAB_513);
        String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, E_MTAB_513);
        subject = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
    }

    @Test
    public void serializedFilesAreEqualToTsvFiles() throws IOException {
        CSVReader csvReader = dataFileHub.getExperimentFiles(E_MTAB_513).main.get();

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