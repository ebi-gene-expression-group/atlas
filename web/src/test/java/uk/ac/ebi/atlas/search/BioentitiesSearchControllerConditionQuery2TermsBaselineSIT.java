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
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionQuery2TermsBaselineSIT extends SinglePageSeleniumFixture {

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

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(4));

        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-GEOD-26284"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("ENCODE cell lines - total RNA, whole cell"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=hMSC-AT%20cell%20line&geneQuery=&serializedFilterFactors=RNA:total%20RNA,CELLULAR_COMPONENT:whole%20cell"));


        assertThat(baselineCounts.get(3).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(3).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(3).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(3).getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=adipose%20tissue&geneQuery="));
    }

}
