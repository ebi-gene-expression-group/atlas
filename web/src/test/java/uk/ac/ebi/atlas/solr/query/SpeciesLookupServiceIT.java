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
    public void singleSpeciesGeneSet_React() {
        assertThat(speciesLookupService.fetchSpeciesForGeneSet("REACT_1619"), is("homo sapiens"));
    }

    @Test
    public void multiSpeciesGeneSet_Interpro() {
        //assertThat(speciesLookupService.fetchSpeciesForGeneSet("IPR027417"), is("homo sapiens"));

    }

    @Test
    public void multiSpeciesGeneSet_GO() {
        //assertThat(speciesLookupService.fetchSpeciesForGeneSet("GO:0003674"), is("homo sapiens"));
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