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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SpecificSelectedContrastIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Contrast"));

        assertThat(subject.getFactorValueHeaders().size(), is(3));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("nrpe1"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 34"));

        assertThat(subject.getGeneNames().size(), is(34));
        assertThat(subject.getGeneNames().subList(0, 3), contains("T5N23_130", "AT3G29644", "GRXS4"));

        //This one drops down because of fold change algorithm
        assertThat(subject.getGeneNames().get(26), is("DML1"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(2), is("<10-10"));

        assertThat(subject.getGeneProfile(2).size(), is(3));
        assertThat(subject.getGeneProfile(2).get(0), is("0.014"));
        assertThat(subject.getGeneProfile(2).get(2), is("6.64" + " \u00D7 " + "10-9"));
    }

}
