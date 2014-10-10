package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HierarchicalClusteringPdfControllerTest {

    HierarchicalClusteringPdfController subject = new HierarchicalClusteringPdfController();

    @Test
    public void extractSpecies_NoSpecies() {
        assertThat(subject.extractSpecies("E-MTAB-513-heatmap.pdf"), is(""));
    }

    @Test
    public void extractSpecies_species2Words() {
        assertThat(subject.extractSpecies("E-GEOD-30352_monodelphis_domestica"), is("monodelphis_domestica"));
    }

    @Test
    public void extractSpecies_species4Words() {
        assertThat(subject.extractSpecies("E-MTAB-2039_oryza_sativa_japonica_group"), is("oryza_sativa_japonica_group"));
    }

    @Test
    public void generateExpdataUrlWithoutSpecies() throws Exception {
        assertThat(subject.generateExpdataUrl("E-MTAB-513", "E-MTAB-513"), is("/expdata/E-MTAB-513/E-MTAB-513-heatmap.pdf"));
    }

    @Test
    public void generateExpdataUrlWithSpecies() throws Exception {
        assertThat(subject.generateExpdataUrl("E-GEOD-30352", "E-GEOD-30352_homo_sapiens"), is("/expdata/E-GEOD-30352/E-GEOD-30352_homo_sapiens-heatmap.pdf"));
    }
}