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
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@Named
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExpressionSerializerServiceIT {

    @Inject
    public ExperimentTrader experimentTrader;

    @Inject
    public BaselineExperimentsCache baselineExperimentsCache;

    @Mock
    ExperimentChecker experimentChecker;

    private String serializedExpressionsFileTemplate;
    private String serializedExpressionLevelsFileTemplate;

    @Value("#{configuration['experiment.magetab.path.template']}")
    public String tsvFileTemplate;


    String accession = "E-MTAB-513";

    private ExpressionSerializerFactory expressionSerializerFactory;
    private ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        Path tmp = Files.createTempDirectory("serialized_expression");
        serializedExpressionsFileTemplate = tmp.toString() + "/{0}.kryo";
        serializedExpressionLevelsFileTemplate = tmp.toString() + "/{0}_levels.kryo";

        expressionSerializerFactory = new ExpressionSerializerFactory(
                new RnaSeqBaselineExpressionKryoSerializer(serializedExpressionsFileTemplate, tsvFileTemplate, new CsvReaderFactory()));

        subject = new ExpressionSerializerService(experimentTrader, expressionSerializerFactory,
                baselineExperimentsCache,experimentChecker);
    }

    @Test
    public void weCanSerializeTheFile() {
        assertEquals(true, fileExists(tsvFileTemplate, accession));
        assertEquals(false, fileExists(serializedExpressionsFileTemplate, accession));
        assertEquals(false, fileExists(serializedExpressionLevelsFileTemplate, accession));

        subject.kryoSerializeExpressionData(accession);

        assertEquals(true, fileExists(serializedExpressionsFileTemplate, accession));
        assertEquals(true, fileExists(serializedExpressionLevelsFileTemplate, accession));
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
