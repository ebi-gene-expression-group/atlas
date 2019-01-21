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
            reader.beginArray();
            while (reader.hasNext()) {
                atlasInformation.addAll(readAtlasInformation(reader));
            }
            reader.endArray();
        }

        return atlasInformation;
    }

    private ArrayList<String> readAtlasInformation(JsonReader reader) throws IOException {
        ArrayList<String> info = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch(name){
                case "Ensembl": info.add("Ensembl: " + reader.nextString()); break;
                case "Ensembl Genomes": info.add("Ensembl Genomes: " + reader.nextString()); break;
                case "WormBase paraSite": info.add("WormBase paraSite: " + reader.nextString()); break;
                case "EFO": info.add("EFO: " + reader.nextString()); break;
                default: reader.skipValue();
            }
        }
        reader.endObject();

        return info;
    }

}
