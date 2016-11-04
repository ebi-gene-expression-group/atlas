
package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ExperimentCutoffSIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    @Test
    public void diffCutoffLessThan0DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-1066", "geneQuery=&cutoff=-1");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("Please select a False Discovery Rate cutoff that is between 0 and 1 (inclusive)"));
    }

    @Test
     public void diffCutoffGraterThan1DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-1066", "geneQuery=&cutoff=11");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("Please select a False Discovery Rate cutoff that is between 0 and 1 (inclusive)"));
    }

    @Test
    public void baselineCutoffLessThan0DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-513", "geneQuery=&cutoff=-1");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("The expression level cutoff must be greater than 0"));
    }

}

