package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class BaselineAnalyticsInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String fileTemplate;

    @Inject
    public BaselineAnalyticsInputStreamFactory(@Value("#{configuration['experiment.magetab.path.template']}")
                                               String fileTemplate,
                                               CsvReaderFactory csvReaderFactory) {
        this.fileTemplate = fileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public ObjectInputStream<BaselineAnalytics> create(String experimentAccession, ExperimentType experimentType) {
        Preconditions.checkArgument(experimentType.isBaseline());
        String tsvFilePath = MessageFormat.format(fileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFilePath);
        return experimentType.isProteomicsBaseline()
                ? new ProteomicsBaselineAnalyticsInputStream(csvReader, tsvFilePath)
                : new RnaSeqBaselineAnalyticsInputStream(csvReader, tsvFilePath);
    }
}
