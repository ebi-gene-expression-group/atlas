
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionAndGeneQuerySIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=apoptotic+process&condition=%22wild+type%22");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(8));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-2812"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Developmental stages - hermaphrodite, NSM"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Caenorhabditis elegans"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-MTAB-2812?_specific=on&queryFactorType=DEVELOPMENTAL_STAGE&queryFactorValues=L1%20larva%20Ce&geneQuery=apoptotic+process&exactMatch=true&serializedFilterFactors=SEX:hermaphrodite,ORGANISM_PART:NSM"));
    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.diffExpressionResultCount(), is("Showing 10 results"));
        assertThat(subject.getContrastColumn(), hasItem("compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36"));
        assertThat(subject.getFoldChange(), hasItems("-1.9", "1.9", "-1.8", "-1.7", "1.7", "1.5", "1.3", "1.2", "1.2", "1.2"));

        //System.out.println("\"" + Joiner.on("\", \"").join(subject.getDiffHeatmapTableGeneColumn()) + "\"");
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("CG4332", "Trp53inp1", "Apip", "Tao", "Gja1", "Zfp36l1", "wgn", "Tgfbr1", "Sgms1", "Mef2a"));
    }

}
