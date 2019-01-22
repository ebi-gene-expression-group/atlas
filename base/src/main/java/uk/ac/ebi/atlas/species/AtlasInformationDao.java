package uk.ac.ebi.atlas.species;

import com.google.gson.stream.JsonReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.JsonFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

@Named
public class AtlasInformationDao {

    private final AtlasResource<JsonReader> atlasInformationJsonFile;

    @Inject
    public AtlasInformationDao(Path atlasInformationFilePath) {
        atlasInformationJsonFile =
                new JsonFile.ReadOnly(
                        atlasInformationFilePath.getParent().toString(),
                        atlasInformationFilePath.getFileName().toString());
    }

    public ArrayList<String> fetchAll() throws IOException {
        ArrayList<String> atlasInformation = new ArrayList<>();
        try (JsonReader reader = atlasInformationJsonFile.get()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch(name){
                    case "ensembl": atlasInformation.add("Ensembl: " + reader.nextString()); break;
                    case "genomes": atlasInformation.add("Ensembl Genomes: " + reader.nextString()); break;
                    case "paraSite": atlasInformation.add("WormBase paraSite: " + reader.nextString()); break;
                    case "efo": atlasInformation.add("EFO: " + reader.nextString()); break;
                    default: reader.skipValue();
                }
            }
            reader.endObject();
        }
        return atlasInformation;
    }
}
