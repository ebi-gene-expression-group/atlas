package uk.ac.ebi.atlas.model.resource;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public abstract class AtlasResource<T> {

    protected final Path path;

    AtlasResource(Path path) {
        this.path = path;
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public boolean existsAndIsNonEmpty() {
        return exists() && (
                path.toFile().isDirectory() ?
                        path.toFile().list().length > 0 :
                        path.toFile().length() > 0);
    }

    public boolean isReadable() {
        return Files.isReadable(path);
    }

    public Path getPath() {
        return path;
    }

    public abstract T get();

    public Optional<T> maybeGet() {
        return exists() ? Optional.of(get()) : Optional.empty();
    }

    public Reader getReader() throws IOException {
        return Files.newBufferedReader(path, StandardCharsets.UTF_8);
    }

    public long size() {
        return path.toFile().length();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " with path " + path.toString();
    }

}
