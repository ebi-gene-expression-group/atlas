package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HierarchicalClusteringPdfViewHelperTest {


    private HierarchicalClusteringPdfViewHelper subject = new HierarchicalClusteringPdfViewHelper("");

    @Test
    public void generateSingleSpeciesUrl() {
        assertThat(subject.generateSingleSpeciesUrl("E-MTAB-513"), is("/experiments/E-MTAB-513/E-MTAB-513-heatmap.pdf"));
    }

    @Test
    public void generateUrl_species2Words() {
        assertThat(subject.generateUrl("E-GEOD-30352", "monodelphis domestica"), is("/experiments/E-GEOD-30352/E-GEOD-30352_monodelphis_domestica-heatmap.pdf"));
    }

}