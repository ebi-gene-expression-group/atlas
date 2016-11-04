
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class DefaultFilterByMenuSIT extends SingleDriverSeleniumFixture {

    public static final String TOTAL_RNA = "total RNA";
    public static final String RNA_TYPE = "RNA";
    public static final String WHOLE_CELL = "whole cell";
    public static final String CELLULAR_COMPONENT = "Cellular component";
    public static final String CELL_LINE = "Cell line";
    public static final String LONG_NON_POLYA_RNA = "long non-polyA RNA";
    public static final String LONG_POLYA_RNA = "long polyA RNA";
    public static final String SK_N_SH_RA = "SK-N-SH_RA";
    public static final String A_549 = "A549";
    public static final String H_MSC_AT_CELL_ELLIPSIS = "hMSC-AT cell…";
    public static final String H_MSC_AT_CELL_LINE = "hMSC-AT cell line";

    protected static Geod26284HeatmapTablePage subject;


    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(), "geneQuery=");
        subject.get();
    }

    @Test
    public void verifyFilterByMenuTopLabel() {
        assertThat(subject.getFilterByMenuTopText(), is("Change filters"));
    }

    @Test
    public void verifyFilterByMenuFirstLabels() {
        assertThat(subject.getFilterByMenuText(0), is(CELL_LINE));
        assertThat(subject.getFilterByMenuText(1), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(2), is(RNA_TYPE));
    }

    @Test
    public void verifyFilterByMenuFirstFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{0, 0}), is(A_549));
        assertThat(subject.getFilterByMenuText(new int[]{0, 22}), is(H_MSC_AT_CELL_LINE));
    }

    @Test
    public void verifyFilterByMenuFirstSecondLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{1, 0}), is("chromatin"));
        assertThat(subject.getFilterByMenuText(new int[]{1, 1}), is("cytosol"));
        assertThat(subject.getFilterByMenuText(new int[]{1, 2}), is("nucleolus"));
        assertThat(subject.getFilterByMenuText(new int[]{1, 3}), is("nucleoplasm"));
        assertThat(subject.getFilterByMenuText(new int[]{1, 4}), is("nucleus"));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5}), is(WHOLE_CELL));
    }

    @Test
    public void verifyFilterByMenuFirstThirdLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 0}), is(LONG_NON_POLYA_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{2, 1}), is(LONG_POLYA_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{2, 2}), is(TOTAL_RNA));
    }

    @Test
    public void verifyFilterByMenuFirstFirstFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{0, 0, 0}), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{0, 0, 1}), is(RNA_TYPE));
    }

    @Test
    public void verifyFilterByMenuFirstSecondFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{1, 0, 0}), is(CELL_LINE));
        assertThat(subject.getFilterByMenuText(new int[]{1, 0, 1}), is(RNA_TYPE));
    }

    @Test
    public void verifyFilterByMenuFirstThirdFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 0, 0}), is(CELL_LINE));
        assertThat(subject.getFilterByMenuText(new int[]{2, 0, 1}), is(CELLULAR_COMPONENT));
    }

    @Test
    public void verifyDefault() {
        verifyDefaultTop9SelectedGenes();
        verifyDefaultHeatmapHeaders();
    }

    public void verifyDefaultTop9SelectedGenes() {
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getGeneNames().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains(
                "MPO", "PROM1", "MATK", "ITGA2B", "CD79B", "WAS", "PRKCH", //expressed on 1 FactorValue
                "CCDC88C", //expressed on two FactorValues
                "ITGAL" //expressed on three FactorValues
        ));
    }


    public void verifyDefaultHeatmapHeaders() {
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains("CD34-positive…", "HFDPC cell line", "HPC-PL cell…", "IMR-90", H_MSC_AT_CELL_ELLIPSIS));
    }

    @Test
    public void verifyRNAtypeTotalRNACellularComponentWholeCellClick() {
        assertThat(subject.getFilterByMenuText(2), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{2, 2}), is(TOTAL_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{2, 2, 1}), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{2, 2, 1, 3}), is(WHOLE_CELL));

        // this should correspond to the experiment settings
        subject.clickFilterByMenuElement(new int[]{2, 2, 1, 3});
        verifyDefaultTop9SelectedGenes();
        verifyDefaultHeatmapHeaders();
    }

    @Test
    public void verifyCellularComponentWholeCellRNAtypeTotalRNAClick() {
        assertThat(subject.getFilterByMenuText(1), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5}), is(WHOLE_CELL));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5, 1}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5, 1, 2}), is(TOTAL_RNA));

        // this should correspond to the experiment settings
        subject.clickFilterByMenuElement(new int[]{1, 5, 1, 2});
        verifyDefaultTop9SelectedGenes();
        verifyDefaultHeatmapHeaders();
    }

    @Test
    public void verifyCellularComponentWholeCellRNAtypeLongNonPolyARNAClick() {
        assertThat(subject.getFilterByMenuText(1), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5}), is(WHOLE_CELL));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5, 1}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{1, 5, 1, 0}), is(LONG_NON_POLYA_RNA));

        subject.clickFilterByMenuElement(new int[]{1, 5, 1, 0});

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains(A_549, "AG445", "BJ", "CD14-positive…", "CD20-positive…", "GM12878", "H1-hESC", "HMEC cell line", "HSMM cell line", "HUVEC cell line", "HeLa-S3", "HepG2", "K562", "MCF-7", "NHEK cell line", "NHLF cell line", SK_N_SH_RA));

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getGeneNames().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains("TMEM176A", "MARCO", "TFAP2B", "GABRA1", "INSRR", "SOX8", "CD6", "MPO", "LGALS14"));
    }

    @Test
    public void verifyAlwaysUsingLastMenuElementClick() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 2, 1, 3}), is(WHOLE_CELL));

        // always the last index
        // click total RNA - whole cell
        subject.clickFilterByMenuElement(new int[]{2, 2, 1, 3});

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains("CD34-positive…", "HFDPC cell line", "HPC-PL cell…", "IMR-90", "hMSC-AT cell…"));

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getGeneNames().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains("MPO", "PROM1", "MATK", "ITGA2B", "CD79B", "WAS", "PRKCH", "CCDC88C", "ITGAL"));

    }

}