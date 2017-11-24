package uk.ac.ebi.atlas.commons.readers;

import org.apache.commons.lang3.tuple.Triple;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixMarketReader implements Closeable {

    private final int numRows;
    private final int numCols;
    private final int numElements;
    private final Stream<Triple<Integer, Integer, Double>> lines;
    private final BufferedReader reader;

    public MatrixMarketReader(InputStream in) throws IOException {
        reader = new BufferedReader(new InputStreamReader(in));

        String line;
        do {
            line = reader.readLine();
        } while (line.startsWith("%"));

        String[] splitLine = line.split(" ");
        numRows = Integer.parseInt(splitLine[0]);
        numCols = Integer.parseInt(splitLine[1]);
        numElements = Integer.parseInt(splitLine[2]);

        lines = reader.lines().map(MatrixMarketReader::parseLine);
    }

    public int getRows() {
        return numRows;
    }

    public int getColumns() {
        return numCols;
    }

    public int getElements() {
        return numElements;
    }

    public Stream<Triple<Integer, Integer, Double>> stream() {
        return lines;
    }

    public List<Triple<Integer, Integer, Double>> list() {
        return lines.collect(Collectors.toList());
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    static private Triple<Integer, Integer, Double> parseLine(String line) {
        String[] splitLine = line.split(" ");
        return Triple.of(
                Integer.parseInt(splitLine[0]),
                Integer.parseInt(splitLine[1]),
                Double.parseDouble(splitLine[2]));
    }
}