package uk.ac.ebi.atlas.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OntologyTermTest {

    private static final String UBERON_0002107_URL = "http://purl.obolibrary.org/obo/UBERON_0002107";
    private static final OntologyTerm UBERON_0002107_TERM =
            OntologyTerm.create("UBERON_0002107", "liver", "http://purl.obolibrary.org/obo/", 9);

    @Test
    public void createFromURI() {
        assertThat(OntologyTerm.createFromURI(UBERON_0002107_URL).accession(), is(UBERON_0002107_TERM.accession()));
        assertThat(OntologyTerm.createFromURI(UBERON_0002107_URL).source(), is(UBERON_0002107_TERM.source()));
    }

    @Test
    public void uri() {
        assertThat(UBERON_0002107_TERM.uri(), is(UBERON_0002107_URL));
    }

    @Test
    public void uriWithoutSlashInSource() {
        assertThat(
                OntologyTerm.create("UBERON_0002107", "liver", "http://purl.obolibrary.org/obo", 9).uri(),
                is(UBERON_0002107_URL));
    }
}
