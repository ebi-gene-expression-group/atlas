package uk.ac.ebi.atlas.profiles.baseline;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfileInputStreamFactory {

    @Value("#{configuration['experiment.magetab.path.template']}")
    protected String baselineExperimentDataFileUrlTemplate;

    private ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder;

    private CsvReaderFactory csvReaderFactory;

    public BaselineProfileInputStreamFactory() {
    }

    @Inject
    public BaselineProfileInputStreamFactory(ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                              CsvReaderFactory csvReaderFactory) {
        this.expressionsRowDeserializerBaselineBuilder = expressionsRowDeserializerBaselineBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }

    public BaselineProfilesInputStream createBaselineProfileInputStream(String experimentAccession, String queryFactorType, double cutOff, Set<Factor> filterFactors) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setFilterFactors(filterFactors);

        BaselineProfileReusableBuilder baselineProfileReusableBuilder = new BaselineProfileReusableBuilder(baselineExpressionFilter, queryFactorType);

        return new BaselineProfilesInputStream(csvReader, experimentAccession, expressionsRowDeserializerBaselineBuilder, baselineProfileReusableBuilder);
    }

    public BaselineProfilesInputStream create(BaselineProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();

        double cutOff = options.getCutoff();
        String queryFactorType = options.getQueryFactorType();
        Set<Factor> filterFactors = options.getSelectedFilterFactors();

        return createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors);
    }
}
