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

package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BarChartAxisForDifferentOrganismPartsSelectedIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormAndBarChartPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver);
        subject.get();
    }

    @Before
    public void displayBarChart(){
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
        subject.selectOrganismPart("skeletal muscle");
        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("1"));
        assertThat(subject.getMaxYAxisValue(), is("4"));
    }

}