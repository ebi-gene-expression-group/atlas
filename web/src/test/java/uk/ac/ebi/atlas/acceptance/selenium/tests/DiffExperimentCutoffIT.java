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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class DiffExperimentCutoffIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    @Test
    public void diffCutoffLessThan0DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-1066", "geneQuery=&cutoff=-1");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("Please select a False Discovery Rate cutoff that is between 0 and 1 (inclusive)"));
    }

    @Test
     public void diffCutoffGraterThan1DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-1066", "geneQuery=&cutoff=11");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("Please select a False Discovery Rate cutoff that is between 0 and 1 (inclusive)"));
    }

    @Test
    public void baselineCutoffLessThan0DisplayErrorMessage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-513", "geneQuery=&cutoff=-1");
        subject.get();
        assertThat(subject.getPreferencesErrors(), startsWith("The expression level cutoff must be greater than 0"));
    }

}

