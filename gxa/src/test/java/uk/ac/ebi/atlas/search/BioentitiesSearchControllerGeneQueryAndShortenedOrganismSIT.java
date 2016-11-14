
package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQueryAndShortenedOrganismSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "organism=Hordeum+vulgare&condition=flower");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(1));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-2809"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Tissues - 8 Mayer"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Hordeum vulgare subsp. vulgare"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-MTAB-2809?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=caryopsis%20(15%20dpa),caryopsis%20(5%20dpa)&geneQuery="));

    }


}
