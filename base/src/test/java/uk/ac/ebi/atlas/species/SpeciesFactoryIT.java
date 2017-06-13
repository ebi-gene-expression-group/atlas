package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class SpeciesFactoryIT {

    @Inject
    SpeciesFactory subject;

    @Inject
    SpeciesPropertiesTrader speciesPropertiesTrader;

    @Test
    public void differentSpeciesSameProperties() {
        Species oryzaJaponica = subject.create("Oryza sativa Japonica group");
        Species oryzaIndica = subject.create("Oryza sativa Indica group");

        assertThat(oryzaJaponica.isUnknown(), is(false));
        assertThat(oryzaIndica.isUnknown(), is(false));

        assertThat(oryzaJaponica.getName(), is(not(oryzaIndica.getName())));
        assertThat(oryzaJaponica.getReferenceName(), is(oryzaIndica.getReferenceName()));
        assertThat(oryzaJaponica.getEnsemblName(), is(oryzaIndica.getEnsemblName()));
        assertThat(oryzaJaponica.getDefaultQueryFactorType(), is(oryzaIndica.getDefaultQueryFactorType()));
        assertThat(oryzaJaponica.getKingdom(), is(oryzaIndica.getKingdom()));

        assertThat(oryzaJaponica.isPlant(), is(true));
        assertThat(oryzaIndica.isPlant(), is(true));
    }

    @Test
    public void speciesComeWithGenomeBrowsers() {
        // Currently this is the only resource we care about
        assertThat(subject.create("homo sapiens").getGenomeBrowsers().size(), greaterThan(0));
    }

    @Test
    public void unknownSpecies() {
        assertThat(subject.create("foobar").isUnknown(), is(true));
    }

    @Test
    public void emptySpecies() {
        assertThat(subject.create("").isUnknown(), is(true));
    }

    @Test
    public void nullSpecies() {
        assertThat(subject.create(null).isUnknown(), is(true));
    }

    @Test
    public void createUnknownSpecies() {
        assertThat(subject.createUnknownSpecies().isUnknown(), is(true));
    }

    @Test
    public void oneSpeciesIsHuman(){
        Set<String> result = new HashSet<>();
        for(SpeciesProperties speciesProperties: speciesPropertiesTrader.getAll())
            if(subject.create(speciesProperties.ensemblName()).isUs()){
                result.add(speciesProperties.ensemblName());
            }
        assertThat(
                result,
                hasSize(1)
        );
    }
}