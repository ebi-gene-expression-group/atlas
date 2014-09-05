package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SpeciesLookupServiceIT {

    @Inject
    SpeciesLookupService speciesLookupService;

    @Test
    public void reactome_singleSpeciesGeneSet() {
        // REACT ids are always for a single species
        assertThat(speciesLookupService.fetchSpeciesForGeneSet("REACT_1619"), is("homo sapiens"));
    }

    @Test
    public void interPro_multiSpeciesGeneSet() {
        //assertThat(speciesLookupService.fetchSpeciesForGeneSet("IPR027417"), is("homo sapiens"));
    }

    @Test
    public void interPro_singleSpeciesGeneSet() {
        assertThat(speciesLookupService.fetchSpeciesForGeneSet("IPR016938"), is("schizosaccharomyces pombe"));
    }

    @Test
    public void GO_multiSpeciesGeneSet() {
        //assertThat(speciesLookupService.fetchSpeciesForGeneSet("GO:0003674"), is("homo sapiens"));
    }

    @Test
    public void GO_singleSpeciesGeneSet() {
        assertThat(speciesLookupService.fetchSpeciesForGeneSet("GO:0001962"), is("rattus norvegicus"));
    }

    @Test
    public void ensgeneId() {
        assertThat(speciesLookupService.fetchSpeciesForBioentityId("ENSMUSG00000021789"), is("mus musculus"));
    }

    @Test
    public void ensproteinId() {
        assertThat(speciesLookupService.fetchSpeciesForBioentityId("ENSP00000000233"), is("homo sapiens"));
    }

}