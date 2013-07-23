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

package uk.ac.ebi.atlas.acceptance.selenium.tests.genequery;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

public class NoGenesAboveSpecifiedCutoffIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION, "serializedFilterFactors=&queryFactorType=&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=true&geneQuery=U3&_queryFactorValues=1&specific=true&_specific=on&cutoff=50");
        subject.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDisplayErrorMessage() {
        assertThat(subject.getPreferencesErrors(), startsWith("No genes found"));
    }

    @Test
    public void shouldNotDisplayHeatmapMessage() {
        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

}
