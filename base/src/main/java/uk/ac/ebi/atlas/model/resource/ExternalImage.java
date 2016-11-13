package uk.ac.ebi.atlas.model.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ExternalImage extends ExternalResource<InputStream> {


    ExternalImage(ResourceType type, Path path, String uri) {
        super(type,path, uri);
    }

    @Override
    public InputStream get() {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
