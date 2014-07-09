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

package uk.ac.ebi.atlas.bioentity.mirna;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageBaselineResultForHairpinRNASIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "hsa-miR-636";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for " + GENE_IDENTIFIER));
    }

    @Test
    public void infoCard() {
        assertThat(subject.isInfoCardExpanded(), is(false));
        subject.clickInfoCard(true);
        assertThat(subject.isInfoCardExpanded(), is(true));

        assertThat(subject.getBioEntityCardTitle(), is("MIMAT0003306 Homo sapiens"));
        assertThat(subject.getPropertiesTableSize(), is(4));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Biotype", "miRNA"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("miRBase", "MIMAT0003306"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("Sequence", "UGUGCUUGCUCGUCCCGCCCGCA"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("Design Element", "A_25_P00010661, A_25_P00010662, A_25_P00010663, A_25_P00010664, A_25_P00012828, A_25_P00012829, A_25_P00012830, A_25_P00012831"));
    }

    @Test
    public void baselineProfilePaneIsOpenAndContainsGenes() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
        assertThat(subject.getGeneNames(), contains("MFSD11", "SRSF2", "MIR636"));
    }

}
