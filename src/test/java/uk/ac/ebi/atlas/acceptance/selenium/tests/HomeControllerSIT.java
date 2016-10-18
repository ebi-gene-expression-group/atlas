
package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HomePage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HomeControllerSIT extends SinglePageSeleniumFixture {

    private HomePage subject;

    public void getStartingPage() {
        subject = new HomePage(driver);
        subject.get();
    }

    @Ignore //TODO fix
    @Test
    public void onSearchSubmissionRedirectToGeneQueryURL() throws InterruptedException {
        String geneQuery = "ENSG00000161547";
        subject.setGeneQuery(geneQuery);
        subject.submitSearch();

        assertThat(driver.getCurrentUrl(), is(new URLBuilder("/gxa/genes/" + geneQuery).buildURL()));
    }

}