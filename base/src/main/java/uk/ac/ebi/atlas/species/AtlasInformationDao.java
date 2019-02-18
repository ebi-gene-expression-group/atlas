package uk.ac.ebi.atlas.species;

import com.google.gson.stream.JsonReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.JsonFile;
import uk.ac.ebi.atlas.utils.GsonProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String>  fetchAll() {
        JsonReader reader = atlasInformationJsonFile.get();
        Map<String, String> data = GsonProvider.GSON.fromJson(reader, HashMap.class);

        return data;
    }
}
