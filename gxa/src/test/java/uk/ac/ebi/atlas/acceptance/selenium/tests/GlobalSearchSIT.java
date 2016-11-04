
package uk.ac.ebi.atlas.acceptance.selenium.tests;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HomePage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GlobalSearchSIT extends SinglePageSeleniumFixture {

    private HomePage subject;

    public void getStartingPage() {
        subject = new HomePage(driver);
        subject.get();
    }

    @Ignore //TODO need to fix it. Find a proper identifier for the pill box
    @Test
    public void globalSearchRedirectsToGeneQuerySearch(){
        String searchText = "ENSG00000179218";
        subject.setGlobalSearchText(searchText);
        subject.globalSearchSubmit();

        assertThat(driver.getCurrentUrl(), Matchers.is(new URLBuilder("/gxa/genes/" + searchText).buildURL()));
    }
}
