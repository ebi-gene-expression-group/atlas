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
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NonSpecificAndCutoff05AndBrainAndBreastSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=&cutoff=0.5"
            + "&queryFactorValues=brain&queryFactorValues=breast"
            + "&specific=false";


    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(50));
        assertThat(selectedGenes.get(0), is("TMSB10"));
        assertThat(selectedGenes.get(1), is("RTN4"));
        assertThat(selectedGenes.get(2), is("AC005702.4"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("1728", "2286", "1206", "704", "1344", "1633", "326", "793", "2380", "156", "1825", "1265", "807", "129", "1040", "425"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("188"), is(true));
    }

}
