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

package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpecificAndOrganismPartSIT extends SinglePageSeleniumFixture {

    private static final String E_MTAB_599_ACCESSION = "E-MTAB-599";
    private static final String HTTP_PARAMETERS = "exactMatch=false&specific=true&geneQuery=Cyp2d10+Tdo2+Serpina1d+Apoh+Albumin&queryFactorValues=liver&_queryFactorValues=1&cutoff=0.5";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, E_MTAB_599_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(8));
        assertThat(selectedGenes, contains("Afm", "Tdo2", "Gc", "Apoh",  "Serpina1d", "Cyp2d10", "5830473C10Rik", "Ecm1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "", "270", "", "1", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("25", "4", "112", "94", "23", "11"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("8"), is(true));
    }

}
