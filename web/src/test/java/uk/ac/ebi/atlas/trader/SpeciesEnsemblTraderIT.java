package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Map;
import java.util.SortedSet;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SpeciesEnsemblTraderIT {

    private SortedSet<String> species;

    @Inject
    private SpeciesEnsemblTrader subject;

    @Before
    public void setUp() throws Exception {
        species = Sets.newTreeSet();
        species.add("Homo sapiens");
        species.add("Mus musculus");
        species.add("Arabidopsis thaliana");

    }

    @Test
    public void testGetEnsemblAcc() throws Exception {

        SortedSet<String> ensemblAccessions = subject.getEnsemblAccessions(species);

        assertThat(species, hasItems("Homo sapiens", "Mus musculus", "Arabidopsis thaliana"));
        assertThat(ensemblAccessions, hasItems("ensembl", "plants"));
        assertThat(subject.getEnsemblAccession("Caenorhabditis elegans"), is("metazoa"));
        assertThat(subject.getEnsemblAccession("Bos taurus"), is("ensembl"));
    }

    @Test
    public void testGetSpeciesEnsemblMap() throws Exception {

        Map<String, String> speciesEnsemblMap = subject.getSpeciesEnsemblMap();

        assertThat(speciesEnsemblMap.size(), is(30));
        assertThat(speciesEnsemblMap.get("Homo sapiens"), is("ensembl"));

    }


}
