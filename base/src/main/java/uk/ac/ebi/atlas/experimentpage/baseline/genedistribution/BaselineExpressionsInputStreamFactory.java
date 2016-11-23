package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerProteomicsBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowRawDeserializerBaselineBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class BaselineExpressionsInputStreamFactory {

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String baselineExperimentSerializedDataFileUrlTemplate;

    private KryoReaderFactory kryoReaderFactory;
    private final DataFileHub dataFileHub;

    @Inject
    public BaselineExpressionsInputStreamFactory(KryoReaderFactory kryoReaderFactory,
                                                 DataFileHub dataFileHub) {
        this.kryoReaderFactory = kryoReaderFactory;
        this.dataFileHub = dataFileHub;
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(BaselineExperiment experiment)
            throws IOException {

        String experimentAccession = experiment.getAccession();

        if(experiment.getType().isProteomicsBaseline()) {
            return new BaselineExpressionsTsvInputStream(
                    dataFileHub.getBaselineExperimentFiles(experimentAccession).main.getReader(),
                    new ExpressionsRowDeserializerProteomicsBaselineBuilder(experiment));
        }
        else {
            String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, experimentAccession);
            try {
                BaselineExpressionsKryoReader kryoReader = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
                return new BaselineExpressionsKryoInputStream(
                        kryoReader,  new ExpressionsRowRawDeserializerBaselineBuilder(experiment));
            } catch (IllegalArgumentException e) {
                return new BaselineExpressionsTsvInputStream(
                        dataFileHub.getBaselineExperimentFiles(experimentAccession).main.getReader(),
                        new ExpressionsRowDeserializerBaselineBuilder(experiment));
            }
        }
    }

}
