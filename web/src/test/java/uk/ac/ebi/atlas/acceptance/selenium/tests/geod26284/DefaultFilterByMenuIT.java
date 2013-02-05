/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod26284;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class DefaultFilterByMenuIT extends SeleniumFixture {

    public static final String TOTAL_RNA = "total rna";
    public static final String RNA_TYPE = "RNA type";
    public static final String WHOLE_CELL = "whole cell";
    public static final String CELLULAR_COMPONENT = "cellular component";
    public static final String CELL_LINE = "cell line";
    public static final String LONG_NON_POLYA_RNA = "long non-polya rna";
    public static final String LONG_POLYA_RNA = "long polya rna";
    public static final String SK_N_SH_RA = "sk-n-sh_ra";
    public static final String A_549 = "a549";

    protected Geod26284HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver, "geneQuery=");
        subject.get();
    }

    @Test
    public void verifyFilterByMenuTopLabel() {
        assertThat(subject.getFilterByMenuTopText(), is("Change filters"));
    }

    @Test
    public void verifyFilterByMenuFirstLabels() {
        assertThat(subject.getFilterByMenuText(0), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(1), is(CELL_LINE));
        assertThat(subject.getFilterByMenuText(2), is(CELLULAR_COMPONENT));
    }

    @Test
    public void verifyFilterByMenuFirstFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{0, 0}), is(LONG_NON_POLYA_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{0, 1}), is(LONG_POLYA_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{0, 2}), is(TOTAL_RNA));
    }

    @Test
    public void verifyFilterByMenuFirstSecondLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{1, 0}), is(A_549));
        assertThat(subject.getFilterByMenuText(new int[]{1, 22}), is(SK_N_SH_RA));
    }

    @Test
    public void verifyFilterByMenuFirstThirdLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 0}), is("chromatin"));
        assertThat(subject.getFilterByMenuText(new int[]{2, 1}), is("cytosol"));
        assertThat(subject.getFilterByMenuText(new int[]{2, 2}), is("nucleolus"));
        assertThat(subject.getFilterByMenuText(new int[]{2, 3}), is("nucleoplasm"));
        assertThat(subject.getFilterByMenuText(new int[]{2, 4}), is("nucleus"));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5}), is(WHOLE_CELL));
    }

    @Test
    public void verifyFilterByMenuFirstFirstFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{0, 0, 0}), is(CELL_LINE));
        assertThat(subject.getFilterByMenuText(new int[]{0, 0, 1}), is(CELLULAR_COMPONENT));
    }

    @Test
    public void verifyFilterByMenuFirstSecondFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{1, 0, 0}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{1, 0, 1}), is(CELLULAR_COMPONENT));
    }

    @Test
    public void verifyFilterByMenuFirstThirdFirstLabels() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 0, 0}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{2, 0, 1}), is(CELL_LINE));
    }

    @Test
    public void verifyDefaultTop9SelectedGenes() {
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getSelectedGenes().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains(
                "RP11-384J4.2", "TERF2", "GFI1", "SCN2A", "SLC10A1", "TRPM2", "GEMIN8P4", //expressed on 1 FactorValue
                "RP11-368L12.1", //expressed on two FactorValues
                "RP11-20I23.6" //expressed on three FactorValues
        ));
    }

    @Test
    public void verifyDefaultHeatmapHeaders() {
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains("cd34-positive...", "hfdpc cell line", "hmsc-at cell line", "hpc-pl cell line", "imr-90"));
    }

    @Test
    public void verifyRNAtypeTotalRNACellularComponentWholeCellClick() {
        assertThat(subject.getFilterByMenuText(0), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{0, 2}), is(TOTAL_RNA));
        assertThat(subject.getFilterByMenuText(new int[]{0, 2, 1}), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{0, 2, 1, 3}), is(WHOLE_CELL));

        // this should correspond to the experiment settings
        subject.clickFilterByMenuElement(new int[]{0, 2, 1, 3});
        verifyDefaultTop9SelectedGenes();
        verifyDefaultHeatmapHeaders();
    }

    @Test
    public void verifyCellularComponentWholeCellRNAtypeTotalRNAClick() {
        assertThat(subject.getFilterByMenuText(2), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5}), is(WHOLE_CELL));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5, 0}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5, 0, 2}), is(TOTAL_RNA));

        // this should correspond to the experiment settings
        subject.clickFilterByMenuElement(new int[]{2, 5, 0, 2});
        verifyDefaultTop9SelectedGenes();
        verifyDefaultHeatmapHeaders();
    }

    @Test
    public void verifyCellularComponentWholeCellRNAtypeLongNonPolyARNAClick() {
        assertThat(subject.getFilterByMenuText(2), is(CELLULAR_COMPONENT));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5}), is(WHOLE_CELL));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5, 0}), is(RNA_TYPE));
        assertThat(subject.getFilterByMenuText(new int[]{2, 5, 0, 0}), is(LONG_NON_POLYA_RNA));

        subject.clickFilterByMenuElement(new int[]{2, 5, 0, 0});

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains(A_549, "ag445", "bj", "cd14-positive...", "cd20-positive b...", "gm12878", "h1-hesc", "hela-s3", "hepg2", "hmec cell line", "hsmm cell line", "huvec cell line", "k562", "mcf-7", "nhek cell line", "nhlf cell line", SK_N_SH_RA));

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getSelectedGenes().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains(
                "RP11-439L8.3", "U4", "RP11-90H3.1", "RTDR1", "RGS7BP", "RP11-736N17.8", "RP11-727M10.1", "RP3-406C18.2", "SCN2A"
        ));
    }

    @Test
    public void verifyAlwaysUsingLastMenuElementClick() {
        assertThat(subject.getFilterByMenuText(new int[]{2, 5, 1, 22}), is(SK_N_SH_RA));

        // always the last index
        subject.clickFilterByMenuElement(new int[]{2, 5, 1, 22});

        //then
        Assert.assertThat(subject.getFactorValueHeaders(), contains("long non-polya...", LONG_POLYA_RNA));

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getSelectedGenes().subList(0, 9);

        //then
        Assert.assertThat(selectedGenes, contains(
                "AC011293.1", "Y_RNA", "7SK", "PCDHGB8P", "RP5-961K14.1", "GEMIN8P4", "RPS6P20", "RTDR1", "RP11-698F20.3"
        ));

    }

}