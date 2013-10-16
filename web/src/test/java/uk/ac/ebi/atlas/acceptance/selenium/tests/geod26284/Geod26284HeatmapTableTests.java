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

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public abstract class Geod26284HeatmapTableTests extends SinglePageSeleniumFixture {

    protected HeatmapTablePage subject;

    protected abstract String getQueryFactorLabel();

    protected abstract String[] getTop9Genes();

    protected abstract String[] getHeatmapHeader();

    protected abstract String[] getFirstGeneProfile();

    protected abstract String[] getNinthGeneProfile();

    protected abstract String getGeneCount();

    @Test
    public void testQueryFactorLabel() {
        MatcherAssert.assertThat(subject.getQueryFactorLabel(), is(getQueryFactorLabel()));
    }

    @Test
    public void verifyTop9SelectedGenes() {
        //given selected filterFactorValues

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getGeneNames().subList(0, 9);

        //then
        assertThat(selectedGenes, contains(getTop9Genes()));
    }

    @Test
    public void heatmapHeadersShouldBeDependentOnSelectedFilterFactorValues() {
        //given selected filterFactorValues

        //then
        assertThat(subject.getFactorValueHeaders(), contains(getHeatmapHeader()));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();

        assertThat(subject.getFirstGeneProfile(), contains(getFirstGeneProfile()));
    }

    @Test
    public void verifyNinthGeneProfile() {
        subject.clickDisplayLevelsButton();

        assertThat(subject.getGeneProfile(9), contains(getNinthGeneProfile()));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains(getGeneCount()), is(true));
    }

}