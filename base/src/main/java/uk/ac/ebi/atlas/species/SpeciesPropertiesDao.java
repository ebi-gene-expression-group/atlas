package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.JsonFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;

@Named
public class SpeciesPropertiesDao {

    private final AtlasResource<JsonReader> speciesPropertiesJsonFile;

    @Inject
    public SpeciesPropertiesDao(Path speciesPropertiesFilePath) {
        speciesPropertiesJsonFile =
                new JsonFile.ReadOnly(
                        speciesPropertiesFilePath.getParent().toString(),
                        speciesPropertiesFilePath.getFileName().toString());
    }

    public ImmutableList<SpeciesProperties> fetchAll() throws IOException {
        ImmutableList.Builder<SpeciesProperties> allSpeciesPropertiesBuilder = ImmutableList.builder();

        try (JsonReader reader = speciesPropertiesJsonFile.get()) {
            reader.beginArray();
            while (reader.hasNext()) {
                allSpeciesPropertiesBuilder.add(readSpeciesProperties(reader));
            }
            reader.endArray();
        }

        return allSpeciesPropertiesBuilder.build();
    }

    private SpeciesProperties readSpeciesProperties(JsonReader reader) throws IOException {
        String ensemblName = null;
        String defaultQueryFactorType = null;
        String kingdom = null;
        ImmutableList.Builder<ImmutableMap<String, String>> resourcesBuilder = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if ("name".equals(name)) {
                ensemblName = reader.nextString();
            } else if ("defaultQueryFactorType".equals(name)) {
                defaultQueryFactorType = reader.nextString();
            } else if ("kingdom".equals(name)) {
                kingdom = reader.nextString();
            } else if ("resources".equals(name) && reader.peek() != JsonToken.NULL) {
                resourcesBuilder = ImmutableList.builder();
                reader.beginArray();
                while (reader.hasNext()) {
                    resourcesBuilder.add(readSpeciesResource(reader));
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return SpeciesProperties.create(
                ensemblName, defaultQueryFactorType, kingdom, resourcesBuilder.build());
    }

    private ImmutableMap<String, String> readSpeciesResource(JsonReader reader) throws IOException {
        ImmutableMap.Builder<String, String> resourceBuilder = ImmutableMap.builder();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            String value = reader.nextString();
            resourceBuilder.put(name, value);
        }
        reader.endObject();

        return resourceBuilder.build();
    }

}
