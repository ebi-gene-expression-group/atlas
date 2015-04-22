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

package uk.ac.ebi.atlas.experimentpage.baseline.proteomics;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProteomicsBaselineExperimentPageControllerSIT extends SeleniumFixture {

    private static final String E_PROT_1 = "E-PROT-1";
    private static final String E_PROT_1_ACCESS_KEY = "&accessKey=37f172c7-c40f-470e-afcb-8199ed77c9d8";
    public static final int SPAST = 1;
    public static final int BRAIN = 0;
    public static final int RHBDD2 = 1;
    public static final int FRONTAL_CORTEX = 6;
    protected HeatmapTablePage subject;

    @Test
    public void first5Genes() {
        // Remove accessKey when experiment goes public
        subject = new HeatmapTablePage(driver, E_PROT_1, "displayLevels=true" + E_PROT_1_ACCESS_KEY);
        subject.get();

        assertThat(subject.getFactorValueHeaders(), contains("B cell", "CD4-positive T…", "CD8-positive T…", "adrenal gland", "colon", "esophagus", "frontal cortex", "gallbladder", "heart", "kidney", "liver", "lung", "monocyte", "natural killer…", "ovary", "pancreas", "platelet", "prostate", "rectum", "retina", "spinal cord", "testis", "urinary bladder"));

        List<String> first5Genes = subject.getGeneNames().subList(0, 5);
        assertThat(first5Genes, contains("RHBDD2","TMEM132A", "GABRA3", "CXorf56", "BTN3A1"));

        assertThat(subject.getGeneProfile(RHBDD2).get(FRONTAL_CORTEX), is("9.5 × 106"));

        assertThat(subject.getHeatmapLegendMinLevels(), contains("4.3 × 10-6"));
        assertThat(subject.getHeatmapLegendMaxLevels(), contains("9.8 × 107"));
    }

    @Test
    public void first5Genes_Fetus() {
        subject = new HeatmapTablePage(driver, E_PROT_1, "serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus&displayLevels=true" + E_PROT_1_ACCESS_KEY);
        subject.get();

        //System.out.println(Joiner.on("\", \"").join(subject.getFactorValueHeaders()));
        assertThat(subject.getFactorValueHeaders(), contains("brain", "gut", "heart", "liver", "ovary", "placenta", "testis"));

        List<String> first5Genes = subject.getGeneNames().subList(0, 5);
        assertThat(first5Genes, contains("SPAST","MARK4", "IYD", "ANKIB1", "COPZ2"));

        assertThat(subject.getGeneProfile(SPAST).get(BRAIN), is("9.8 × 106"));
    }

}