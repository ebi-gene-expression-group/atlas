
package uk.ac.ebi.atlas.experimentpage.heatmap;

import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneNameLinksSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void clickingOnGeneNameShouldTakeToTheGenePage() {
        BioEntityPage bioEntityPage = subject.clickGeneName(1);
        assertThat(bioEntityPage.getBioEntityCardTitle(), is("TEX33 Homo sapiens testis expressed 33"));
    }

}
