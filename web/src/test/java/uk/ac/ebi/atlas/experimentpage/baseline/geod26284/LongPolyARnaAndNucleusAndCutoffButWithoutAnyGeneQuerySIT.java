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

public class LongPolyARnaAndNucleusAndCutoffButWithoutAnyGeneQuerySIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(), "geneQuery=&serializedFilterFactors=RNA%3Along+polyA+RNA%2CCELLULAR_COMPONENT%3Anucleus&queryFactorType=CELL_LINE&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"FGR", "ATP1A2", "DCN", "CALCR", "NR1H4",
                "C8B", "TFAP2B", "CYP3A43", "TTC22"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"A549", "GM12878", "H1-hESC", "HUVEC cell line", "HeLa-S3", "HepG2", "IMR-90", "K562", "MCF-7", "NHEK cell line", "SK-N-SH"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"", "281", "", "", "", "", "", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"", "", "", "", "", "", "", "", "", "3", ""};
    }

    @Override
    protected String getGeneCount() {
        return "467";
    }
}