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

import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class OneCellLineAndCutoff05NonSpecificSearchIT extends Geod26284HeatmapTableTests {

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver,
                "filterFactorValues=CELLULAR_COMPONENT%3Awhole+cell%C2RNA%3Atotal+RNA&queryFactorType=&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=&queryFactorValues=CD34-positive+mobilized+cell+cell+line&_queryFactorValues=1&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"TMSB10", "PTBP3", "Y_RNA", "ARHGAP1", "THOC6", "THOC3", "BMI1", "AC111200.7", "CALU"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive...", "HFDPC cell line", "HPC-PL cell line", "IMR-90", "hMSC-AT cell line"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"123", "288", "695", "1023", "490"};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"6", "209", "223", "311", "123"};
    }

    @Override
    protected String getGeneCount() {
        return "25";
    }

}