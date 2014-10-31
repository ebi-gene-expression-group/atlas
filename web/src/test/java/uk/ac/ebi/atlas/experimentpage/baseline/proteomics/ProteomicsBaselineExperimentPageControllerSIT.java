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
    public static final int SPAST = 1;
    public static final int BRAIN = 0;
    public static final int ITGA3 = 1;
    public static final int RETINA = 19;
    protected HeatmapTablePage subject;

    @Test
    public void first5Genes() {
        subject = new HeatmapTablePage(driver, E_PROT_1, "displayLevels=true");
        subject.get();

        assertThat(subject.getFactorValueHeaders(), contains("B cell", "CD4-positive T...", "CD8-positive T...", "adrenal gland", "colon", "esophagus", "frontal cortex", "gallbladder", "heart", "kidney", "liver", "lung", "monocyte", "natural killer...", "ovary", "pancreas", "platelet", "prostate", "rectum", "retina", "spinal cord", "testis", "urinary bladder"));

        List<String> first5Genes = subject.getGeneNames().subList(0, 5);
        assertThat(first5Genes, contains("ITGA3","CD6", "LAS1L", "MRC2", "PSMC4"));

        assertThat(subject.getGeneProfile(ITGA3).get(RETINA), is("9.91 × 106"));

        assertThat(subject.getHeatmapLegendMinLevels(), contains("4.7 × 106"));
        assertThat(subject.getHeatmapLegendMaxLevels(), contains("9.91 × 106"));
    }

    @Test
    public void first5Genes_Fetus() {
        subject = new HeatmapTablePage(driver, E_PROT_1, "serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus&displayLevels=true");
        subject.get();

        //System.out.println(Joiner.on("\", \"").join(subject.getFactorValueHeaders()));
        assertThat(subject.getFactorValueHeaders(), contains("brain", "gut", "heart", "liver", "ovary", "placenta", "testis"));

        List<String> first5Genes = subject.getGeneNames().subList(0, 5);
        assertThat(first5Genes, contains("SPAST","CFH", "RNASET2", "HSPB6", "LIG3"));

        assertThat(subject.getGeneProfile(SPAST).get(BRAIN), is("9.79 × 106"));
    }

}