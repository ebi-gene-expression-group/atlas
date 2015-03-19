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

package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpecificAndCutoff05SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "cutoff=0.5"
            + "&specific=true";

    private static final String HIGHER_RANKING_GENE = "ACTL7A";
    private static final String LOWER_RANKING_GENE = "TEX33";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void specificityShouldDetermineRanking() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneFpkm = subject.getMaxExpressionLevel(1);
        double lowerRankingGeneFpkm = subject.getMaxExpressionLevel(2);

        //then
        assertThat(higherRankingGeneFpkm, is(69D));
        assertThat(higherRankingGeneFpkm, is(greaterThan(lowerRankingGeneFpkm)));

        //gene at row 11 follows gene at row 10
        assertThat(subject.getGeneThatRanksAt(1), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(2), is(LOWER_RANKING_GENE));

    }

}
