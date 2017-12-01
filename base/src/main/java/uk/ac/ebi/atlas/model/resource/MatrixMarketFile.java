package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class MatrixMarketFile extends AtlasResource<MatrixMarketReader> {

    public MatrixMarketFile(String parentDirectory, String template, String ... args) {
        super(Paths.get(parentDirectory, MessageFormat.format(template, (Object []) args)));
    }

    public MatrixMarketReader get() {
        try {
            return new MatrixMarketReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
