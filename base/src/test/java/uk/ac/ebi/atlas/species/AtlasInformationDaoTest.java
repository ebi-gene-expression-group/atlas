package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

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
        assertThat(subject.fetchAll())
                .containsAllEntriesOf(
                        ImmutableMap.of(
                                "ensembl", "94",
                                "ensembl_genomes", "41",
                                "wormbase_parasite", "11",
                                "efo", "2.98"));
    }
}
