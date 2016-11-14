package uk.ac.ebi.atlas.experimentpage.genequery;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class LowerCaseGeneQuerySIT extends SeleniumFixture {


    protected HeatmapTableWithSearchFormPage subject;

    @Test
    public void hasResultForGeneQueryContainingLowerCaseLetters() {
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MEXP-1099", "geneQuery=FBgn0031030");
        //when
        subject.get();

        //then
        assertThat(subject.getGeneNames(), contains("Tao"));

    }
}
