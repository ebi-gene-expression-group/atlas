package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithTwoOrganismPartFilterIT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver,
                "organismParts=adipose&geneQuery=&organismParts=heart&_organismParts=1&cutoff=9");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(3));
        assertThat(selectedGenes, contains("AL031284.1", "AL162853.1", "CU463998.3"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("228", "","","","", "579","",""
                                                            ,"","","","","","","",""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("","","","","", "57","",""
                                                          ,"","","","","","","",""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("3"), is(true));
    }

}
