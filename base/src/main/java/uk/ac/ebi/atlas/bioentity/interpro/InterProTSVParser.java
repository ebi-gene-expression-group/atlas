package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;

public class InterProTSVParser {

    private static final int NAME_COLUMN_INDEX = 0;
    private static final int ACCESSION_COLUMN_INDEX = 1;
    private static final int TYPE_COLUMN_INDEX = 2;

    private final CSVReader csvReader;

    public InterProTSVParser(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    ImmutableMap<String, OntologyTerm> parse() throws IOException {

        ImmutableMap.Builder<String, OntologyTerm> builder = ImmutableMap.builder();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            if (line.length > 2) {
                String name = line[NAME_COLUMN_INDEX];
                String accession = line[ACCESSION_COLUMN_INDEX];
                String type = line[TYPE_COLUMN_INDEX];
                if (accession.matches("^IPR.+")) {
                    builder.put(accession, OntologyTerm.create(accession, name + " (" + type.toLowerCase() + ")"));
                }
            }
        }

        return builder.build();
    }

}
