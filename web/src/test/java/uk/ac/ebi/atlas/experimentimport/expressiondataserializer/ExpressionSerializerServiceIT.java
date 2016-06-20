package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

@Named
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExpressionSerializerServiceIT {

    @Inject
    public ExperimentTrader experimentTrader;

    @Inject
    public BaselineExperimentsCache baselineExperimentsCache;

    private String serializedExpressionsFileTemplate;
    private String serializedExpressionLevelsFileTemplate;

    @Value("#{configuration['experiment.magetab.path.template']}")
    public String tsvFileTemplate;


    String accesion = "E-MTAB-513";

    private ExpressionSerializerFactory expressionSerializerFactory;
    private ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {

        Path tmp = Files.createTempDirectory("serialized_expression");
        serializedExpressionsFileTemplate = tmp.toString() + "/{0}.kryo";
        serializedExpressionLevelsFileTemplate = tmp.toString() + "/{0}_levels.kryo";

        expressionSerializerFactory = new ExpressionSerializerFactory(
                new RnaSeqBaselineExpressionKryoSerializer(serializedExpressionsFileTemplate,
                        serializedExpressionLevelsFileTemplate, tsvFileTemplate, new CsvReaderFactory()));

        subject = new ExpressionSerializerService(experimentTrader, expressionSerializerFactory,
                baselineExperimentsCache);
    }

    @Test
    public void weCanSerializeTheFile() {
        assertEquals(true, fileExists(tsvFileTemplate, accesion));
        assertEquals(false, fileExists(serializedExpressionsFileTemplate, accesion));
        assertEquals(false, fileExists(serializedExpressionLevelsFileTemplate, accesion));

        subject.kryoSerializeExpressionData(accesion);

        assertEquals(true, fileExists(serializedExpressionsFileTemplate, accesion));
        assertEquals(true, fileExists(serializedExpressionLevelsFileTemplate, accesion));
    }

    private boolean fileExists(String template, String experimentAccession) {
        return Files.exists(FileSystems.getDefault().getPath(template.replaceAll("\\{0\\}", experimentAccession)));
    }

}
