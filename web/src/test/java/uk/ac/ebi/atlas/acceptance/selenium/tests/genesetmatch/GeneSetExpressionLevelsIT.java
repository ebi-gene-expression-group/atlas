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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class GeneSetExpressionLevelsIT extends SeleniumFixture {

    private static final String E_E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    protected HeatmapTableWithSearchFormPage subject;

    @Before
    public void initSubject() {
    }

    @Test
    public void shouldHaveAGeneHeader() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22&_exactMatch=on&geneSetMatch=true" +
                "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getGeneColumnHeader(), is("Gene set"));
    }

    @Test
    public void shouldHaveGeneSetCheckBoxSelected() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22&_exactMatch=on&geneSetMatch=true" +
                "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.isGeneSetMatch(), is(true));
    }

    @Test
    public void shouldFindExpressionLevelsForTermInDoubleQuotes() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22&_exactMatch=on&geneSetMatch=true" +
                "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedProfiles(), contains("\"Alpha-1-acid glycoprotein\""));
        assertThat(subject.getFirstGeneProfile(), contains("", "", "0.5", "0.5", "6451", "", "", "3" ));

    }

    @Test
    public void shouldFindExpressionLevelsForReactomeTerm() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&" +
                "displayLevels=true&displayGeneDistribution=true&geneQuery=react_1619&" +
                "_exactMatch=on&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedProfiles(), contains("react_1619"));
        assertThat(subject.getFirstGeneProfile(), contains("4", "4", "12", "22", "17", "4", "6", "14"));

    }

    @Test
    public void shouldFindExpressionLevelsForMultipleTerms() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22+react_1619&_exactMatch=on" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        //then
        assertThat(subject.getSelectedProfiles(), contains("\"Alpha-1-acid glycoprotein\"", "react_1619"));
        assertThat(subject.getFirstGeneProfile(), contains("", "", "0.5", "0.5", "6451", "", "", "3" ));
        assertThat(subject.getGeneProfile(2), contains("4", "4", "12", "22", "17", "4", "6", "14"));

    }

    @Test
    public void shouldDisplayNumberOfGeneSetsFound(){
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22+react_1619&_exactMatch=on" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        String geneCountMessage = subject.getGeneCount();
        //then
        assertThat(geneCountMessage, is("Showing 2 of 2 gene sets found:"));
    }

}
