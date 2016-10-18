package uk.ac.ebi.atlas.utils;

import au.com.bytecode.opencsv.CSVReader;

import java.io.Closeable;
import java.io.IOException;

/*
 * Buffers next line, so it can provide a hasNext method
 */
public class BufferedCSVReader implements Closeable {

    private CSVReader csvReader;
    private String[] nextLine;

    public BufferedCSVReader(CSVReader csvReader) {
        this.csvReader = csvReader;
        try {
            storeNext();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void storeNext() throws IOException {
        nextLine = csvReader.readNext();
    }

    public String[] readNext() throws IOException {
        String[] result = nextLine;
        storeNext();
        return result;
    }

    public boolean hasNext() {
        return (nextLine != null);
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }
}
