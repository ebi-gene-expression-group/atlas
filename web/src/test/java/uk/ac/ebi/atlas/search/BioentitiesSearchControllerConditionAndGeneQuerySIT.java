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

public class BioentitiesSearchControllerConditionAndGeneQuerySIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=%22apoptotic+process%22&condition=%22wild+type%22");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        //given
        subject.clickBaselinePane();

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(1));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Six tissues"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("-MTAB-599?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=%22apoptotic+process%22&exactMatch=true"));

    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.diffExpressionResultCount(), is("Showing 16 results"));
        assertThat(subject.getContrastColumn(), hasItem(
                "compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36"));
        assertThat(subject.getFoldChange(), hasItems("3.46", "1"));

        //System.out.println("\"" + Joiner.on("\", \"").join(subject.getDiffHeatmapTableGeneColumn()) + "\"");

        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("Lcn2", "Tnfrsf12a", "Trp53inp1", "Apip", "Tao", "Gja1", "Srgn", "Eif2ak3", "Mknk2", "Kras", "wgn", "Arf6", "Tgfbr1", "Sgms1", "Mef2a", "Dab2"));

    }

}
