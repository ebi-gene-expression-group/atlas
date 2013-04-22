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

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithTranscriptBreakdownPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TranscriptBreakdownPlotIT extends SeleniumFixture {

    private static final String E_GEOD_26284_ACCESSION = "E-GEOD-26284";
    protected HeatmapTableWithTranscriptBreakdownPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_GEOD_26284_ACCESSION);
        subject.get();
    }

    @Test
    public void verifyButtonClickFirstProfile() {
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(9, 3);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for PTBP3 (7 transcripts) in IMR-90"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000374257", "ENST00000343327", "ENST00000374255", "Others"));
    }


}
