package uk.ac.ebi.atlas.model.resource;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AtlasResource<T> {

    protected final Path path;

    AtlasResource(Path path){
        this.path = path;
    }

    public boolean exists(){
        return Files.exists(path);
    }

    public boolean isReadable() {
        return Files.isReadable(path);
    }

    public abstract T get();

    public Reader getReader() throws IOException {
        return Files.newBufferedReader(path, StandardCharsets.UTF_8);
    }

    public long size() {
        return path.toFile().length();
    }

    @Override
    public String toString(){
        return this.getClass().getName()+" with path "+path.toString();
    }

}
