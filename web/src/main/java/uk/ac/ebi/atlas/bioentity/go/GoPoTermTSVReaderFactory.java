package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class GoPoTermTSVReaderFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String filePath;
    private static final String GO_TERM = "GO:";
    private static final String PO_TERM = "PO:";

    @Inject
    public GoPoTermTSVReaderFactory(@Value("#{configuration['go.terms.file']}")
                                    String filePath,
                                    CsvReaderFactory csvReaderFactory) {
        this.filePath = filePath;
        this.csvReaderFactory = csvReaderFactory;
    }

    public GoPoTermTSVReader createGoTerms() {
        CSVReader csvReader = csvReaderFactory.createTsvReader(filePath);
        return new GoPoTermTSVReader(csvReader, GO_TERM);

    }

    public GoPoTermTSVReader createPoTerms() {
        CSVReader csvReader = csvReaderFactory.createTsvReader(filePath);
        return new GoPoTermTSVReader(csvReader, PO_TERM);

    }

    public String getFilePath() {
        return filePath;
    }
}
