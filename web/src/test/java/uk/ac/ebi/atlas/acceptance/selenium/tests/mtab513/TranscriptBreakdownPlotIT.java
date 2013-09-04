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

package uk.ac.ebi.atlas.acceptance.selenium.tests.mtab513;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithTranscriptBreakdownPage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TranscriptBreakdownPlotIT extends SeleniumFixture {

    private static final String E_MTAB_513_ACCESSION = "E-MTAB-513";
    protected HeatmapTableWithTranscriptBreakdownPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_MTAB_513_ACCESSION);
        subject.get();
    }

    @Test
    public void verifyButtonClickOnProfileExpression() {
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(11, 14);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for USP26 in testis\n(2 out of 4 transcripts are expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000417459", "ENST00000370832"));
    }

}
