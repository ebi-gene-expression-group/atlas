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
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class BioentitiesSearchControllerConditionQuery2TermsDifferentialSIT extends SeleniumFixture {


    @Test
    public void nrpe1OrCdk8() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.diffExpressionResultCount(), is("Showing 25 results"));
    }

    @Test
    public void globalSearchTermIsIdentifiersSeparatedByOR() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.getGlobalSearchTerm(), is("nrpe1+OR+cdk8"));
    }

    @Test
    public void globalSearchBaselineDoNotHaveResults(){
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=nrpe1%09cdk8");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
    }

    @Test
    public void searchFullPhraseAndNotIndividualWords() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=5+weeks");
        subject.get();


        // should not be E-GEOD-21860 (which contains the word "weeks" but not "5 weeks"

        assertThat(subject.getDiffHeatmapContrastSummaryTooltipTableCell(1, 2), is(not("12 weeks")));
        assertThat(subject.getDiffHeatmapContrastSummaryTooltipTableCell(2, 2), is("5 week"));
    }
}
