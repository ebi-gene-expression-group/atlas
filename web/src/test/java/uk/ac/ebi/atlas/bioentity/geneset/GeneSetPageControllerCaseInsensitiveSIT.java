package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GeneSetPageControllerCaseInsensitiveSIT extends SinglePageSeleniumFixture {

    private static final String REACTOME_IDENTIFIER = "R-HSA-1474244";
    private static final String INTERPRO_IDENTIFIER = "IPR000003";
    private static final String GO_IDENTIFIER = "GO:0043167";
    private static final String PO_IDENTIFIER = "PO:0000013";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
    }

    @Test
    public void reactomeIdentifierIsCaseInsensitive() {
        subject = new BioEntityPage(driver, REACTOME_IDENTIFIER.toLowerCase(), "genesets");
        subject.get();

        BioEntityPage target = new BioEntityPage(driver, REACTOME_IDENTIFIER, "genesets");
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

    @Test
    public void interproIdentifierIsCaseInsensitive() {
        subject = new BioEntityPage(driver, INTERPRO_IDENTIFIER.toLowerCase(), "genesets");
        subject.get();

        BioEntityPage target = new BioEntityPage(driver, INTERPRO_IDENTIFIER, "genesets");
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

    @Test
    public void goIdentifierIsCaseInsensitive() {
        subject = new BioEntityPage(driver, GO_IDENTIFIER.toLowerCase(), "genesets");
        subject.get();

        BioEntityPage target = new BioEntityPage(driver, GO_IDENTIFIER, "genesets");
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

    @Test
    public void poIdentifierIsCaseInsensitive() {
        subject = new BioEntityPage(driver, PO_IDENTIFIER.toLowerCase(), "genesets");
        subject.get();

        BioEntityPage target = new BioEntityPage(driver, PO_IDENTIFIER, "genesets");
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
