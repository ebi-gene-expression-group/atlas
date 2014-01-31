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

package uk.ac.ebi.atlas.acceptance.selenium.tests.bioentitiespage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesCountWithHref;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesPageGeneQueryDifferentSpeciesIT extends SinglePageSeleniumFixture {

    public static final String GENE_QUERY_PARAM = "ENSG00000161547%20ENSMUSG00000030105";
    public static final String GLOBAL_SEARCH_TERM = "ENSG00000161547+OR+ENSMUSG00000030105";


    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "geneQuery=" + GENE_QUERY_PARAM);
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        subject.clickBaselineProfile();

        List<BaselineBioEntitiesCountWithHref> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(2));

        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Six tissues"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(0).getCount(), is(-1));

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(1).getCount(), is(-1));
    }


    @Test
    public void checkDifferentialDisplaysGeneAndOrganismColumnWithValuesForEachSpecies() {
        subject.clickDifferentialDisplayLevelsButton();
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("Arl8b", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "Arl8b"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains("Mus musculus", "Homo sapiens", "Homo sapiens", "Homo sapiens", "Homo sapiens", "Mus musculus"));
    }


    @Test
    public void globalSearchTermIsIdentifiersSeparatedByOR() {
        assertThat(subject.getGlobalSearchTerm(), is(GLOBAL_SEARCH_TERM));
    }

    @Test
    public void globalSearchWidgetShouldHaveResults(){
        subject.clickShowMoreDataWidget();
        assertThat(subject.getGlobalSearchAllResultsTotal(), is(greaterThan(0)));
    }

}
