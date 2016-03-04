package uk.ac.ebi.atlas.utils;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class CsvReaderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderFactory.class);

    public CSVReader createTsvReader(String tsvFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFilePath));
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));
            return createTsvReader(dataFileReader);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error trying to open " + tsvFilePath, e);
        }
    }

    public CSVReader createTsvGzReader(String tsvGzFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvGzFilePath));
            Reader dataFileReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(filePath))));
            return createTsvReader(dataFileReader);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error trying to open " + tsvGzFilePath, e);
        }
    }

    public static CSVReader createTsvReader(Reader source) {
        return new CSVReader(source, '\t');
    }

}
