package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Test;
import uk.ac.ebi.atlas.model.OntologyTerm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FactorTest {

    private OntologyTerm ontologyTerm1 = OntologyTerm.create("UBERON_0002349", "http://purl.obolibrary.org/obo/");
    private OntologyTerm ontologyTerm2 = OntologyTerm.create("UBERON_0002082", "http://purl.obolibrary.org/obo/");
    private OntologyTerm ontologyTerm3 = OntologyTerm.create("UBERON_0002082", "http://purl.obolibrary.org/obo/");
    private Factor subject = new Factor("type", "value", ontologyTerm1, ontologyTerm2, ontologyTerm3);

    @Test
    public void repeatedOntologyTermsAreUnique() {
        assertThat(subject.getValueOntologyTerms().size(), is(2));
        assertThat(subject.getValueOntologyTerms(), containsInAnyOrder(ontologyTerm1, ontologyTerm2));
    }
}