package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenePageControllerCaseInsensitiveSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSG00000005801";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
    }

    @Test
    public void geneIdentifierIsCaseInsensitive() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER.toLowerCase(), "genes");
        subject.get();

        BioEntityPage target = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        target.get();

        subject.clickInfoCard(true);
        target.clickInfoCard(true);
        assertThat(subject.getBioEntityCardTitle(), is(target.getBioEntityCardTitle()));

        subject.clickBaselinePane();
        target.clickBaselinePane();
        assertThat(subject.getBaselinePaneContents(), is(target.getBaselinePaneContents()));

        subject.clickDifferentialPane();
        target.clickDifferentialPane();
        assertThat(subject.getDiffHeatmapTableGeneColumn(), is(target.getDiffHeatmapTableGeneColumn()));
    }
}
