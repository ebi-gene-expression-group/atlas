package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class ProteomicsBaselineAnalyticsInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String fileTemplate;

    @Inject
    public ProteomicsBaselineAnalyticsInputStreamFactory(@Value("#{configuration['experiment.magetab.path.template']}")
                                                         String fileTemplate,
                                                         CsvReaderFactory csvReaderFactory) {
        this.fileTemplate = fileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public ProteomicsBaselineAnalyticsInputStream create(String experimentAccession) {
        String tsvFilePath = MessageFormat.format(fileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFilePath);
        return new ProteomicsBaselineAnalyticsInputStream(csvReader, tsvFilePath);
    }
}
