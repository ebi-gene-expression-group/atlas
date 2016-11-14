package uk.ac.ebi.atlas.model.resource;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class TsvFile extends AtlasResource<CSVReader>{

    private static final Logger LOGGER = LoggerFactory.getLogger(TsvFile.class);

    public TsvFile(String dataFilesLocation, String template, String ... args) {
        super(Paths.get(dataFilesLocation+MessageFormat.format(template, args)));
    }

    @Override
    public CSVReader get() {
        try {
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(path));
            return new CSVReader(dataFileReader, '\t');
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
