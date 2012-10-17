package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.SearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class EmptySearchFormIT extends SeleniumFixture {

    private SearchFormPage subject;

    public void getStartingPage() {
        subject = new SearchFormPage(firefoxDriver);
        subject.get();
    }

    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneIDsString(), is(""));
        assertThat(subject.getCutoff(), is("0.0"));
        assertThat(subject.getOrganismParts().size(), is(0));
    }


}
