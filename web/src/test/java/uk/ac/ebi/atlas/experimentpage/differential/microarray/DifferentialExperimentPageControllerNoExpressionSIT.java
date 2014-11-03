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

package uk.ac.ebi.atlas.experimentpage.differential.microarray;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class DifferentialExperimentPageControllerNoExpressionSIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    @Test
    public void microarrayDifferential_noExpressionsFoundMessageWhenGeneHasNoExpression() {
        subject = new HeatmapTablePage(driver, "E-TABM-713", "geneQuery=miR-17-92");
        subject.get();

        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

    @Test
    public void rnaSeqDifferential_noExpressionsFoundMessageWhenGeneHasNoExpression() {
        subject = new HeatmapTablePage(driver, "E-GEOD-21860", "geneQuery=Brca1");
        subject.get();

        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

}
