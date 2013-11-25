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
import static org.hamcrest.Matchers.is;

public class BioentitiesPageConditionQuery2TermsBaselineIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "condition=adipose+thymus");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        //given
        subject.clickBaselineProfile();

        List<BaselineBioEntitiesCountWithHref> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(2));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-513"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Illumina Body Map"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-MTAB-513?queryFactorType=ORGANISM_PART&queryFactorValues=adipose&geneQuery="));
        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Six tissues"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(1).getHref(), endsWith("E-MTAB-599?queryFactorType=ORGANISM_PART&queryFactorValues=thymus&geneQuery="));
    }

}
