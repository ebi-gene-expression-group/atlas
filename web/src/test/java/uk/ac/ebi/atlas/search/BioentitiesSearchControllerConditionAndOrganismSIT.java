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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchControllerConditionAndOrganismSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "organism=Homo+sapiens&condition=adult");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(3));

        BaselineBioEntitiesSearchResult result1 = selectResult(baselineCounts, "E-PROT-1");
        assertThat(result1.getExperimentName(), is("Human Proteome Map - adult"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=B%20cell,CD4-positive%20T%20cell,CD8-positive%20T%20cell,adrenal%20gland,colon,esophagus,frontal%20cortex,gallbladder,heart,kidney,liver,lung,monocyte,natural%20killer%20cell,ovary,pancreas,platelet,prostate,rectum,retina,spinal%20cord,testis,urinary%20bladder&geneQuery=&exactMatch=true&serializedFilterFactors=DEVELOPMENTAL_STAGE:adult"));

        BaselineBioEntitiesSearchResult result2 = selectResult(baselineCounts, "E-MTAB-1733");
        assertThat(result2.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=&exactMatch=true"));
    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDifferentialPane();
        assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 9859 results"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains(Collections.nCopies(50, "Homo sapiens").toArray()));

    }

}
