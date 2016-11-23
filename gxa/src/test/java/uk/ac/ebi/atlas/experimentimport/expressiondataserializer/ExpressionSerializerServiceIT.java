package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class ExpressionSerializerServiceIT {

    private static final String accession = "E-MTAB-513";

    @Inject
    public ExperimentTrader experimentTrader;

    @Mock
    ExperimentChecker experimentChecker;

    @Inject
    DataFileHub dataFileHub;

    private String serializedExpressionsFileTemplate;

    private ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Path tmp = Files.createTempDirectory("serialized_expression");
        serializedExpressionsFileTemplate = tmp.toString() + "/{0}.kryo";

        subject = new ExpressionSerializerService(experimentTrader, new RnaSeqBaselineExpressionKryoSerializer(dataFileHub,
                serializedExpressionsFileTemplate),
                experimentChecker);
    }

    @Test
    public void weCanSerializeTheFile() {
        assertThat(dataFileHub.getBaselineExperimentFiles(accession).main.exists(), is(true));
        assertThat(fileExists(serializedExpressionsFileTemplate, accession), is(false));

        subject.kryoSerializeExpressionData(accession);

        assertThat(fileExists(serializedExpressionsFileTemplate, accession), is(true));
    }

    @Test
    public void weCheckTheFileExistsBeforeWeAttemptToSerializeIt(){
        subject.kryoSerializeExpressionData(accession);
        verify(experimentChecker).checkAllFiles(eq(accession), any(ExperimentType.class));
    }

    private boolean fileExists(String template, String experimentAccession) {
        return Files.exists(FileSystems.getDefault().getPath(template.replaceAll("\\{0\\}", experimentAccession)));
    }

}
