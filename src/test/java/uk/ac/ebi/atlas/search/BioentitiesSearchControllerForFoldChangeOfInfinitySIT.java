
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class BioentitiesSearchControllerForFoldChangeOfInfinitySIT extends SinglePageSeleniumFixture {



    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=Prok1");
        subject.get();
    }

    @Test
    public void profileShowsInfinity() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getFoldChange(), hasItems("∞"));
    }

}
