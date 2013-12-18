package uk.ac.ebi.atlas.dao.dto;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class BaselineExpressionDtoInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String baselineExperimentDataFileTemplate;

    @Inject
    public BaselineExpressionDtoInputStreamFactory(@Value("#{configuration['experiment.magetab.path.template']}")
                                                   String baselineExperimentDataFileTemplate,
                                                   CsvReaderFactory csvReaderFactory) {
        this.baselineExperimentDataFileTemplate = baselineExperimentDataFileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public BaselineExpressionDtoInputStream createBaselineExpressionDtoInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new BaselineExpressionDtoInputStream(csvReader);
    }
}
