package uk.ac.ebi.atlas.bioentity.interpro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class InterProTraderIT {

    private static final OntologyTerm FIRST_TERM = OntologyTerm.create("IPR000001", "Kringle (domain)");
    private static final OntologyTerm LAST_TERM = OntologyTerm.create("IPR029787", "Nucleotide cyclase (domain)");

    @Inject
    InterProTrader subject;

    @Test
    public void hasFirstTerm() {
        assertThat(subject.get(FIRST_TERM.accession()), is(Optional.of(FIRST_TERM)));
    }

    @Test
    public void hasLastTerm() {
        assertThat(subject.get(LAST_TERM.accession()), is(Optional.of(LAST_TERM)));
    }

    @Test
    public void returnEmptyIfTermNotfound() {
        assertThat(subject.get("IPRFOOBAR"), is(Optional.empty()));
    }
}