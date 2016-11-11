package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class SpeciesLookupServiceIT {

    @Inject
    SpeciesLookupService speciesLookupService;

    @Test
    public void widget_lookupSingleSpeciesGeneSet() {
        assertThat(speciesLookupService.fetchFirstSpeciesByField(null, "Q9Y615").get(), is("homo sapiens"));
    }

    @Test
    public void widget_lookupProtein() {
        assertThat(speciesLookupService.fetchFirstSpeciesByField(null, "R-HSA-73887").get(), is("homo sapiens"));
    }

    @Test
    public void reactome_singleSpeciesGeneSet() {
        // REACT pathway ids are always for a single species
        Optional<String> result = speciesLookupService.fetchSpeciesForGeneSet("R-HSA-73887");
        assertThat(result.get(), is("homo sapiens"));
    }

    @Test
    public void interPro_multiSpeciesGeneSetAmbiguousCaseReturnsNoAnswer() {
        Optional<String> result = speciesLookupService.fetchSpeciesForGeneSet("IPR027417");
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void GO_multiSpeciesGeneSetAmbiguousCaseReturnsNoAnswer() {
        Optional<String> result = speciesLookupService.fetchSpeciesForGeneSet("GO:0003674");
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void PO_singleSpeciesGeneSet() {
        Optional<String> result = speciesLookupService.fetchSpeciesForGeneSet("PO:0000013");
        assertThat(result.get(), is("arabidopsis thaliana"));
    }

    @Test
    public void ensgeneId() {
        assertThat(speciesLookupService.fetchSpeciesForBioentityId("ENSMUSG00000021789").get(), is("mus musculus"));
    }

    @Test
    public void ensproteinId() {
        assertThat(speciesLookupService.fetchSpeciesForBioentityId("ENSP00000000233").get(), is("homo sapiens"));
    }

    @Test
    public void plantReactomeId() {
        Optional<String> result = speciesLookupService.fetchSpeciesForGeneSet("R-HSA-73887");
        assertThat(result.get(), is("homo sapiens"));
    }
}