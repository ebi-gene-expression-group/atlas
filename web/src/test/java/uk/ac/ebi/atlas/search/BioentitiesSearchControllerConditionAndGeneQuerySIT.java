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

package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesCountWithHref;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionAndGeneQuerySIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "geneQuery=%22BAR%20domain%22&condition=%22wild%20type%22");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        //given
        subject.clickBaselineProfile();

        List<BaselineBioEntitiesCountWithHref> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(1));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Six tissues"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("-MTAB-599?queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=%22BAR+domain%22"));

    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains(
                "compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36",
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24"));
        assertThat(subject.getFoldChange(), hasItems("0.54", "0.01"));
    }

    @Test
    public void checkDifferentialProfilesCount() {
        assertThat(subject.diffExpressionResultCount(), is("2 search result(s) found"));
    }

}
