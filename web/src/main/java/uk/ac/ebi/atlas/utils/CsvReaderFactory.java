package uk.ac.ebi.atlas.utils;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("singleton")
public class CsvReaderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderFactory.class);

    public CSVReader createTsvReader(String tsvFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFilePath));
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));
            return createTsvReader(dataFileReader);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }

    public CSVReader createTsvGzReader(String tsvGzFilePath) throws NoSuchFileException {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvGzFilePath));
            Reader dataFileReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(filePath))));
            return createTsvReader(dataFileReader);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NoSuchFileException(e.getMessage());
        }
    }

    public static CSVReader createTsvReader(Reader source) {
        return new CSVReader(source, '\t');
    }

}
