package uk.ac.ebi.atlas.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OntologyTermTest {

    @Test
    public void splitAtFinalSlash() {
        assertThat(OntologyTerm.splitAtFinalSlash("http://purl.obolibrary.org/obo/UBERON:0002107"), is(new String[]{"http://purl.obolibrary.org/obo/", "UBERON:0002107"}));
    }

    @Test
    public void splitNoSlash() {
        assertThat(OntologyTerm.splitAtFinalSlash("UBERON:0002107"), is(new String[]{null, "UBERON:0002107"}));
    }

    @Test
    public void termWithSource() {
        OntologyTerm ontologyTerm = new OntologyTerm("UBERON:0002107", "http://purl.obolibrary.org/obo/");
        assertThat(ontologyTerm.uri(), is("http://purl.obolibrary.org/obo/UBERON:0002107"));
    }

    @Test
    public void termWithSourceNoEndingInSlash() {
        OntologyTerm ontologyTerm = new OntologyTerm("UBERON:0002107", "UBERON");
        assertThat(ontologyTerm.uri(), is("UBERON/UBERON:0002107"));
    }

}