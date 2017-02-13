package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class SpeciesFactoryIT {

    @Inject
    private SpeciesFactory subject;

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

}