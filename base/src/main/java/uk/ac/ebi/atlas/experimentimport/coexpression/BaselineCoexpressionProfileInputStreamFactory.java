package uk.ac.ebi.atlas.experimentimport.coexpression;

import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;

@Named
public class BaselineCoexpressionProfileInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String fileTemplate;

    @Inject
    public BaselineCoexpressionProfileInputStreamFactory(@Value("#{configuration['experiment.coexpression_matrix.template']}") String fileTemplate,
                                                         CsvReaderFactory csvReaderFactory) {
        this.fileTemplate = fileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public BaselineCoexpressionProfileInputStream create(String experimentAccession) throws NoSuchFileException {
        String tsvGzFilePath = MessageFormat.format(fileTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvGzReader(tsvGzFilePath);
        return new BaselineCoexpressionProfileInputStream(csvReader, tsvGzFilePath);
    }
}
