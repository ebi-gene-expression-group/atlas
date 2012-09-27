package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff20000AndTranscripts8IT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(firefoxDriver, "?cutoff=20000&heatmapMatrixSize=8");
        subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(9));
        //and
        assertThat(subject.getOrganismParts(), contains("adipose", "adrenal", "breast", "colon", "heart", "kidney", "liver", "skeletal muscle", "white blood cells"));
    }

    @Test
    public void verifySelectedTranscripts() {
        assertThat(subject.getSelectedTranscripts(), contains("ENST00000486939", "ENST00000362079", "ENST00000390237", "ENST00000559220", "ENST00000361390", "ENST00000361789"));
    }

    @Test
    public void verifyFirstTranscriptProfile() {
        assertThat(subject.getFirstTranscriptProfile(), contains("", "", "", "", "", "", "36872.6684888937", "", ""));
    }

    @Test
    public void verifyLastTranscriptProfile() {
        assertThat(subject.getLastTranscriptProfile(), contains("24566.6097632967", "", "20171.8025101343", "30178.2443854876", "", "21488.0555149394", "", "32640.5435071735", ""));
    }

}
