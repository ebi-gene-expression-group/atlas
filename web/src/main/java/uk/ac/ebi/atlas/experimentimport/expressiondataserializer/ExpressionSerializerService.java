package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetKryoSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermKryoSerializer;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 02/04/15.
 */
@Named
@Scope("singleton")
public class ExpressionSerializerService {

    private static final Logger LOGGER = LogManager.getLogger(ExpressionSerializerService.class);

    private ExperimentTrader experimentTrader;
    private ExpressionSerializerFactory expressionSerializerFactory;
    private BaselineExperimentsCache baselineExperimentsCache;


    @Inject
    public ExpressionSerializerService(ExperimentTrader experimentTrader, ExpressionSerializerFactory expressionSerializerFactory,
                                       BaselineExperimentsCache baselineExperimentsCache) {
        this.experimentTrader = experimentTrader;
        this.expressionSerializerFactory = expressionSerializerFactory;
        this.baselineExperimentsCache = baselineExperimentsCache;
    }

    public void kryoSerializeExpressionData(String experimentAccession) {
        try {
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
            expressionSerializerFactory.getKryoSerializer(experiment.getType())
                    .serializeExpressionData(experimentAccession, baselineExperimentsCache.getExperiment(experimentAccession).getExperimentalFactors());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

    public void kryoDeserializeExpressionData(String experimentAccession) {
        LOGGER.info("Starting to read Kryo-serialized file for " + experimentAccession);

        String serializedBaselineExpressionFileName = "/Users/amunoz/ATLAS3.TEST/integration-test-data/serialized_expression/E-MTAB-2706.ser";
        try (FileInputStream fis = new FileInputStream(serializedBaselineExpressionFileName)) {
            UnsafeInput input = new UnsafeInput(fis);
            Kryo kryo = new Kryo();
            ImmutableSetKryoSerializer.registerSerializers(kryo);
            OntologyTermKryoSerializer.registerSerializers(kryo);
            BaselineExpressionsKryoReader kryoReader = new BaselineExpressionsKryoReader(kryo, input);

            kryoReader.rewindAndReadAssays();
            while (kryoReader.readLine()) {

            }
            LOGGER.info("Done reading");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
