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
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class NonSpecificNoContrastSelectedIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-GEOD-38400.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-GEOD-38400/all-analytics.tsv"));
        assertThat(subject.getDownloadRawCountsLink(), endsWith("/gxa/experiments/E-GEOD-38400/raw-counts.tsv"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&specific=false");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 51"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("AT1G33840", "F14M2.2", "T5N23_130"));
        assertThat(subject.getSelectedProfiles().subList(13, 14), contains("DML1"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getGeneProfile(14).size(), is(3));
        assertThat(subject.getGeneProfile(14).get(0), is("3.22" + " \u00D7 " + "10-4"));
        assertThat(subject.getGeneProfile(14).get(2), is("7.29" + " \u00D7 " + "10-6"));
    }

}
