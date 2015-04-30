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
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchControllerConditionQueryFemaleSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "condition=female");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(17));
        BaselineBioEntitiesSearchResult result = selectResult(baselineCounts, "E-MTAB-513");
        assertThat(result.getExperimentName(), is("Illumina Body Map"));
        assertThat(result.getSpecies(), is("Homo sapiens"));
        assertThat(result.getHref(), endsWith("E-MTAB-513?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=adipose,brain,breast,colon,kidney,lymph%20node,ovary,thyroid&geneQuery=&exactMatch=true"));

    }

    @Test
    public void checkDifferentialProfilesCount() {
        subject.clickDifferentialPane();
        // TODO Change back when E-GEOD-21860 and E-GEOD-43049 are back
        // assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 1233 results"));
        assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 1115 results"));
    }

}
