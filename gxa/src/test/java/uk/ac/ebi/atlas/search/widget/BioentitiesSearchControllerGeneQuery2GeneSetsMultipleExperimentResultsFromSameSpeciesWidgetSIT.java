
package uk.ac.ebi.atlas.search.widget;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQuery2GeneSetsMultipleExperimentResultsFromSameSpeciesWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=R-HSA-69278%09R-HSA-162582");
        subject.get();
    }

    @Test
    public void baselinePaneResultsMessage() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));
    }

    @Test
    public void displaysWidget() {
        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues", "Illumina Body Map", "Vertebrate tissues", "Human Proteome Map - adult", "Human Proteome Map - fetus"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-2836?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-1733?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-MTAB-513?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(3), endsWith("/experiments/E-GEOD-30352?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=ORGANISM%3AHomo%20sapiens"));
        assertThat(subject.getGeneLink(4), endsWith("/experiments/E-PROT-1?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
        assertThat(subject.getGeneLink(5), endsWith("/experiments/E-PROT-1?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus"));
    }

    @Test
    public void hiddenBaselineResults() {
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(10));
        subject.clickMoreBaselineResults();
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(131));
    }
}
