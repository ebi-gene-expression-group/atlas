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

package uk.ac.ebi.atlas.acceptance.selenium.tests.genepage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class GenesetBioEntityPageIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "REACT_1698";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, IDENTIFIER, "genesets", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void checkGeneCardTitle() {
        assertThat(subject.getBioEntityCardTitle(), is("REACT_1698 Homo sapiens Metabolism of nucleotides"));
    }

    @Test
    public void checkCardExpansion() {
        assertThat(subject.isInfoCardExpanded(), is(true));
        subject.clickInfoCard();
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void checkTableSize() {
        assertThat(subject.getPropertiesTableSize(), is(1));
    }

    @Test
    public void checkTableRows() {
        assertThat(subject.getPropertiesTableRow(0), hasItems("Reactome ID", "Metabolism of nucleotides"));
    }

    @Test
    public void checkLinksInTable() {
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://www.reactome.org/cgi-bin/eventbrowser_st_id?ST_ID=REACT_1698"));
    }
}