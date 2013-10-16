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
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class BioentitiesPageGeneQuery2SpeciesIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "geneQuery=ENSG00000161547%20ENSMUSG00000030105");
        subject.get();
    }

    @Test
    public void checkBaselineContainsFirstGene() {
        subject.clickBaselineProfile();
        assertThat(subject.getGeneNames(), contains("SRSF2"));
    }


    @Test
    public void checkDifferentialDisplaysGeneAndOrganismColumnWithValuesForEachSpecies() {
        subject.clickDifferentialDisplayLevelsButton();
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("Arl8b", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "Arl8b"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains("Mus musculus", "Homo sapiens", "Homo sapiens", "Homo sapiens", "Homo sapiens", "Mus musculus"));
    }

}
