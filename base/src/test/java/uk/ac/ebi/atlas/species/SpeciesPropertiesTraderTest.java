package uk.ac.ebi.atlas.species;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SpeciesPropertiesTraderTest {

    private static MockDataFileHub dataFileHub;

    private SpeciesPropertiesDao speciesPropertiesDao = new SpeciesPropertiesDao();

    private int speciesPropertiesCountBeforeRefresh;

    private SpeciesPropertiesTrader subject = new SpeciesPropertiesTrader();

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
    }

    @Before
    public void setUp() throws IOException {
        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesPropertiesTraderTest.class.getResourceAsStream("species-properties.json"),
                        StandardCharsets.UTF_8));

        speciesPropertiesDao.setDataFileHub(dataFileHub);
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
        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesPropertiesTraderTest.class.getResourceAsStream("species-tyrannosaurus-rex.json"),
                        StandardCharsets.UTF_8));
        subject.refresh();

        assertThat(subject.getAll(), hasSize(1));
        assertThat(subject.getAll().iterator().next().referenceName(), is("tyrannosaurus rex"));

    }

    @Test
    public void refreshDescribesChanges() throws Exception {
        String refreshMessage = subject.refresh();
        assertThat(refreshMessage, is("No changes were made to the reference species"));

        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesPropertiesTraderTest.class.getResourceAsStream("species-tyrannosaurus-rex.json"),
                        StandardCharsets.UTF_8));
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
            dataFileHub.addSpeciesJsonFile(Lists.newArrayList("invalid", "json", "contents"));
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
}