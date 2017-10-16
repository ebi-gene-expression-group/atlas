package uk.ac.ebi.atlas.bioentity.go;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class GoPoTraderIT {

    private static final String GO_0000001 = "GO:0000001";
    private static final OntologyTerm GO_0000001_TERM = OntologyTerm.create(GO_0000001, "mitochondrion inheritance", "", 6);
    private static final String GO_2001317 = "GO:2001317";
    private static final OntologyTerm GO_2001317_TERM = OntologyTerm.create(GO_2001317, "kojic acid biosynthetic process", "", 6);
    private static final String PO_0000001 = "PO:0000001";
    private static final OntologyTerm PO_0000001_TERM = OntologyTerm.create(PO_0000001, "embryo proper");
    private static final String PO_0030087 = "PO:0030087";
    private static final OntologyTerm PO_0030087_TERM = OntologyTerm.create(PO_0030087, "non-vascular leaf initial cell");

    @Inject
    private GoPoTrader subject;

    @Test
    public void hasGO_0000001() throws Exception {
        assertThat(subject.getTerm(GO_0000001), is(Optional.of(GO_0000001_TERM)));
    }

    @Test
    public void hasGO_2001317() throws Exception {
        assertThat(subject.getTerm(GO_2001317), is(Optional.of(GO_2001317_TERM)));
    }

    @Test
    public void hasPO_0000001() throws Exception {
        assertThat(subject.getTerm(PO_0000001), is(Optional.of(PO_0000001_TERM)));
    }

    @Test
    public void hasPO_0030087() throws Exception {
        assertThat(subject.getTerm(PO_0030087), is(Optional.of(PO_0030087_TERM)));
    }

    @Test
    public void goTermName() throws Exception {
        assertThat(subject.getTermName(GO_0000001), is(Optional.of(GO_0000001_TERM.name())));
    }

    @Test
    public void poTermName() throws Exception {
        assertThat(subject.getTermName(PO_0030087), is(Optional.of(PO_0030087_TERM.name())));
    }

    @Test
    public void noTerm() throws Exception {
        assertThat(subject.getTerm("FO:OBAR"), is(Optional.empty()));
    }

    @Test
    public void noTermName() throws Exception {
        assertThat(subject.getTermName("FO:OBAR"), is(Optional.empty()));
    }
}
