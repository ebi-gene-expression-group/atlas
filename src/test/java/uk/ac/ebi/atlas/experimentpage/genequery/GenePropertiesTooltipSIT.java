
package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class GenePropertiesTooltipSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=%22actin-related+protein%22+bile+acid+protein&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    //This will fail with PhantomJS
    @Test
    @Ignore //TODO: we need to fix this test for matching the correct id:geneQuery in selenium
    public void matchingGeneQueryTermsShouldBeHighlighted() throws InterruptedException {
        //given
        String tooltipContent = subject.getGenePropertyTooltipContent(0);

        //then
        assertThat(tooltipContent, containsString("ACTL7A"));
        List<String> highlightedTerms = subject.getGenePropertyTooltipHighlightedTerms(0);
        assertThat(highlightedTerms, hasItems("protein", "Actin-related protein"));

    }

}
