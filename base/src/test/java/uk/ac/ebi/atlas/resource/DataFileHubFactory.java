package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Paths;

@Named
public class DataFileHubFactory {

    private final String experimentsFilesLocation;

    @Inject
    public DataFileHubFactory(@Value("#{configuration['experimentsFilesLocation']}") String experimentsFilesLocation) {
        this.experimentsFilesLocation = experimentsFilesLocation;
    }

    public DataFileHub getGxaDataFileHub() {
        return new DataFileHub(Paths.get(experimentsFilesLocation, "gxa").toString());
    }

    public DataFileHub getScxaDataFileHub() {
        return new DataFileHub(Paths.get(experimentsFilesLocation, "scxa").toString());
    }

}
