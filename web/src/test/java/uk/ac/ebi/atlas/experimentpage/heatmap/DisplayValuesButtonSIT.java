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

package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DisplayValuesButtonSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void displayLevelButtonShouldOnlyToggleHeatmapVisualizationStyle() throws InterruptedException {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(),is(true));
        assertThat(subject.areExpressionLevelsHidden(),is(true));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //Thread.sleep(2000);
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(), is(false));
        assertThat(subject.areExpressionLevelsHidden(),is(false));
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then again
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(),is(true));
        assertThat(subject.areExpressionLevelsHidden(),is(true));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
    }

    @Test
    public void displayLevelStateShouldBePreservedBetweenSearchRequests() {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        subject.clickDisplayLevelsButton();
        //and
        subject.setCutoff(11.1);
        //and
        subject = subject.submit();
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then the page has reloaded, to verify it let's check that gene count has a different value
        assertThat(geneCountBefore, not(equalTo(subject.getGeneCount())));
        //and
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));
        assertThat(subject.areGradientLevelsHidden(),is(false));
        assertThat(subject.areExpressionLevelsHidden(),is(false));

    }

}
