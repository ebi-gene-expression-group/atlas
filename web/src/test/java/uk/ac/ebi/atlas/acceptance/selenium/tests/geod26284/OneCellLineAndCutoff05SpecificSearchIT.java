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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod26284;

public class OneCellLineAndCutoff05SpecificSearchIT extends Geod26284HeatmapTableTests {

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver,
                "filterFactorValues=CELLULAR_COMPONENT%3Awhole+cell%2CMATERIAL_TYPE%3Atotal+RNA&queryFactorType=&heatmapMatrixSize=50&displayGeneDistribution=false&geneQuery=&queryFactorValues=CD34-positive+mobilized+cell+cell+line&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5");
        subject.get();
    }


    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"PTBP3", "THOC6", "AC111200.7", "THOC3", "TERF2", "GFI1", "RP11-20I23.6", "ARHGAP1", "7SK"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive...", "HFDPC cell line", "HPC-PL cell line", "IMR-90", "hMSC-AT cell line"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"92", "14", "12", "9", "7"};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"5", "3", "2", "2", "2"};
    }

    @Override
    protected String getGeneCount() {
        return "25";
    }
}