package uk.ac.ebi.atlas.widget.multiexperiment;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeatmapWidgetControllerSIT extends SeleniumFixture {

    private HeatmapTableWidgetPage subject;

    public void get(String geneQuery){
        subject = HeatmapTableWidgetPage.create(driver, "geneQuery=" + geneQuery + "&propertyType=bioentity_identifier");
        subject.get();
    }

    @Test
    public void noExpressionsForGeneWithNoExpressions() {
        get("ENSG00000207555");
        assertThat(subject.getHeatmapMessage(), is("No expressions found"));
    }

    @Test
    public void noExpressionsForLowDataGene() {
        get("ENSG00000207556");
        assertThat(subject.getHeatmapMessage(), is("No expressions found"));
    }

}
