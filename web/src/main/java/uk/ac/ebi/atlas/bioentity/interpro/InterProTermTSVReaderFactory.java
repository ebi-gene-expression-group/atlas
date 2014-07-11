package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class InterProTermTSVReaderFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String filePath;

    @Inject
    public InterProTermTSVReaderFactory(@Value("#{configuration['interpro.terms.file']}")
                                        String filePath,
                                        CsvReaderFactory csvReaderFactory) {
        this.filePath = filePath;
        this.csvReaderFactory = csvReaderFactory;
    }

    public InterProTermTSVReader create() {
        CSVReader csvReader = csvReaderFactory.createTsvReader(filePath);
        return new InterProTermTSVReader(csvReader);
    }

    public String getFilePath() {
        return filePath;
    }
}
