
package uk.ac.ebi.atlas.bioentity.protein;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.net.UnknownHostException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProteinPageControllerSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSP00000355434";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "proteins", "openPanelIndex=0");
        subject.get();
    }

    //This will fail with PhantomJS
    @Test
    public void checkCardExpansion() throws UnknownHostException {
        assertThat(subject.isInfoCardExpanded(), is(true));
        subject.clickInfoCard(false);
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void bioEntityPropertyPane() {
        assertThat(subject.getBioEntityCardTitle(), is("Q8N349 Homo sapiens olfactory receptor, family 2, subfamily L, member 13"));
        assertThat(subject.getPropertiesTableSize(), is(6));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Ensembl Protein", "ENSP00000355434"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Ensembl Transcript"));
        assertThat(subject.getPropertiesTableRow(1).get(1), containsString("ENST00000366478"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("UniProt", "Q8N349"));

        // this will fail if there was a timeout when fetching reactome ids from UniProt, see UniProtClient.fetchReactomeIds
        assertThat(subject.getPropertiesTableRow(5), hasItems("Reactome", "Olfactory Signaling Pathway"));

        assertThat(subject.getLinksInTableRow(1).get(0), containsString("http://www.ensemblgenomes.org/id/ENST00000366478"));
        assertThat(subject.getLinksInTableRow(2).get(0), startsWith("http://www.ensemblgenomes.org/id-gene/ENSG00000196071"));
        assertThat(subject.getLinksInTableRow(3).get(0), startsWith("http://www.uniprot.org/uniprot/Q8N349"));
        assertThat(subject.getLinksInTableRow(5).get(0), is("http://www.reactome.org/cgi-bin/eventbrowser_st_id?ST_ID=R-HSA-381753"));
    }
}