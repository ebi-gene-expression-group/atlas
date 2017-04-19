package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class SpeciesLookupServiceIT {

    @Inject
    private SpeciesLookupService subject;

    @Test
    public void reactome_singleSpeciesGeneSet() {
        // REACT pathway ids are always for a single species
        Optional<String> result = subject.fetchSpeciesForGeneSet("R-HSA-73887");
        assertThat(result.get(), is("homo sapiens"));
    }

    @Test
    public void interPro_multiSpeciesGeneSetAmbiguousCaseReturnsNoAnswer() {
        Optional<String> result = subject.fetchSpeciesForGeneSet("IPR027417");
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void GO_multiSpeciesGeneSetAmbiguousCaseReturnsNoAnswer() {
        Optional<String> result = subject.fetchSpeciesForGeneSet("GO:0003674");
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void PO_singleSpeciesGeneSet() {
        Optional<String> result = subject.fetchSpeciesForGeneSet("PO:0000013");
        assertThat(result.get(), is("arabidopsis thaliana"));
    }

    @Test
    public void ensgeneId() {
        assertThat(subject.fetchSpeciesForBioentityId("ENSMUSG00000021789").get(), is("mus musculus"));
    }

    @Test
    public void ensproteinId() {
        assertThat(subject.fetchSpeciesForBioentityId("ENSP00000000233").get(), is("homo sapiens"));
    }

    @Test
    public void plantReactomeId() {
        Optional<String> result = subject.fetchSpeciesForGeneSet("R-HSA-73887");
        assertThat(result.get(), is("homo sapiens"));
    }
}