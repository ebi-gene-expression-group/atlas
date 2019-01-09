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
        String left = null;
        String right = null;
        ArrayList<String> info = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if ("left".equals(name)) {
                left = reader.nextString();
                info.add(left);
            } else if ("right".equals(name)) {
                right = reader.nextString();
                info.add(right);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return info;
    }

}
