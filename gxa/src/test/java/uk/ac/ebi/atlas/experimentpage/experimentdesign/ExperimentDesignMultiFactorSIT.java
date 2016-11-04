
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ExperimentDesignMultiFactorSIT extends SinglePageSeleniumFixture {

    private ExperimentDesignTablePage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignTablePage(driver, "E-TABM-51");
        subject.get();
    }

    @Test
    public void sortedByAllFactors() {
        assertThat(subject.getExperimentDesignTableHeader().size(), is(12));
        assertThat(subject.getExperimentDesignTableInfo(), is("Showing 1 to 18 of 18 entries (filtered from 126 total entries)"));
        assertThat(subject.getFirstExperimentDesignTableLine(), hasItem("284b"));
        assertThat(subject.getLastExperimentDesignTableLine(), hasItem("357-2"));
    }

}
