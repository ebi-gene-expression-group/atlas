package uk.ac.ebi.atlas.acceptance.selenium.tests.anatomogram;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.AnatomogramTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.notNull;

public class AnatomogramPresentTest extends SeleniumFixture {

    AnatomogramTablePage subject;

    public void getStartingPage() {
        subject = new AnatomogramTablePage(driver) {
            @Override
            protected String getPageURI() {
                return "/gxa/experiments/E-MTAB-513";
            }
        };
        subject.get();
    }

    @Test
    public void testAnotomogramIsThere() {
        assertThat(subject.getAnatomogram(), is(notNullValue()));
    }
}
