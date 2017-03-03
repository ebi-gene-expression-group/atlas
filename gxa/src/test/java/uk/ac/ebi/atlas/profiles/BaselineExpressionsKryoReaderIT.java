package uk.ac.ebi.atlas.profiles;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class BaselineExpressionsKryoReaderIT {

    private static final String accession = "E-MTAB-513";
    private static final int GENE_ID_INDEX = 0;
    private static final int GENE_NAME_INDEX = 1;
    private static final int FIRST_LEVEL_INDEX = 2;

    @Inject
    private ExpressionSerializerService expressionSerializerService;

    @Inject
    private DataFileHub dataFileHub;

    private BaselineExpressionsKryoReader subject;

    @Before
    public void setUp() {
        expressionSerializerService.kryoSerializeExpressionData(accession);
        subject = BaselineExpressionsKryoReader.create(dataFileHub.getKryoFileForReading(accession));
    }

    @Test
    public void serializedFilesAreEqualToTsvFiles() throws IOException {
        CSVReader csvReader = new CSVReader(dataFileHub.getBaselineExperimentFiles(accession).main.getReader(), '\t');

        // Read header
        String[] tsvLine = csvReader.readNext();
        List<String> assaysFromTsv = Arrays.asList(tsvLine).subList(FIRST_LEVEL_INDEX, tsvLine.length);

        while (subject.readLine()) {
            tsvLine = csvReader.readNext();

            assertThat(subject.getGeneId(), is(tsvLine[GENE_ID_INDEX]));
            assertThat(subject.getGeneName(), is(tsvLine[GENE_NAME_INDEX]));

            List<String> expressionLevelStrings = new ArrayList<>();
            for (BaselineExpression expression : subject.getExpressions()) {
                Assert.assertThat(assaysFromTsv, hasItem(expression.getDataColumnDescriptorId()));
                expressionLevelStrings.add(expression.getLevelAsString());
            }
            assertThat(expressionLevelStrings.size(), is(assaysFromTsv.size()));

            for (String expression : Arrays.asList(tsvLine).subList(FIRST_LEVEL_INDEX, tsvLine.length)) {
                assertTrue(expressionLevelStrings.containsAll(Arrays.asList(expression.split(","))));
            }
        }
    }

}