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

package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class OneCellLineAndCutoff05SpecificSearchSIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(),
                "serializedFilterFactors=CELLULAR_COMPONENT%3Awhole+cell%2CRNA%3Atotal+RNA&queryFactorType=&heatmapMatrixSize=50&displayGeneDistribution=false&geneQuery=&queryFactorValues=CD34-positive+mobilized+cell+cell+line&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"MPO", "PROM1", "DEF6", "MATK", "ITGA2B", "CD74", "CD79B", "WAS", "PRKCH"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive…", "HFDPC cell line", "HPC-PL cell…", "IMR-90", "hMSC-AT cell…"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"144", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"14", "", "", "", ""};
    }

    @Override
    protected String getGeneCount() {
        return "152";
    }
}