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

package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.StringContains.containsString;

public class ProteinWidgetIT extends SeleniumFixture {

    private static final String Q9GIL2_ACCESSION = "Q9GIL2";

    private HeatmapTableWidgetPage heatmapTablePage;

    @Before
    public void initPage(){
        heatmapTablePage = new HeatmapTableWidgetPage(driver, "geneQuery=" + Q9GIL2_ACCESSION);
        heatmapTablePage.get();
    }

    @Test
    public void testAnatomogramIsThere() {
        assertThat(heatmapTablePage.getAnatomogram().isDisplayed(), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoResultFoundForNotValidAccession() {
        heatmapTablePage = new HeatmapTableWidgetPage(driver, "geneQuery=123321xyzzyx");
        heatmapTablePage.get();
        heatmapTablePage.getAnatomogram().isDisplayed();
    }

    @Test
    public void verifyResultOnSinglePropertyQuery() {
        Assert.assertThat(heatmapTablePage.getGeneCount(), containsString("of 1"));
    }

    @Test
    public void testTitle() {
        String experimentDescription = heatmapTablePage.getExperimentDescription();
        assertThat(experimentDescription, startsWith("RNA-Seq of human individual tissues and mixture of 16 " +
                "tissues (Illumina Body Map)"));
    }

    @Test
    public void testGeneName() {
        String firstGeneName = heatmapTablePage.getSelectedGenes().get(0);
        assertThat(firstGeneName, is("HLA-B"));
    }

}
