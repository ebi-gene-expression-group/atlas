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

package uk.ac.ebi.atlas.acceptance.selenium.tests.mirna;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HairpinRNADiffGeneBioEntityPageIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "hsa-mir-767";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=2");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isDifferentialProfileExpanded(), is(true));
    }

    @Test
    public void checkMatureRNADiffProfilesArePresentForHairpinRNA() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains("disease state: 'sepsis' vs 'control'",
                "disease state: 'sepsis' vs 'control'",
                "disease state: 'sepsis' vs 'control'"));
        assertThat(subject.getPValues(), hasItems("0.001", "0.002", "0.031"));
    }

}
