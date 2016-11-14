package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

@Named("baselineProfileInputStreamFactory")
@Scope("prototype")
public class BaselineProfileInputStreamFactory
implements ProfileStreamFactory<BaselineProfileStreamOptions, BaselineProfile, Factor> {

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    protected String baselineExperimentSerializedDataFileUrlTemplate;

    private ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder;
    private ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder;

    private final DataFileHub dataFileHub;
    private KryoReaderFactory kryoReaderFactory;

    @Inject
    public BaselineProfileInputStreamFactory(DataFileHub dataFileHub,
                                             ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                                             ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder,
                                             KryoReaderFactory kryoReaderFactory) {
        this.dataFileHub = dataFileHub;
        this.expressionsRowDeserializerBaselineBuilder = expressionsRowDeserializerBaselineBuilder;
        this.expressionsRowRawDeserializerBaselineBuilder = expressionsRowRawDeserializerBaselineBuilder;

        this.kryoReaderFactory = kryoReaderFactory;
    }

    public ExpressionProfileInputStream<BaselineProfile, BaselineExpression> createBaselineProfileInputStream(String experimentAccession, String queryFactorType, double cutOff, Set<Factor> filterFactors) {
        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setFilterFactors(filterFactors);

        BaselineProfileReusableBuilder baselineProfileReusableBuilder = new BaselineProfileReusableBuilder(baselineExpressionFilter, queryFactorType);

        String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, experimentAccession);
        try {
            BaselineExpressionsKryoReader baselineExpressionsKryoReader = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
            return new BaselineProfilesKryoInputStream(baselineExpressionsKryoReader, experimentAccession, expressionsRowRawDeserializerBaselineBuilder, baselineProfileReusableBuilder);
        }
        catch (IllegalArgumentException e) {
            return new BaselineProfilesTsvInputStream(dataFileHub.getExperimentFiles(experimentAccession).main.get(),
                    experimentAccession,
                    expressionsRowDeserializerBaselineBuilder, baselineProfileReusableBuilder);
        }
    }

    public ObjectInputStream<BaselineProfile> create(BaselineProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();

        double cutOff = options.getCutoff();
        String queryFactorType = options.getQueryFactorType();
        Set<Factor> filterFactors = options.getSelectedFilterFactors();

        return createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors);
    }

}
