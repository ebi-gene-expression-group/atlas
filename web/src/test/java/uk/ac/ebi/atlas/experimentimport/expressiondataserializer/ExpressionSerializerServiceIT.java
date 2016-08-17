package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@Named
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExpressionSerializerServiceIT {

    private static final String accession = "E-MTAB-513";

    @Inject
    public ExperimentTrader experimentTrader;

    @Mock
    ExperimentChecker experimentChecker;

    private String serializedExpressionsFileTemplate;

    @Value("#{configuration['experiment.magetab.path.template']}")
    public String tsvFileTemplate;

    private ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Path tmp = Files.createTempDirectory("serialized_expression");
        serializedExpressionsFileTemplate = tmp.toString() + "/{0}.kryo";

        subject = new ExpressionSerializerService(experimentTrader, new RnaSeqBaselineExpressionKryoSerializer(serializedExpressionsFileTemplate, tsvFileTemplate, new CsvReaderFactory()),
                experimentChecker);
    }

    @Test
    public void weCanSerializeTheFile() {
        assertThat(fileExists(tsvFileTemplate, accession), is(true));
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
