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

package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerGeneInProteomicsExperimentBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSG00000005884";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void baselineWidgetGenes() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 3 of 3 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        List<String> factorValueHeaders = subject.getFactorValueHeaders();
        assertThat(factorValueHeaders, contains("B cell", "CD4-positive T...", "CD8-positive T...", "adipose tissue", "adrenal gland", "animal ovary", "appendix", "bladder", "bone marrow", "brain", "cerebral cortex", "colon", "duodenum", "endometrium", "esophagus", "frontal cortex", "gall bladder", "gallbladder", "gut", "heart", "kidney", "liver", "lung", "lymph node", "monocyte", "natural killer...", "ovary", "pancreas", "placenta", "platelet", "prostate", "rectum", "retina", "salivary gland", "skin", "small intestine", "spinal cord", "spleen", "stomach", "testis", "thyroid", "urinary bladder"));

        assertThat(subject.getGeneNames().size(), is(3));
        assertThat(subject.getGeneNames(), contains("Twenty seven tissues", "Human Proteome Map - adult", "Human Proteome Map - fetus"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-PROT-1?geneQuery=ENSG00000005884&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-PROT-1?geneQuery=ENSG00000005884&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus"));
    }


}
