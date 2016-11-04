package uk.ac.ebi.atlas.species;


import uk.ac.ebi.atlas.model.Species2;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Named
public class Species2DAO {

    private String speciesResourcePath;

    private Species2[] speciesArray;

    @Inject
    public Species2DAO(@Value("#{configuration['species.resource']}") String speciesResourcePath) {
        this.speciesResourcePath = speciesResourcePath;
    }

    private SpeciesJson[] readJson() {
        File file = new File(speciesResourcePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String str = new String(data, "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(str, SpeciesJson[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Species2 fetchSpecies(String name) {

        Function<SpeciesJson, Species2> speciesJsonToSpecies = new Function<SpeciesJson, Species2>() {
            @Nullable
            @Override
            public Species2 apply(@Nullable SpeciesJson input) {
                ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();
                for (ResourceJson resource : input.resources) {
                    builder.put(resource.type, resource.url);
                }

                return Species2.create(input.name, input.canonicalName, input.defaultQueryFactorType, input.kingdom, builder.build());
            }
        };

        Iterable<Species2> species = FluentIterable.from(Arrays.asList(readJson())).transform(speciesJsonToSpecies);

        ImmutableMap.Builder<String, Species2> speciesMapBuilder = ImmutableMap.builder();
        for (Species2 s : species) {
            speciesMapBuilder.put(s.name(), s);
        }
        return speciesMapBuilder.build().get(name);
    }

    private class SpeciesJson {
        private SpeciesJson() {}

        private String name;
        private String canonicalName;
        private String defaultQueryFactorType;
        private String kingdom;
        private ResourceJson[] resources;
    }

    private class ResourceJson {
        private ResourceJson() {};

        private String type;
        private String url;
    }
}
