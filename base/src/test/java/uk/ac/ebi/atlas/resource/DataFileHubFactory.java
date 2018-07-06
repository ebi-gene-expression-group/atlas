package uk.ac.ebi.atlas.resource;

import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class DataFileHubFactory {
    private final String experimentsFilesLocation;

    // Another possibility is to have an additional property appended to experimentsFilesLocation and use then
    // @TestPropertySource(properties = { "subproject=scxa" })
    public DataFileHubFactory(String dataFilesLocation) {
        this.experimentsFilesLocation = dataFilesLocation;
    }

    public DataFileHub getGxaDataFileHub() {
        return new DataFileHub(Paths.get(experimentsFilesLocation, "gxa").toString());
    }

    public DataFileHub getScxaDataFileHub() {
        return new DataFileHub(Paths.get(experimentsFilesLocation, "scxa").toString());
    }

}
