/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code, * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// TODO: we need to rename this test, as the content changed
public class SpecificAndCutoff05AndBrainAndBreastIT extends SeleniumFixture {

    private static final String HTTP_PARAMETERS = "cutoff=0.5"
            + "&queryFactorValues=brain&queryFactorValues=breast"
            + "&specific=true";
    private static final String HIGHER_RANKING_GENE = "GPD2";
    private static final String LOWER_RANKING_GENE = "PLEKHB2";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void higherAverageFpkmAcrossSelectedMinusAverageFpkmNonSelected() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneAverageFpkmOnSelectedFactors = subject.getAverageFpkm(29, "brain", "breast");
        double lowerRankingGeneAverageFpkmOnSelectedFactors = subject.getAverageFpkm(30, "brain", "breast");
        double higherRankingGeneAverageFpkmOnRemainingFactors = subject.getAverageFpkm(29, "adipose", "adrenal", "colon", "heart", "kidney", "leukocyte", "liver", "lung", "lymph node", "ovary", "prostate", "skeletal muscle", "testis", "thyroid");
        double lowerRankingGeneAverageFpkmOnRemainingFactors = subject.getAverageFpkm(30, "adipose", "adrenal", "colon", "heart", "kidney", "leukocyte", "liver", "lung", "lymph node", "ovary", "prostate", "skeletal muscle", "testis", "thyroid");

        //then
        assertThat(higherRankingGeneAverageFpkmOnSelectedFactors, is(10.5D));
        assertThat(higherRankingGeneAverageFpkmOnRemainingFactors, is(7.142857142857143D));
        //and
        assertThat(lowerRankingGeneAverageFpkmOnSelectedFactors, is(33.0D));
        assertThat(lowerRankingGeneAverageFpkmOnRemainingFactors, is(22.785714285714285D));

        //and average fpkm is greater for gene at row 29 than gene at row 30
        assertThat(higherRankingGeneAverageFpkmOnSelectedFactors / higherRankingGeneAverageFpkmOnRemainingFactors,
                is(greaterThan(lowerRankingGeneAverageFpkmOnSelectedFactors / lowerRankingGeneAverageFpkmOnRemainingFactors)));

        //gene at row 30 follows gene at row 29
        assertThat(subject.getGeneThatRanksAt(29), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(30), is(LOWER_RANKING_GENE));

    }

}
