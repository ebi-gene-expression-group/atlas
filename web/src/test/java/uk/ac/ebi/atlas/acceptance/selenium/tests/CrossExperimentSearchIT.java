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
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CrossExperimentSearchIT extends SeleniumFixture {

    public static final String CROSS_EXPERIMENT_SEARCH_BASE_URL = "/gxa/experiments/all";

    private String buildCrossExperimentSearchURL(String queryString) {
        return new URLBuilder(CROSS_EXPERIMENT_SEARCH_BASE_URL).buildURL("queryString=" + queryString);
    }

    @Test
    public void shouldRedirectToProteinPage(){
        String queryString = "ENSP00000355434";
        driver.get(buildCrossExperimentSearchURL(queryString));

        BioEntityPage bioEntityPage = new BioEntityPage(driver);

        assertThat(bioEntityPage.getBioEntityCardTitle(), is("Q8N349 Homo sapiens olfactory receptor, family 2, subfamily L, member 13"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Ensembl Protein"));
    }

    @Test
    public void shouldRedirectToGenePage(){
        String queryString = "ENSG00000179218";
        driver.get(buildCrossExperimentSearchURL(queryString));
        BioEntityPage bioEntityPage = new BioEntityPage(driver);

        assertThat(bioEntityPage.getBioEntityCardTitle(), is("CALR Homo sapiens calreticulin"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Synonyms"));
    }

    @Test
    public void shouldRedirectToGenesetPage(){
        String queryString = "REACT_1619";
        driver.get(buildCrossExperimentSearchURL(queryString));
        BioEntityPage bioEntityPage = new BioEntityPage(driver);

        assertThat(bioEntityPage.getBioEntityCardTitle(), is("REACT_1619 Homo sapiens Death Receptor Signalling"));
        bioEntityPage.clickInfoCard(true);
        assertThat(bioEntityPage.getPropertiesTableRow(0).get(0), is("Reactome"));
    }

    @Test
    public void shouldRedirectToResourceNotFound(){
        String queryString = "XYZMC2";
        driver.get(buildCrossExperimentSearchURL(queryString));
        BioEntityPage bioEntityPage = new BioEntityPage(driver);

        assertThat(bioEntityPage.isResourceNotFound(), is(true));
    }
}
