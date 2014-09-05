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
        // REACT pathway ids are always for a single species
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet("REACT_1619");
        assertThat(result.isMultiSpecies(), is(false));
        assertThat(result.firstSpecies(), is("homo sapiens"));
    }

    @Test
    public void interPro_multiSpeciesGeneSet() {
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet("IPR027417");
        assertThat(result.isMultiSpecies(), is(true));
        assertThat(result.firstSpecies(), is("homo sapiens"));
    }

    @Test
    public void interPro_singleSpeciesGeneSet() {
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet("IPR016938");
        assertThat(result.isMultiSpecies(), is(false));
        assertThat(result.firstSpecies(), is("schizosaccharomyces pombe"));
    }

    @Test
    public void GO_multiSpeciesGeneSet() {
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet("GO:0003674");
        assertThat(result.isMultiSpecies(), is(true));
        assertThat(result.firstSpecies(), is("arabidopsis thaliana"));
    }

    @Test
    public void GO_singleSpeciesGeneSet() {
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet("GO:0001962");
        assertThat(result.isMultiSpecies(), is(false));
        assertThat(result.firstSpecies(), is("rattus norvegicus"));
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