package uk.ac.ebi.atlas.model.resource;

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


    public abstract T get();

}
