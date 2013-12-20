package uk.ac.ebi.atlas.experimentloader.analytics.baseline;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class BaselineAnalyticsInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String baselineExperimentDataFileTemplate;

    @Inject
    public BaselineAnalyticsInputStreamFactory(@Value("#{configuration['experiment.magetab.path.template']}")
                                               String baselineExperimentDataFileTemplate,
                                               CsvReaderFactory csvReaderFactory) {
        this.baselineExperimentDataFileTemplate = baselineExperimentDataFileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public BaselineAnalyticsInputStream createBaselineExpressionDtoInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new BaselineAnalyticsInputStream(csvReader);
    }
}
