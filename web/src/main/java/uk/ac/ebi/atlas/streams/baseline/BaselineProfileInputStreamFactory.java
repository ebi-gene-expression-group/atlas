package uk.ac.ebi.atlas.streams.baseline;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
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
    private String baselineExperimentDataFileUrlTemplate;

    private BaselineExpressionsQueueBuilder baselineExpressionsQueueBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public BaselineProfileInputStreamFactory(BaselineExpressionsQueueBuilder baselineExpressionsQueueBuilder,
                              CsvReaderFactory csvReaderFactory) {
        this.baselineExpressionsQueueBuilder = baselineExpressionsQueueBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }

    public ObjectInputStream<BaselineProfile> createBaselineProfileInputStream(String experimentAccession, String queryFactorType, double cutOff, Set<Factor> filterFactors) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        BaselineExpressionIsAboveCutoffAndForFilterFactors baselineExpressionFilter = new BaselineExpressionIsAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(cutOff);
        baselineExpressionFilter.setFilterFactors(filterFactors);

        BaselineProfileFilteredExpressionBuilder baselineProfileFilteredExpressionBuilder = new BaselineProfileFilteredExpressionBuilder(baselineExpressionFilter, queryFactorType);

        return new BaselineProfilesInputStream(csvReader, experimentAccession, baselineExpressionsQueueBuilder, baselineProfileFilteredExpressionBuilder);
    }

}
