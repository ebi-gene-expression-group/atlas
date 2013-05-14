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

package uk.ac.ebi.atlas.acceptance.selenium.tests.genesetmatch;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class GeneSetExpressionLevelsIT extends SeleniumFixture {

    private static final String E_E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    protected HeatmapTableWithSearchFormPage subject;

    @Before
    public void initSubject() {
    }

    @Test
    public void shouldFindExpressionLevelsForCytoplasm() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART" +
                "&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=cytoplasm" +
                "&exactMatch=true&_exactMatch=on&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedGenes(), contains("cytoplasm"));
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("28", "24", "17", "29", "27", "18", "22", "62"));

    }

    @Test
    public void shouldFindExpressionLevelsForNucleus() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART" +
                "&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=nucleus" +
                "&exactMatch=true&_exactMatch=on&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedGenes(), contains("nucleus"));
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("39", "28", "16", "22", "11", "21", "19", "33"));

    }

    /*
    @Test
    public void shouldFindExpressionLevelsForMultipleTerms() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART" +
                "&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=nucleus+cytoplasm" +
                "&exactMatch=true&_exactMatch=on&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedGenes(), contains("nucleus", "cytoplasm"));
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("39", "28", "16", "22", "11", "21", "19", "33"));
        assertThat(subject.getGeneProfile(1), contains("28", "24", "17", "29", "27", "18", "22", "62"));

    }
*/

}
