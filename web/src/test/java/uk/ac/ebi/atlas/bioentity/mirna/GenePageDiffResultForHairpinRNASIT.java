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

import java.net.UnknownHostException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageDiffResultForHairpinRNASIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "hsa-miR-486-5p";

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

        assertThat(subject.getBioEntityCardTitle(), is("MIMAT0002177 Homo sapiens"));
        assertThat(subject.getPropertiesTableSize(), is(4));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Biotype", "miRNA"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("miRBase", "MIMAT0002177"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("Sequence", "UCCUGUACUGAGCUGCCCCGAG"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("Design Element", "A_25_P00010644, A_25_P00010645"));
    }

    @Test
    public void checkSelectedProfiles() throws UnknownHostException {
        subject.clickDifferentialPane();
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains("disease state: 'sepsis' vs 'control'"));
    }


}
