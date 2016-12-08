package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class SpeciesTraderIT {

    private static MockDataFileHub dataFileHub;

    private SpeciesDao speciesDao = new SpeciesDao();

    private SpeciesTrader subject = new SpeciesTrader();

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesTraderIT.class.getResourceAsStream("species-all.json"), StandardCharsets.UTF_8));
    }

    @Before
    public void setUp() throws IOException {
        speciesDao.setDataFileHub(dataFileHub);
        subject.setSpeciesDao(speciesDao);
    }

    @Test
    public void getSpeciesByName() {
        assertThat(subject.getByName("homo_sapiens"), is(subject.getByName("Homo sapiens")));
    }

    @Test
    public void getSpeciesWithSubspecies() {
        assertThat(subject.getByName("Hordeum vulgare subsp. vulgare"), is(subject.getByName("hordeum_vulgare")));
    }

    @Test
    public void getSpeciesByKingdom() throws Exception {
        int count = 0;
        for (String kingdom : ImmutableList.of("animals", "plants", "fungi")) {
            Collection<SpeciesConfigurationRecord> kingdomSpecies = subject.getByKingdom(kingdom);

            count += kingdomSpecies.size();

            for (SpeciesConfigurationRecord species : kingdomSpecies) {
                assertThat(species.kingdom(), is(kingdom));
            }
        }

        assertThat(speciesDao.fetchAllSpecies(), hasSize(count));
    }

    @Test
    public void refreshSpecies() throws Exception {
        assertThat(subject.getByName("Tyrannosaurus rex"), is(nullValue()));
        dataFileHub.addSpeciesJsonFile(
                IOUtils.readLines(
                        SpeciesTraderIT.class.getResourceAsStream("species-tyrannosaurus-rex.json"), StandardCharsets.UTF_8));
        subject.refresh();
        assertThat(subject.getByName("Tyrannosaurus rex"), is(not(nullValue())));
    }

}