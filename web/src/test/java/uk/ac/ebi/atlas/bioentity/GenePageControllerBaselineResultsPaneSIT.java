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

package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenePageControllerBaselineResultsPaneSIT extends SinglePageSeleniumFixture {

    @Override
    protected void getStartingPage() {
    }

    @Test
    public void whenNoBaselineResultsAndDiffResultsThenTheDifferentialPaneIsOpen() {
        BioEntityPage subject = new BioEntityPage(driver, "ENSMUSG00000070683", "genes");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("1 result"));
        assertThat(subject.isDifferentialPaneExpanded(), is(true));
    }

    @Test
    public void whenBaselineResultsThenBaselinePaneIsOpen() {
        BioEntityPage subject = new BioEntityPage(driver, "ENSMUSG00000064356", "genes");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.isBaselinePaneExpanded(), is(true));
    }

    @Test
    public void whenNoBaselineOrDiffResultsThenInfoPaneIsOpen() {
        BioEntityPage subject = new BioEntityPage(driver, "ENSMUSG00000027401", "genes");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.isInfoCardExpanded(), is(true));
    }

    @Test
    public void whenBaselineResultsThenIfonPaneIsOpenAndNoDiffResults() {
        BioEntityPage subject = new BioEntityPage(driver, "ENSMUSG00000042800", "genes"); // mus musculus
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.isBaselinePaneExpanded(), is(true));
    }

}
