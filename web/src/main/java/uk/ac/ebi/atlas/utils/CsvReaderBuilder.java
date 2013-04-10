package uk.ac.ebi.atlas.utils;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class CsvReaderBuilder {

    private static final Logger logger = Logger.getLogger(CsvReaderBuilder.class);

    public CSVReader buildCsvReader(String tsvFileURL) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFileURL));
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));
            return new CSVReader(dataFileReader, '\t');
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building GeneProfileInputStream.", e);
        }
    }
}
