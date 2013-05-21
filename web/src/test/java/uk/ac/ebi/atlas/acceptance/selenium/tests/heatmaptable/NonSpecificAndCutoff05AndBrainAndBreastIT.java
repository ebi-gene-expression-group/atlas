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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NonSpecificAndCutoff05AndBrainAndBreastIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=&cutoff=0.5"
            + "&queryFactorValues=brain&queryFactorValues=breast"
            + "&specific=false";

    private static final String HIGHER_RANKING_GENE = "TMSB10";
    private static final String LOWER_RANKING_GENE = "RTN4";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void averageFpkmAcrossSelectedFactors() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneAverageFpkmOnSelectedFactors = subject.getAverageExpressionLevel(1, "brain", "breast");
        double lowerRankingGeneAverageFpkmOnSelectedFactors = subject.getAverageExpressionLevel(2, "brain", "breast");

        //then
        assertThat(higherRankingGeneAverageFpkmOnSelectedFactors, is(1024.0));

        //then
        assertThat(lowerRankingGeneAverageFpkmOnSelectedFactors, is(535.5));

        //and
        assertThat(higherRankingGeneAverageFpkmOnSelectedFactors, is(greaterThan(lowerRankingGeneAverageFpkmOnSelectedFactors)));

        assertThat(subject.getGeneThatRanksAt(1), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(2), is(LOWER_RANKING_GENE));

    }

}
