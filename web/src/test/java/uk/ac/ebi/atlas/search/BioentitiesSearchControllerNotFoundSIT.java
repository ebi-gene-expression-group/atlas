package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BioentitiesSearchControllerNotFoundSIT extends SeleniumFixture {

    @Test
    public void nonExistentGene() {
        BioEntityPage subject = BioEntitiesPage.search(driver, "geneQuery=FOOBAR");
        subject.get();
        assertThat(subject.getSearchResultsHeader(), is("Expression Atlas results for FOOBAR"));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.getBaselinePaneContents(), is(""));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
    }

}
