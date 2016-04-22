package uk.ac.ebi.atlas.bioentity.go;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class GoPoTermTraderIT {

    private static final String GO_0000001 = "GO:0000001";
    private static final OntologyTerm GO_0000001_TERM = OntologyTerm.create(GO_0000001, "mitochondrion inheritance", "", 6);
    private static final String GO_2001317 = "GO:2001317";
    private static final OntologyTerm GO_2001317_TERM = OntologyTerm.create(GO_2001317, "kojic acid biosynthetic process", "", 6);
    private static final String PO_0000001 = "PO:0000001";
    private static final OntologyTerm PO_0000001_TERM = OntologyTerm.create(PO_0000001, "embryo proper");
    private static final String PO_0030087 = "PO:0030087";
    private static final OntologyTerm PO_0030087_TERM = OntologyTerm.create(PO_0030087, "non-vascular leaf initial cell");


    @Inject
    GoPoTermTrader subject;

    @Test
    public void hasGO_0000001() throws IOException {
        assertEquals(subject.getTerm(GO_0000001), GO_0000001_TERM);
    }

    @Test
    public void hasGO_2001317() throws IOException {
        assertEquals(subject.getTerm(GO_2001317), GO_2001317_TERM);
    }

    @Test
    public void hasPO_0000001() throws IOException {
        assertEquals(subject.getTerm(PO_0000001), PO_0000001_TERM);
    }

    @Test
    public void hasPO_0030087() throws IOException {
        assertEquals(subject.getTerm(PO_0030087), PO_0030087_TERM);
    }

}
