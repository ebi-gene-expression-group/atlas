package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmptySearchFormIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(firefoxDriver);
        subject.get();
    }

    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneQuery(), is(""));
        assertThat(subject.getCutoff(), is("0.5"));
        assertThat(subject.getOrganismParts().size(), is(0));
    }


}
