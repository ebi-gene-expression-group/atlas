package uk.ac.ebi.atlas.profiles.baseline;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

@Named("baselineProfileInputStreamFactory")
@Scope("prototype")
public class BaselineProfileInputStreamFactory {

    @Value("#{configuration['experiment.magetab.path.template']}")
    protected String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    protected String baselineExperimentSerializedDataFileUrlTemplate;

    private ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder;
    private ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder;

    private CsvReaderFactory csvReaderFactory;
    private KryoReaderFactory kryoReaderFactory;

    public BaselineProfileInputStreamFactory() {
    }

    @Inject
    public BaselineProfileInputStreamFactory(ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                                             ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder,
                                             CsvReaderFactory csvReaderFactory, KryoReaderFactory kryoReaderFactory) {
        this.expressionsRowDeserializerBaselineBuilder = expressionsRowDeserializerBaselineBuilder;
        this.expressionsRowRawDeserializerBaselineBuilder = expressionsRowRawDeserializerBaselineBuilder;

        this.csvReaderFactory = csvReaderFactory;
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
            String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
            CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
            return new BaselineProfilesTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerBaselineBuilder, baselineProfileReusableBuilder);
        }
    }

    public ExpressionProfileInputStream<BaselineProfile, BaselineExpression> createMultiHeadersFactorBaselineProfileInputStream(String experimentAccession,
                                                                                                                                String queryFactorType,
                                                                                                                                double cutOff,
                                                                                                                                Set<ImmutableSet<Factor>> multiHeaderFactors) {
        IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setMultiHeaderFactors(multiHeaderFactors);

        BaselineProfileReusableBuilder baselineProfileReusableBuilder = new BaselineProfileReusableBuilder(baselineExpressionFilter, queryFactorType);

        String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, experimentAccession);
        try {
            BaselineExpressionsKryoReader baselineExpressionsKryoReader = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
            return new BaselineProfilesKryoInputStream(baselineExpressionsKryoReader, experimentAccession, expressionsRowRawDeserializerBaselineBuilder, baselineProfileReusableBuilder);
        }
        catch (IllegalArgumentException e) {
            String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
            CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
            return new BaselineProfilesTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerBaselineBuilder, baselineProfileReusableBuilder);
        }
    }

    public ExpressionProfileInputStream<BaselineProfile, BaselineExpression> create(BaselineProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();

        double cutOff = options.getCutoff();
        String queryFactorType = options.getQueryFactorType();
        Set<Factor> filterFactors = options.getSelectedFilterFactors();

        Set<ImmutableSet<Factor>> allMultiHeaderFactors = options.getAllMultiHeaderFactors();
        if (allMultiHeaderFactors!=null && !allMultiHeaderFactors.isEmpty()) {
            return createMultiHeadersFactorBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, allMultiHeaderFactors);
        }

        return createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors);
    }
}