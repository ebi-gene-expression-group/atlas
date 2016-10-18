
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class BioentitiesSearchControllerConditionQuery2TermsDifferentialSIT extends SeleniumFixture {


    @Test
    public void nrpe1OrCdk8() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.diffExpressionResultCount(), is("Showing 25 results"));
    }

    @Test
    public void globalSearchTermIsIdentifiersSeparatedByOR() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.getGlobalSearchTerm(), is("nrpe1+OR+cdk8"));
    }

    @Test
    public void globalSearchBaselineDoNotHaveResults(){
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
    }

    @Test
    public void searchFullPhraseAndNotIndividualWords() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=5+weeks");
        subject.get();


        // should not be E-GEOD-21860 (which contains the word "weeks" but not "5 weeks"

        assertThat(subject.getDiffHeatmapContrastSummaryTooltipTableCell(1, 2), is(not("12 weeks")));
        assertThat(subject.getDiffHeatmapContrastSummaryTooltipTableCell(2, 2), is("5 week"));
    }
}
