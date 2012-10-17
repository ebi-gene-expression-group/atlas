package uk.ac.ebi.atlas.acceptance.selenium;

import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePageOrganismOriented;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableOrganismOrientedWithCutoff540AndGeneFilterIT extends HeatmapTableWithCutoff540AndGeneFilterIT {

    public void getStartingPage() {
        subject = new HeatmapTablePageOrganismOriented(firefoxDriver, "geneIDsString=ENSG00000175084+ENSG00000210195&cutoff=540");
        subject.get();
    }


}
