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

public class DefaultFilterFactorValuesAndCutoffButWithoutAnyGeneQueryIT extends Geod26284HeatmapTableTests {

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver, "geneQuery=");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"RP11-384J4.2", "TERF2", "GFI1", "SCN2A", "SLC10A1", "TRPM2", "GEMIN8P4", "RP11-368L12.1", "RP11-20I23.6"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive...", "HFDPC cell line", "HPC-PL cell line", "IMR-90", "hMSC-AT cell line"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"", "", "6", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"4", "", "1", "", "1"};
    }

    @Override
    protected String getGeneCount() {
        return "30";
    }
}