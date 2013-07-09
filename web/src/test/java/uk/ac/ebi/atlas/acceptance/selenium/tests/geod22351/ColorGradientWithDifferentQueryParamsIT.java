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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod22351;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ColorGradientWithDifferentQueryParamsIT extends SeleniumFixture {

    private static final String E_GEOD_22351_ACCESSION = "E-GEOD-22351";
    protected HeatmapTablePage subject;

    @Test
    public void verifyUpGradientLevelsAreShowingAndCorrect() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        assertThat(subject.areExpressionLevelsHidden(), is(false));
        assertThat(subject.areGradientLevelsHidden(), is(false));

        List<String> gradientMinLabels = subject.getDiffGradientMinLabels();
        List<String> gradientMaxLabels = subject.getDiffGradientMaxLabels();

        assertThat(gradientMinLabels, contains("0.041"));
        assertThat(gradientMaxLabels, contains("0"));
    }

    @Test
    public void verifyUpGradientStartAndEndColor() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        List<String> startColor = subject.getDiffGradientStartColor();
        List<String> endColor = subject.getDiffGradientEndColor();

        assertThat(startColor, contains("255, 175, 175"));
        assertThat(endColor, contains("255, 0, 0"));
    }

    @Test
    public void verifyDownGradientLevelsAreShowingAndCorrect() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.areExpressionLevelsHidden(), is(false));
        assertThat(subject.areGradientLevelsHidden(), is(false));

        List<String> gradientMinLabels = subject.getDiffGradientMinLabels();
        List<String> gradientMaxLabels = subject.getDiffGradientMaxLabels();

        assertThat(gradientMinLabels, contains("0.041"));
        assertThat(gradientMaxLabels, contains("6.61 × 10-5"));
    }

    @Test
    public void verifyDownGradientStartAndEndColor() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();

        List<String> startColor = subject.getDiffGradientStartColor();
        List<String> endColor = subject.getDiffGradientEndColor();

        assertThat(startColor, contains("192, 192, 192"));
        assertThat(endColor, contains("0, 0, 255"));
    }

    @Test
    public void verifyUpDownGradientLevelsAreShowingAndCorrect() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.areExpressionLevelsHidden(), is(false));
        assertThat(subject.areGradientLevelsHidden(), is(false));

        List<String> gradientMinLabels = subject.getDiffGradientMinLabels();
        List<String> gradientMaxLabels = subject.getDiffGradientMaxLabels();

        assertThat(gradientMinLabels, contains("0.041", "0.041"));
        assertThat(gradientMaxLabels, contains("6.61 × 10-5", "0"));
    }

    @Test
    public void verifyUpDownGradientStartAndEndColor() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        List<String> startColor = subject.getDiffGradientStartColor();
        List<String> endColor = subject.getDiffGradientEndColor();

        assertThat(startColor, contains("192, 192, 192", "255, 175, 175"));
        assertThat(endColor, contains("0, 0, 255", "255, 0, 0"));
    }

    @Test
    public void verifyGradientOnlyShowsWhenValuesPresent() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=false&regulation=UP_DOWN&cutoff=1.0E-5");
        subject.get();

        List<String> gradientMinLabels = subject.getDiffGradientMinLabels();
        List<String> gradientMaxLabels = subject.getDiffGradientMaxLabels();

        assertThat(gradientMinLabels, contains("7.19 × 10-6"));
        assertThat(gradientMaxLabels, contains("0"));

        List<String> startColor = subject.getDiffGradientStartColor();
        List<String> endColor = subject.getDiffGradientEndColor();

        assertThat(startColor, contains("255, 175, 175"));
        assertThat(endColor, contains("255, 0, 0"));

    }
}