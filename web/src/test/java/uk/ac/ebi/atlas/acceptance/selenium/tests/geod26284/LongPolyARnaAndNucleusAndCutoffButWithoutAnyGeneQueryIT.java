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

public class LongPolyARnaAndNucleusAndCutoffButWithoutAnyGeneQueryIT extends Geod26284HeatmapTableTests {

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver, "geneQuery=&serializedFilterFactors=MATERIAL_TYPE%3Along+polya+rna%2CCELLULAR_COMPONENT%3Anucleus&queryFactorType=CELL_LINE");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"RP11-439L8.3", "RP11-192H23.4", "RP11-736N17.8", "RP11-727M10.1", "RGS7BP",
                "RP5-961K14.1", "SLC10A1", "RP11-14C10.1", "RP11-273B20.1"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"a549", "gm12878", "h1-hesc", "hela-s3", "hepg2", "huvec cell line", "imr-90", "k562",
                "mcf-7", "nhek cell line", "sk-n-sh"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"", "19", "", "", "", "", "", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"", "", "", "25", "", "", "", "", "0.9", "", ""};
    }

    @Override
    protected String getGeneCount() {
        return "50";
    }
}