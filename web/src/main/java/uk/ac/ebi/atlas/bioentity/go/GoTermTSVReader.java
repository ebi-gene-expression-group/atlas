package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;

import java.io.Closeable;
import java.io.IOException;

public class GoTermTSVReader implements Closeable {

    private final CSVReader csvReader;

    public GoTermTSVReader(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    ImmutableMap<String, String> readAll() throws IOException {

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            String accession = nextLine[0];
            String term = nextLine[1].replace("_", " ");
            builder.put(accession, term);
        }

        return builder.build();
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }
}
