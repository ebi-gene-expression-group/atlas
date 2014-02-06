package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class RnaSeqBaselineTranscriptReaderFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String fileTemplate;

    @Inject
    public RnaSeqBaselineTranscriptReaderFactory(@Value("#{configuration['experiment.transcripts.path.template']}")
                                                 String fileTemplate,
                                                 CsvReaderFactory csvReaderFactory) {
        this.fileTemplate = fileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public RnaSeqBaselineTranscriptReader create(String experimentAccession, String[] orderedAssayGroupIds) {
        String tsvFilePath = MessageFormat.format(fileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFilePath);
        return new RnaSeqBaselineTranscriptReader(csvReader, tsvFilePath, orderedAssayGroupIds);
    }
}
