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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod30352;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithTranscriptBreakdownPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TranscriptBreakdownPlotIT extends SeleniumFixture {

    private static final String E_E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    protected HeatmapTableWithTranscriptBreakdownPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_E_GEOD_30352_ACCESSION,"serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=ENSG00000058668&exactMatch=true&_exactMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Test
    public void verifyButtonClickKidneyProfile() {
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(0, 3);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for ATP2B4 (9 transcripts) in kidney"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000484746","ENST00000341360","ENST00000357681","Others"));
    }

    @Test
    public void verifyButtonClickPrefrontalCortexProfile() {
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(0, 5);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for ATP2B4 (9 transcripts) in prefrontal cortex"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000484746","ENST00000341360","ENST00000367218","Others"));
    }

}
