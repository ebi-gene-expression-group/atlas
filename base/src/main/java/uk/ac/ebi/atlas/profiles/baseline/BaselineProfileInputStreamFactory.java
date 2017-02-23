package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.BaselineProfileKryoInputStream;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Set;

@Named
public class BaselineProfileInputStreamFactory
implements ProfileStreamFactory<BaselineExperiment, BaselineProfileStreamOptions,OldBaselineProfile, Factor>{

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    protected String baselineExperimentSerializedDataFileUrlTemplate;

    private final DataFileHub dataFileHub;
    private KryoReaderFactory kryoReaderFactory;

    @Inject
    public BaselineProfileInputStreamFactory(DataFileHub dataFileHub,
                                             KryoReaderFactory kryoReaderFactory) {
        this.dataFileHub = dataFileHub;
        this.kryoReaderFactory = kryoReaderFactory;
    }

    public ObjectInputStream<BaselineProfile> createBaselineProfileInputStream(BaselineExperiment experiment, String queryFactorType, double cutOff, Set<Factor> filterFactors)
    throws IOException {
        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setFilterFactors(filterFactors);

        BaselineProfileReusableBuilder baselineProfileReusableBuilder = new BaselineProfileReusableBuilder(baselineExpressionFilter, queryFactorType);

        //TODO design me better
        if(experiment.getType().isProteomicsBaseline()){
            return new BaselineProfilesTsvInputStream(
                    dataFileHub.getBaselineExperimentFiles(experiment.getAccession()).main.getReader(),
                    new ExpressionsRowDeserializerProteomicsBaselineBuilder(experiment), baselineProfileReusableBuilder);
        } else {
            try {
                return new BaselineProfileKryoInputStream(
                        BaselineExpressionsKryoReader.create(MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, experiment.getAccession())),
                        experiment);
            } catch (IllegalArgumentException e) {
                return new BaselineProfilesTsvInputStream(
                        dataFileHub.getBaselineExperimentFiles(experiment.getAccession()).main.getReader(),
                        new ExpressionsRowDeserializerBaselineBuilder(experiment), baselineProfileReusableBuilder);
            }
        }
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment, BaselineProfileStreamOptions
            options) throws
            IOException {

        double cutOff = options.getCutoff();
        String queryFactorType = options.getQueryFactorType();
        Set<Factor> filterFactors = options.getSelectedFilterFactors();

        return createBaselineProfileInputStream(experiment, queryFactorType, cutOff, filterFactors);
    }

}
