/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.acceptance.selenium.tests;


import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BarChartAxisForDifferentOrganismPartsSelectedIT extends SinglePageSeleniumFixture {

    private HeatmapTableWithSearchFormAndBarChartPage subject;

    @Override
    protected void getStartingPage() {
        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver);
        subject.get();
    }

    @Before
    public void displayBarChart() {
        subject.clickDisplayChartButton();
    }

    @Test
    public void checkBarChartAxisForAllOrganismParts() {
        assertThat(subject.getXAxisValue(0), is("0"));
        assertThat(subject.getXAxisValue(1), is("0.2"));
        assertThat(subject.getMaxXAxisValue(), is("40"));

        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("100"));
        assertThat(subject.getMaxYAxisValue(), is("400"));
    }

    @Test
    public void checkBarChartAxisForSkeletalMuscle() {
        subject.selectQueryFactorValue("skeletal muscle");
        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("50"));
        assertThat(subject.getMaxYAxisValue(), is("200"));
    }

}