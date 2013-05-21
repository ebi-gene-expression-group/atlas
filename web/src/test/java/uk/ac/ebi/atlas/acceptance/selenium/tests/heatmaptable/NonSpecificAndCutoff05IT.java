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

package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NonSpecificAndCutoff05IT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=&cutoff=0.5"
            + "&specific=false";

    private static final String HIGHER_RANKING_GENE = "ARPC5";
    private static final String LOWER_RANKING_GENE = "RAB13";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void averageFpkmShouldDetermineRanking() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneAverageFpkm = subject.getAverageFpkm(10);
        double lowerRankingGeneAverageFpkm = subject.getAverageFpkm(11);

        //then
        assertThat(higherRankingGeneAverageFpkm, is(89.4375));
        assertThat(higherRankingGeneAverageFpkm, is(greaterThan(lowerRankingGeneAverageFpkm)));

        //and max fpkm is greater for gene at row 11 than gene at row 10
        assertThat(subject.getMaxExpressionLevel(11), is(lessThan(subject.getMaxExpressionLevel(10))));

        //gene at row 11 follows gene at row 10
        assertThat(subject.getGeneThatRanksAt(10), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(11), is(LOWER_RANKING_GENE));

    }

}
