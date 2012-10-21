package uk.ac.ebi.atlas.acceptance.selenium;

import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePageOrganismOriented;

public class HeatmapTableOrganismOrientedWithCutoff540AndOrganismPartFilterIT extends SeleniumFixture {

    protected HeatmapTablePageOrganismOriented subject;

    public void getStartingPage() {
        subject = new HeatmapTablePageOrganismOriented(firefoxDriver, "heatmapMatrixSize=5&organismParts=heart&_organismParts=1&cutoff=20");
        subject.get();
    }


}
