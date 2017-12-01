package uk.ac.ebi.atlas.commons.readers.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Predicate;

public class TsvReaderImpl implements TsvReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsvReaderImpl.class);

    private Reader tsvReader;

    public TsvReaderImpl(Reader tsvReader) {
        this.tsvReader = tsvReader;
    }

    @Override
    public String[] readLine(long lineIndex) {
        try (CSVReader csvReader = new CSVReader(tsvReader, '\t')) {

            String[] line = null;
            for (int i = 0; i <= lineIndex; i++) {
                line = csvReader.readNext();
            }
            return line;

        } catch (IOException e) {
            LOGGER.error("Cannot read TSV file: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String[]> readAll() {
        return readAndFilter(new IsNotCommentPredicate());
    }

    private List<String[]> readAndFilter(Predicate<String> acceptanceCriteria) {
        try (CSVReader csvReader = new CSVReader(tsvReader, '\t')) {

            ImmutableList.Builder<String[]> rowsBuilder = new ImmutableList.Builder<>();
            csvReader.readAll().stream().filter(row -> acceptanceCriteria.test(row[0])).forEach(rowsBuilder::add);
            return rowsBuilder.build();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static class IsNotCommentPredicate implements Predicate<String> {
        @Override
        public boolean test(String rowHeader) {
            return ! rowHeader.trim().startsWith("#");
        }
    }
}