package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.UnsafeInput;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermSerializer;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.profiles.KryoReader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 02/04/15.
 */
@Named
@Scope("singleton")
public class ExpressionSerializerService {

    private static final Logger LOGGER = Logger.getLogger(ExpressionSerializerService.class);

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

    public void serializeExpressionData(String experimentAccession) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        expressionSerializerFactory.getSerializer(experiment.getType())
                                   .serializeExpressionData(experimentAccession, baselineExperimentsCache.getExperiment(experimentAccession).getExperimentalFactors());
    }

    public void deserializeExpressionData(String experimentAccession) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        LOGGER.info("Starting to read serialized file...");

        String serializedBaselineExpressionFileName = "/Users/amunoz/ATLAS3.TEST/integration-test-data/serialized_expression/E-MTAB-2706.ser";
        try (FileInputStream fis = new FileInputStream(serializedBaselineExpressionFileName)) {
            UnsafeInput input = new UnsafeInput(fis);
            Kryo kryo = new Kryo();
            ImmutableSetSerializer.registerSerializers(kryo);
            OntologyTermSerializer.registerSerializers(kryo);
            KryoReader kryoReader = new KryoReader(kryo, input);

            kryoReader.rewindAndReadAssays();
            while (kryoReader.readLine()) {

            }
            LOGGER.info("Done reading");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
