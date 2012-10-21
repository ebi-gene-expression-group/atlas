package uk.ac.ebi.atlas.acceptance.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import uk.ac.ebi.atlas.acceptance.selenium.pages.SearchFormPage;

public class SearchFormIT extends SeleniumFixture {

    private SearchFormPage subject;

    public void getStartingPage() {
        subject = new SearchFormPage(firefoxDriver, "geneIDsString=ENSG00000175084+ENSG00000210195&cutoff=540&organismParts=heart&organismParts=liver");
        subject.get();
    }

    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneIDsString(), is("ENSG00000175084 ENSG00000210195"));
        assertThat(subject.getCutoff(), is("540.0"));
        assertThat(subject.getOrganismParts(), contains("heart", "liver"));
    }


}
