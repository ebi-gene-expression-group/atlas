package uk.ac.ebi.atlas.species;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Component
public class AtlasInformationDao {
    private final Path atlasInformationFilePath;
    public final LazyReference<ImmutableMap<String, String>> atlasInformation =
            new LazyReference<ImmutableMap<String, String>>() {
                @Override
                protected ImmutableMap<String, String> create() {
                    try {
                        JsonReader jsonReader =
                                new JsonReader(
                                        Files.newBufferedReader(atlasInformationFilePath, StandardCharsets.UTF_8));
                        ImmutableMap<String, String> atlasInformation =
                                ImmutableMap.copyOf(GSON.<HashMap<String, String>>fromJson(jsonReader, HashMap.class));
                        jsonReader.close();
                        return atlasInformation;
                    } catch (IOException e) {
                        return ImmutableMap.of(
                                "ensembl", "unknown",
                                "ensembl_genomes", "unknown",
                                "wormbase_parasite", "unknown",
                                "efo", "unknown");
                    }
                }
            };

    public AtlasInformationDao(Path atlasInformationFilePath) {
        this.atlasInformationFilePath = atlasInformationFilePath;
    }
}
