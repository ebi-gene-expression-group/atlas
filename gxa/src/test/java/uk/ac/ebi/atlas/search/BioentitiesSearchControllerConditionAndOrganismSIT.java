
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchControllerConditionAndOrganismSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "organism=Homo+sapiens&condition=adult");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(3));

        BaselineBioEntitiesSearchResult result1 = selectResult(baselineCounts, "E-PROT-1");
        assertThat(result1.getExperimentName(), is("Human Proteome Map - adult"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=B%20cell,CD4-positive%20T%20cell,CD8-positive%20T%20cell,adrenal%20gland,colon,esophagus,frontal%20cortex,gallbladder,heart,kidney,liver,lung,monocyte,natural%20killer%20cell,ovary,pancreas,platelet,prostate,rectum,retina,spinal%20cord,testis,urinary%20bladder&geneQuery=&serializedFilterFactors=DEVELOPMENTAL_STAGE:adult"));

        BaselineBioEntitiesSearchResult result2 = selectResult(baselineCounts, "E-MTAB-1733");
        assertThat(result2.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery="));
    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDifferentialPane();
        assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 9859 results"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains(Collections.nCopies(50, "Homo sapiens").toArray()));

    }

}
