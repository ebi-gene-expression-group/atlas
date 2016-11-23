
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq.geod22351;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class RnaSeqHeatmapTableWithDifferentRegulationsButDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_GEOD_22351_ACCESSION = "E-GEOD-22351";
    protected HeatmapTablePage subject;

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-GEOD-22351.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-GEOD-22351/all-analytics.tsv"));
        assertThat(subject.getDownloadRawCountsLink(), endsWith("/gxa/experiments/E-GEOD-22351/raw-counts.tsv"));
    }

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Comparison"));

        assertThat(subject.getFactorValueHeaders().size(), is(1));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("genotype"));
    }

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 40"));

        assertThat(subject.getGeneNames().size(), is(40));
        assertThat(subject.getGeneNames().subList(0, 4), contains("Gm13886", "Cst7", "Itgax", "Ccl4"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("4.1"));

        assertThat(subject.getGeneProfile(2).size(), is(1));
        assertThat(subject.getGeneProfile(2).get(0), is("3.8"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("1.2"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 9"));

        assertThat(subject.getGeneNames().size(), is(9));
        assertThat(subject.getGeneNames().subList(0, 3), contains("Gm15512", "Mybpc3", "Pla2g3"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("-2.7"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("-1"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 49"));

        assertThat(subject.getGeneNames().size(), is(49));
        assertThat(subject.getGeneNames().subList(0, 4), contains("Gm13886", "Cst7", "Itgax", "Ccl4"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("4.1"));
        assertThat(subject.getGeneProfile(2).size(), is(1));
        assertThat(subject.getGeneProfile(2).get(0), is("3.8"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("-1"));
    }

    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 0, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL), is("Adjusted p-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 1, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL), is("0.002"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL), is("4.1"));
    }

}
