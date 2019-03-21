package uk.ac.ebi.atlas.species;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

class AtlasInformationDaoTest {
    private AtlasInformationDao subject;

    @BeforeEach
    void setUp() throws Exception {
        File tmpFile = File.createTempFile("foo", ".json");

        tmpFile.deleteOnExit();
        try {
            Files.write(
                    tmpFile.toPath(),
                    ImmutableList.of(
                            "{",
                            "  \"ensembl\": \"94\",\n",
                            "  \"ensembl_genomes\": \"41\",\n",
                            "  \"wormbase_parasite\": \"11\",\n",
                            "  \"efo\": \"2.98\"\n" +
                            "}"),
                    Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        subject = new AtlasInformationDao(tmpFile.toPath());
    }

    @Test
    void retrievesData() {
        assertThat(subject.atlasInformation.get())
                .containsAllEntriesOf(
                        ImmutableMap.of(
                                "ensembl", "94",
                                "ensembl_genomes", "41",
                                "wormbase_parasite", "11",
                                "efo", "2.98"));
    }

    @Test
    void noFileLeaksPlease() {
        assertThat(subject.atlasInformation)
                .isInstanceOf(LazyReference.class);
    }

    @Test
    void returnsUnknownIfFileCannotBeRead() throws Exception {
        subject = new AtlasInformationDao(Paths.get(randomAlphabetic(10)));
        assertThat(subject.atlasInformation.get())
                .containsOnlyKeys("ensembl", "ensembl_genomes", "wormbase_parasite", "efo")
                .containsValues("unknown");

        File tmpFile = File.createTempFile("foo", ".json");
        tmpFile.deleteOnExit();
        assertThat(subject.atlasInformation.get())
                .containsOnlyKeys("ensembl", "ensembl_genomes", "wormbase_parasite", "efo")
                .containsValues("unknown");
    }
}
