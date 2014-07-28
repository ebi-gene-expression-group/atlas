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

public class BioentitiesSearchControllerResultInMultiSpeciesExperimentSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "geneQuery=tex33");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        //given
        subject.clickBaselinePane();

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(4));

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("Vertebrate tissues - Homo sapiens"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(2).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=tex33&exactMatch=true&serializedFilterFactors=ORGANISM:Homo%20sapiens"));


        assertThat(baselineCounts.get(3).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(3).getExperimentName(), is("Vertebrate tissues - Mus musculus"));
        assertThat(baselineCounts.get(3).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(3).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=tex33&exactMatch=true&serializedFilterFactors=ORGANISM:Mus%20musculus"));
    }

}
