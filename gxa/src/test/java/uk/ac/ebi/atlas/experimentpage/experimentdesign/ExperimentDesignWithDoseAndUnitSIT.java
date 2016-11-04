
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class ExperimentDesignWithDoseAndUnitSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MEXP-1276";

    private ExperimentDesignTablePage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void defaultExperimentDesignPage() {
        // TODO https://www.pivotaltracker.com/story/show/100371514
        // assertThat(subject.getFirstExperimentDesignTableLine(), hasItems("8 to 12 weeks", "dibenzazepine 10 micromoles per kilogram"));
        assertThat(subject.getFirstExperimentDesignTableLine(), hasItems("8 to 12 week", "dibenzazepine 10 micromole per kilogram"));
    }

}
