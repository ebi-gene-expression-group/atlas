package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class SearchFormIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "geneQuery=ENSG00000175084+ENSG00000210195&cutoff=540&queryFactorValues=heart&queryFactorValues=liver");
        subject.get();
    }

    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneQuery(), is("ENSG00000175084 ENSG00000210195"));
        assertThat(subject.getCutoff(), is("540"));
        assertThat(subject.getQueryFactorValues(), contains("heart", "liver"));
    }


}
