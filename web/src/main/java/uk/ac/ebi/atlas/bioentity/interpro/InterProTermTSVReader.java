package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;

import java.io.Closeable;
import java.io.IOException;

public class InterProTermTSVReader implements Closeable {

    private final CSVReader csvReader;

    public InterProTermTSVReader(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    ImmutableMap<String, String> readAll() throws IOException {

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            String term = nextLine[0];
            String accession = nextLine[1];
            String type = nextLine[2];
            builder.put(accession, term + " (" + type.toLowerCase() + ")");
        }

        return builder.build();
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }
}
