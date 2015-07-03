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
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.hasResult;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchControllerConditionQuery2TermsBaselineSIT extends SeleniumFixture {

    @Test
    public void adiposeOrThymus() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=adipose%09thymus");
        subject.get();
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(5));

        BaselineBioEntitiesSearchResult result1 = selectResult(baselineCounts, "E-GEOD-26284");
        assertThat(result1.getExperimentName(), is("ENCODE cell lines - total RNA, whole cell"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=hMSC-AT%20cell%20line&geneQuery=&exactMatch=true&serializedFilterFactors=RNA:total%20RNA,CELLULAR_COMPONENT:whole%20cell"));

        BaselineBioEntitiesSearchResult result2 = selectResult(baselineCounts, "E-MTAB-1733");
        assertThat(result2.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=adipose%20tissue&geneQuery=&exactMatch=true"));
    }

    @Test
    public void searchFullPhraseAndNotIndividualWords() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=whole+post-emergence+inflorescence");
        subject.get();
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        //should not contain experiments with the world "whole", eg: E-GEOD-26284 (Homo sapiens - ENCODE cell lines - long non-polyA RNA, whole cell)
        assertThat(hasResult(baselineCounts, "E-GEOD-26284"), is(false));
        assertThat(baselineCounts, hasSize(1));

        BaselineBioEntitiesSearchResult result1 = selectResult(baselineCounts, "E-MTAB-2039");
        assertThat(result1.getExperimentName(), is("Tissues - 9 Davidson"));
        assertThat(result1.getSpecies(), is("Oryza sativa Japonica Group"));
        assertThat(result1.getHref(), endsWith("E-MTAB-2039?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=emerging%20inflorescence&geneQuery=&exactMatch=true"));
    }

}
