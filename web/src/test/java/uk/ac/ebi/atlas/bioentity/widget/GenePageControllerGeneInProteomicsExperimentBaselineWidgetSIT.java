
package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerGeneInProteomicsExperimentBaselineWidgetSIT extends SingleDriverSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSG00000005884";
    private static final int RETINA = 33;

    private static BioEntityPage subject;

    @BeforeClass
    public static void getStartingPage() {
        subject = new BioEntityPage(SingleDriverSeleniumFixture.create(), GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void baselineWidgetGenes() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 4 of 4 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        List<String> factorValueHeaders = subject.getFactorValueHeaders();
        assertThat(factorValueHeaders, containsInAnyOrder("B cell", "CD4-positive…", "CD8-positive…", "adipose tissue", "adrenal gland", "animal ovary", "appendix", "bladder", "bone marrow", "brain", "cerebral cortex", "colon", "duodenum", "endometrium", "esophagus", "fallopian tube", "frontal cortex", "gall bladder", "gallbladder", "gut", "heart", "kidney", "liver", "lung", "lymph node", "monocyte", "natural killer…", "ovary", "pancreas", "placenta", "platelet", "prostate", "rectum", "retina", "salivary gland", "skeletal muscle", "skin", "small intestine", "smooth muscle", "spinal cord", "spleen", "stomach", "testis", "thyroid", "tonsil", "urinary bladder"));

        assertThat(subject.getGeneNames().size(), is(4));
        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues", "Human Proteome Map - adult", "Human Proteome Map - fetus"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-PROT-1?geneQuery=ENSG00000005884&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
        assertThat(subject.getGeneLink(3), endsWith("/experiments/E-PROT-1?geneQuery=ENSG00000005884&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus"));
    }

    @Test
    public void expressionLevelDisplayedInScientificNotation() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getGeneProfile(3).get(RETINA), is("9.9 × 106"));
    }

}