package uk.ac.ebi.atlas.widget;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithTranscriptBreakdownPage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
public class HeatmapWidgetControllerTranscriptSIT extends SeleniumFixture {

    protected HeatmapTableWidgetPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWidgetPage(driver, "geneQuery=ENSG00000228278&propertyType=bioentity_identifier");
        subject.get();
    }

    @Test
    public void shouldHaveAGeneHeader() {
        assertThat(subject.getGeneColumnHeader(), is("Gene"));

    }

    @Test
    public void verifyButtonClickFirstProfile() {

        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(0, 3);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for ORM2 in appendix\n(1 out of 2 transcripts are expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000431067"));
    }

}
