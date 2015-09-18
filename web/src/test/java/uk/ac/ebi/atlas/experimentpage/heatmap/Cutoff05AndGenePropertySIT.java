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

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class Cutoff05AndGenePropertySIT extends SinglePageSeleniumFixture {

    private final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
//
    }

    @Test
    public void verifyResultOnSinglePropertyQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 263"));
    }

    @Test
    public void verifyResultOnMultiplePropertyQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 263"));
    }

    @Test
    public void verifyResultOnMultiplePropertyAndOrganismPartQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&queryFactorValues=skeletal+muscle&queryFactorValues=thyroid&_queryFactorValues=2&cutoff=0.5");
        subject.get();

        assertThat(subject.getGeneCount(), containsString("of 15"));
        subject.clickDisplayLevelsButton();
        List<String> geneProfile = subject.getFirstGeneProfile();
        assertThat(geneProfile, hasItems("0.6","","","1","","","0.6","0.9","1","","","1","","8","8","","3" ));
    }

}
