package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePageOrganismOriented;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableOrganismOrientedWithCutoff540AndOrganismPartFilterIT extends SeleniumFixture {

    protected HeatmapTablePageOrganismOriented subject;

    public void getStartingPage() {
        subject = new HeatmapTablePageOrganismOriented(firefoxDriver, "heatmapMatrixSize=5&organismParts=heart&_organismParts=1&cutoff=20");
        subject.get();
    }


}
