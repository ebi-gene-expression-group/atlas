
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public abstract class Geod26284HeatmapTableTests extends SingleDriverSeleniumFixture {

    protected static HeatmapTablePage subject;

    protected abstract String getQueryFactorLabel();

    protected abstract String[] getTop9Genes();

    protected abstract String[] getHeatmapHeader();

    protected abstract String[] getFirstGeneProfile();

    protected abstract String[] getNinthGeneProfile();

    protected abstract String getGeneCount();

    @Test
    public void testQueryFactorLabel() {
        MatcherAssert.assertThat(subject.getQueryFactorLabel(), is(getQueryFactorLabel()));
    }

    @Test
    public void verifyTop9SelectedGenes() {
        //given selected filterFactorValues

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getGeneNames().subList(0, 9);

        //then
        assertThat(selectedGenes, contains(getTop9Genes()));
    }

    @Test
    public void heatmapHeadersShouldBeDependentOnSelectedFilterFactorValues() {
        //given selected filterFactorValues

        //then
        assertThat(subject.getFactorValueHeaders(), contains(getHeatmapHeader()));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains(getFirstGeneProfile()));
    }

    @Test
    public void verifyNinthGeneProfile() {
        assertThat(subject.getGeneProfile(9), contains(getNinthGeneProfile()));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains(getGeneCount()), is(true));
    }

}