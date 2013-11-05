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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineExperimentsPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GlobalSearchIT  extends SinglePageSeleniumFixture {

    private BaselineExperimentsPage subject;

    public void getStartingPage() {
        subject = new BaselineExperimentsPage(driver);
        subject.get();
    }

    @Test
    public void shouldRedirectToProteinPage(){
        subject.setGlobalSearchText("ENSP00000355434");
        BioEntityPage bioEntityPage = subject.globalSearchSubmit();
        assertThat(bioEntityPage.getBioEntityCardTitle(), is("Q8N349 Homo sapiens olfactory receptor, family 2, subfamily L, member 13"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Ensembl Protein"));
    }

    @Test
    public void shouldRedirectToGenePage(){
        subject.setGlobalSearchText("ENSG00000179218");
        BioEntityPage bioEntityPage = subject.globalSearchSubmit();
        assertThat(bioEntityPage.getBioEntityCardTitle(), is("CALR Homo sapiens calreticulin"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Synonyms"));
    }

    @Test
    public void shouldRedirectToGenesetPage(){
        subject.setGlobalSearchText("REACT_1619");
        BioEntityPage bioEntityPage = subject.globalSearchSubmit();
        assertThat(bioEntityPage.getBioEntityCardTitle(), is("REACT_1619 Homo sapiens Death Receptor Signalling"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Reactome"));
    }

    @Test
    public void shouldRedirectToResourceNotFound(){
        subject.setGlobalSearchText("XYZMC2");
        BioEntityPage bioEntityPage = subject.globalSearchSubmit();
        assertThat(bioEntityPage.isResourceNotFound(), is(true));
    }
}
