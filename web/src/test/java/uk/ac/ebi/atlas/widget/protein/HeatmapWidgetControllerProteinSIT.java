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

package uk.ac.ebi.atlas.widget.protein;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.StringContains.containsString;

public class HeatmapWidgetControllerProteinSIT extends SeleniumFixture {

    private static final String PROTEIN_ACCESSION = "Q8TCE9";

    private HeatmapTableWidgetPage heatmapTablePage;

    @Before
    public void initPage(){
        heatmapTablePage = new HeatmapTableWidgetPage(driver, "geneQuery=" + PROTEIN_ACCESSION);
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
        assertThat(experimentDescription, startsWith("RNA-seq of coding RNA from tissue samples of 122 human individuals representing 32 different tissues"));
    }

    @Test
    public void testGeneName() {
        String firstGeneName = heatmapTablePage.getGeneNamesJsp().get(0);
        assertThat(firstGeneName, is("LGALS14"));
    }

    @Test
    public void testLinkToExperiment() {
        String experimentDescriptionLink = heatmapTablePage.getExperimentDescriptionLink();
        assertThat(experimentDescriptionLink, endsWith("/experiments/E-MTAB-2836?geneQuery=Q8TCE9&serializedFilterFactors="));
    }


}
