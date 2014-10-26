package uk.ac.ebi.atlas.profiles.baseline;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfileInputStreamFactory extends BaselineProfileInputStreamFactory {

    private ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder;
    private CsvReaderFactory csvReaderFactory;

    @Inject
    public ProteomicsBaselineProfileInputStreamFactory(ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder,
                                                       CsvReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
        this.expressionsRowDeserializerProteomicsBaselineBuilder = expressionsRowDeserializerProteomicsBaselineBuilder;
    }

    @Override
    public BaselineProfilesInputStream createBaselineProfileInputStream(String experimentAccession, String queryFactorType, double cutOff, Set<Factor> filterFactors) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setFilterFactors(filterFactors);

        BaselineProfileReusableBuilder baselineProfileReusableBuilder = new BaselineProfileReusableBuilder(baselineExpressionFilter, queryFactorType);

        return new BaselineProfilesInputStream(csvReader, experimentAccession, expressionsRowDeserializerProteomicsBaselineBuilder, baselineProfileReusableBuilder);
    }

    @Override
    public BaselineProfilesInputStream create(BaselineProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();

        double cutOff = options.getCutoff();
        String queryFactorType = options.getQueryFactorType();
        Set<Factor> filterFactors = options.getSelectedFilterFactors();

        return createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors);
    }

}
