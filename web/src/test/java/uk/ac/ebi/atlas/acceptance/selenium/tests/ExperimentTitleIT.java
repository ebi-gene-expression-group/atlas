package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class ExperimentTitleIT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver);
        subject.get();
    }

    @Test
    public void testTitle() {
        assertThat(subject.getExperimentDescription(), startsWith("RNA-Seq of human individual tissues and mixture of 16 " +
                "tissues (Illumina Body Map)"));
    }


}
