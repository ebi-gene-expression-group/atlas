package uk.ac.ebi.atlas.commons.readers;

import org.apache.commons.lang3.tuple.Triple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

public class MatrixMarketReader implements AutoCloseable {
    private final int numRows;
    private final int numCols;
    private final Stream<Triple<Integer, Integer, Double>> lines;
    private final BufferedReader inReader;

    public MatrixMarketReader(Reader reader) throws IOException {
        inReader = new BufferedReader(reader);

        String line;
        do {
            line = inReader.readLine();
        } while (line.startsWith("%"));

        String[] splitLine = line.split(" ");
        numRows = Integer.parseInt(splitLine[0]);
        numCols = Integer.parseInt(splitLine[1]);

        lines = inReader.lines().map(MatrixMarketReader::parseLine);
    }

    public int getRows() {
        return numRows;
    }

    public int getColumns() {
        return numCols;
    }

    public Stream<Triple<Integer, Integer, Double>> stream() {
        return lines;
    }

    @Override
    public void close() {
        try {
            inReader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Triple<Integer, Integer, Double> parseLine(String line) {
        String[] splitLine = line.split(" ");
        return Triple.of(
                Integer.parseInt(splitLine[0]),
                Integer.parseInt(splitLine[1]),
                Double.parseDouble(splitLine[2]));
    }
}
