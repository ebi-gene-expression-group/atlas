package uk.ac.ebi.atlas.species;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class SpeciesPropertiesDaoTest {

    private static final String[] SPECIES_PROPERTIES_JSON_WITH_UNKNOWN_FIELD = {
            "[{",
            "  \"name\":\"Arabidopsis_lyrata\",",
            "  \"defaultQueryFactorType\":\"ORGANISM_PART\",",
            "  \"kingdom\":\"plants\",",
            "  \"resources\":[{",
            "    \"type\":\"genome_browser\",",
            "    \"urls\":[\"http://plants.ensembl.org/Arabidopsis_lyrata\",\"http://ensembl.gramene.org/Arabidopsis_lyrata\"]",
            "  }],",
            "  \"foo\":\"bar\"",
            "}]"
    };

    private static MockDataFileHub dataFileHub;

    private SpeciesPropertiesDao subject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
    }

    @Before
    public void setUp() throws Exception {
        subject = new SpeciesPropertiesDao();
        subject.setDataFileHub(dataFileHub);
    }

    @Test
    public void fetchAll() throws Exception {
        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesPropertiesTraderTest.class.getResourceAsStream("species-properties.json"),
                        StandardCharsets.UTF_8));
        assertThat(subject.fetchAll(), hasSize(greaterThan(50)));
    }

    @Test
    public void unknownFieldsAreIgnored() throws Exception {
        dataFileHub.addSpeciesJsonFile(Lists.newArrayList(SPECIES_PROPERTIES_JSON_WITH_UNKNOWN_FIELD));
        assertThat(subject.fetchAll(), hasSize(1));
    }

    @Test(expected = IOException.class)
    public void invalidJsonThrowsException() throws Exception {
        dataFileHub.addSpeciesJsonFile(Lists.newArrayList("invalid", "json", "contents"));
        subject.fetchAll();
    }

}