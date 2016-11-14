package uk.ac.ebi.atlas.model.resource;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class CsvFile extends AtlasResource<CSVReader> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFile.class);

    public CsvFile(String dataFilesLocation, String template, String ... args) {
        super(Paths.get(dataFilesLocation+ MessageFormat.format(template, args)));
    }

    @Override
    public CSVReader get() {
        try {
            return new CSVReader(new InputStreamReader(Files.newInputStream(path)), '\t');
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
