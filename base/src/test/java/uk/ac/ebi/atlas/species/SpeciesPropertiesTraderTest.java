package uk.ac.ebi.atlas.species;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.io.IOUtils.toInputStream;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class SpeciesPropertiesTraderTest {

    private Path location;

    private int speciesPropertiesCountBeforeRefresh;

    private SpeciesPropertiesTrader subject = new SpeciesPropertiesTrader();

    @Before
    public void setUp() throws IOException {
        location =
                createSpeciesPropertiesFile(
                        SpeciesPropertiesTraderTest.class.getResourceAsStream("species-properties.json"));
        SpeciesPropertiesDao speciesPropertiesDao = new SpeciesPropertiesDao(location.toString());
        subject.setSpeciesPropertiesDao(speciesPropertiesDao);

        subject.refresh();
        speciesPropertiesCountBeforeRefresh = subject.getAll().size();
    }

    @Test
    public void getAll() throws Exception {
        assertThat(subject.getAll(), hasSize(greaterThan(50)));
    }

    @Test
    public void get() throws Exception {
        assertThat(subject.get("Homo sapiens").referenceName(), is("homo sapiens"));
        assertThat(subject.get("Homo sapiens").ensemblName(), is("Homo_sapiens"));
        assertThat(subject.get("Homo sapiens").kingdom(), is("animals"));
        assertThat(subject.get("Hordeum vulgare").getResourcesOfType("genome_browser"), hasSize(2));
    }

    @Test
    public void refresh() throws Exception {
        Files.copy(
                SpeciesPropertiesTraderTest.class.getResourceAsStream("species-tyrannosaurus-rex.json"),
                location.resolve("species-properties.json"),
                REPLACE_EXISTING);
        subject.refresh();

        assertThat(subject.getAll(), hasSize(1));
        assertThat(subject.getAll().iterator().next().referenceName(), is("tyrannosaurus rex"));

    }

    @Test
    public void refreshDescribesChanges() throws Exception {
        String refreshMessage = subject.refresh();
        assertThat(refreshMessage, is("No changes were made to the reference species"));

        Files.copy(
                SpeciesPropertiesTraderTest.class.getResourceAsStream("species-tyrannosaurus-rex.json"),
                location.resolve("species-properties.json"),
                REPLACE_EXISTING);
        refreshMessage = subject.refresh();

        Pattern removedSpeciesPattern = Pattern.compile(".*\\[(.+)\\] removed.*");
        Matcher matcher = removedSpeciesPattern.matcher(refreshMessage);
        matcher.find();
        assertThat(matcher.group(1).split(", ").length, is(speciesPropertiesCountBeforeRefresh));
        assertThat(refreshMessage, containsString("[Tyrannosaurus_rex] added"));
    }

    @Test
    public void failedRefreshKeepsOldProperties() throws Exception {
        int speciesPropertiesCountBeforeRefresh = subject.getAll().size();

        try {
            Files.copy(
                    toInputStream("invalid JSON contents", StandardCharsets.UTF_8),
                    location.resolve("species-properties.json"));
            subject.refresh();
            // We should never get here
            throw new RuntimeException();
        } catch (IOException e) {
            assertThat(subject.getAll(), hasSize(speciesPropertiesCountBeforeRefresh));
        }
    }

    @Test
    public void unknownSpecies() throws Exception {
        assertThat(subject.get("foobar"), is(SpeciesProperties.UNKNOWN));
        assertThat(subject.get(null), is(SpeciesProperties.UNKNOWN));
    }

    @Test
    public void speciesNamesAreNormalised() throws Exception {
        assertThat(subject.get("homo sapiens"), not(is(SpeciesProperties.UNKNOWN)));
        assertThat(subject.get("homo sapiens"), is(subject.get("HoMo_SaPieNs")));
        assertThat(subject.get("homo sapiens"), is(subject.get("Homo sapiens")));

        assertThat(subject.get("hordeum vulgare"), not(is(SpeciesProperties.UNKNOWN)));
        assertThat(subject.get("hordeum vulgare"), is(subject.get("Hordeum vulgare subsp. vulgare")));
        assertThat(subject.get("hordeum vulgare"), is(subject.get("Hordeum_vulgare")));
    }

    private Path createSpeciesPropertiesFile(InputStream in) throws IOException {
        Path tempDirPath = Files.createTempDirectory(null);
        Files.copy(in, tempDirPath.resolve("species-properties.json"));
        return tempDirPath;
    }
}