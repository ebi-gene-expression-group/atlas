package uk.ac.ebi.atlas.acceptance.selenium.tests.bioentitiespage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DiffBioentitiesPageIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "brain");
        subject.get();
        subject.useDiffHeatmapTable();
    }


    @Test
    public void checkSelectedProfiles() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains(
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24",
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23"));
        assertThat(subject.getPValues(), hasItems("0.02", "0.04"));
    }

    @Test
    public void checkContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 0), is("Property"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 1), is("Test value"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 2), is("Reference value"));
    }

    @Test
    public void checkContrastSummaryTooltipTableFirstRow() {
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 0), is("genotype"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 1), is("p107 -/-"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 2), is("wild type"));
    }


    @Test
    public void checkContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContrastSummaryTooltipExperimentDescription(0), is("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates"));
        assertThat(subject.getContrastSummaryTooltipContrastDescription(0), is("genotype:'p107 -/-' vs 'wild type' on A-AFFY-24"));
    }

    @Test
    public void checkFirstLinkIsCorrect() {
        assertThat(subject.getLinkInDiffTableRow(1), endsWith("E-GEOD-3779?geneQuery=ENSMUSG00000028385&queryFactorValues=g2_g1"));
    }

}
