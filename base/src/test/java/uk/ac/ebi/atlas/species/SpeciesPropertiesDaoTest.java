package uk.ac.ebi.atlas.species;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.commons.io.IOUtils.toInputStream;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class SpeciesPropertiesDaoTest {

    private static final String SPECIES_PROPERTIES_JSON_WITH_UNKNOWN_FIELD =
            "[{" +
            "  \"name\":\"Arabidopsis_lyrata\", " +
            "  \"defaultQueryFactorType\":\"ORGANISM_PART\", " +
            "  \"kingdom\":\"plants\", " +
            "  \"resources\":[" +
            "      {" +
            "        \"type\":\"genome_browser\", " +
            "        \"name\":\"Ensembl Plants\", " +
            "        \"url\":\"http://plants.ensembl.org/Arabidopsis_lyrata\"" +
            "      }, " +
            "      {" +
            "        \"type\":\"genome_browser\", " +
            "        \"name\":\"Ensembl Plants\", " +
            "        \"url\":\"http://plants.ensembl.org/Arabidopsis_lyrata\"" +
            "      }" +
            "  ], " +
            "  \"foo\":\"bar\"" +
            "}]";

    private SpeciesPropertiesDao subject;

    @Test
    public void fetchAll() throws Exception {
        subject =
                new SpeciesPropertiesDao(
                        createSpeciesPropertiesFile(
                                SpeciesPropertiesDaoTest.class.getResourceAsStream("species-properties.json")));
        assertThat(subject.fetchAll(), hasSize(greaterThan(50)));
    }

    @Test
    public void unknownFieldsAreIgnored() throws Exception {
        subject =
                new SpeciesPropertiesDao(
                        createSpeciesPropertiesFile(toInputStream(
                                SPECIES_PROPERTIES_JSON_WITH_UNKNOWN_FIELD, StandardCharsets.UTF_8)));

        assertThat(subject.fetchAll(), hasSize(1));
    }

    @Test(expected = IOException.class)
    public void invalidJsonThrowsException() throws Exception {
        subject =
                new SpeciesPropertiesDao(
                        createSpeciesPropertiesFile(toInputStream("invalid JSON contents", StandardCharsets.UTF_8)));
        subject.fetchAll();
    }

    private Path createSpeciesPropertiesFile(InputStream in) throws IOException {
        Path tempDirPath = Files.createTempDirectory("");
        tempDirPath.toFile().deleteOnExit();
        Files.copy(in, tempDirPath.resolve("species-properties.json"));
        return tempDirPath.resolve("species-properties.json");
    }
}
