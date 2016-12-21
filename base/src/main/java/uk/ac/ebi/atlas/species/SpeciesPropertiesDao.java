package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Named
public class SpeciesPropertiesDao {

    private DataFileHub dataFileHub;

    @Inject
    public void setDataFileHub(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

//    public SpeciesProperties get(String species) throws IOException {
//        try (JsonReader reader = dataFileHub.getSpeciesPropertiesFile().json.get()) {
//            reader.beginArray();
//            while (reader.hasNext()) {
//                SpeciesProperties speciesJson = readSpeciesProperties(reader);
//                if (speciesJson.referenceName().equals(species)) {
//                    return speciesJson;
//                }
//            }
//            reader.endArray();
//        }
//
//        return null;
//    }

    public ImmutableList<SpeciesProperties> getAll() throws IOException {
        ImmutableList.Builder<SpeciesProperties> allSpeciesPropertiesBuilder = ImmutableList.builder();

        try (JsonReader reader = dataFileHub.getSpeciesPropertiesFile().json.get()) {
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
        Map<String, List<String>> resources = null;

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
                resources = Maps.newHashMap();
                reader.beginArray();
                while (reader.hasNext()) {
                    Pair<String, List<String>> resource = readSpeciesResource(reader);
                    resources.put(resource.getLeft(), resource.getRight());
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return SpeciesProperties.create(
                ensemblName.toLowerCase().replace("_", " "), ensemblName, defaultQueryFactorType, kingdom, resources);
    }

    private Pair<String, List<String>> readSpeciesResource(JsonReader reader) throws IOException {
        String type = null;
        List<String> urls = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if ("type".equals(name)) {
                type = reader.nextString();
            } else if ("urls".equals(name) && reader.peek() != JsonToken.NULL) {
                urls = readStringsArray(reader);
            }
        }
        reader.endObject();
        return Pair.of(type, urls);
    }

    private List<String> readStringsArray(JsonReader reader) throws IOException {
        List<String> strings = Lists.newArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();

        return strings;
    }

}
