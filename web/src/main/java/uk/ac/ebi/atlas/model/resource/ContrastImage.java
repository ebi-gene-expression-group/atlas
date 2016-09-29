package uk.ac.ebi.atlas.model.resource;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ContrastImage extends ExternalResource<InputStream> {


    ContrastImage(ResourceType type, Path path, String uri) {
        super(type,path, uri);
    }

    @Override
    public InputStream get() {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
