package uk.ac.ebi.atlas.species;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
public class SpeciesDao {

    private DataFileHub dataFileHub;

    @Inject
    public void setDataFileHub(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SpeciesConfigurationRecord fetchSpecies(String SpeciesConfigurationRecord) throws IOException {
        try (JsonReader reader = dataFileHub.getSpeciesFiles().all.get()) {
            reader.beginArray();
            while (reader.hasNext()) {
                SpeciesConfigurationRecord speciesJson = readSpecies(reader);
                if (speciesJson.name().equals(SpeciesConfigurationRecord)) {
                    return speciesJson;
                }
            }
            reader.endArray();
        }

        return null;
    }

    public List<SpeciesConfigurationRecord> fetchAllSpecies() throws IOException {
        ArrayList<SpeciesConfigurationRecord> allSpecies = Lists.newArrayList();

        try (JsonReader reader = dataFileHub.getSpeciesFiles().all.get()) {
            reader.beginArray();
            while (reader.hasNext()) {
                allSpecies.add(readSpecies(reader));
            }
            reader.endArray();
        }

        return allSpecies;
    }

    private SpeciesConfigurationRecord readSpecies(JsonReader reader) throws IOException {
        String speciesName = null;
        String defaultQueryFactorType = null;
        String kingdom = null;
        Map<String, List<String>> resources = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if ("name".equals(name)) {
                speciesName = reader.nextString();
            } else if ("defaultQueryFactorType".equals(name)) {
                defaultQueryFactorType = reader.nextString();
            } else if ("kingdom".equals(name)) {
                kingdom = reader.nextString();
            } else if ("resources".equals(name) && reader.peek() != JsonToken.NULL) {
                resources = Maps.newHashMap();
                reader.beginArray();
                while (reader.hasNext()) {
                    Pair<String, List<String>> resource = readResource(reader);
                    resources.put(resource.getLeft(), resource.getRight());
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return SpeciesConfigurationRecord.create(speciesName, defaultQueryFactorType, kingdom, resources);
    }

    private Pair<String, List<String>> readResource(JsonReader reader) throws IOException {
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
