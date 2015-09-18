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

package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod3307;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class ExperimentPageHeaderSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-3307";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void testTitle() {
        assertThat(subject.getExperimentDescription(), startsWith("Transcription profiling by array of 12 human muscle diseases"));
    }

    @Test
    public void testOrganisms() {
        assertThat(subject.getExperimentOrganisms(), is("Organism(s): Homo sapiens"));
    }

    @Test
    public void testArrayDesigns() {
        assertThat(subject.getExperimentArrayDesigns(), is("Array Design(s): Affymetrix GeneChip Human Genome HG-U133A [HG-U133A] Affymetrix GeneChip Human Genome HG-U133B [HG-U133B]"));
    }

}
