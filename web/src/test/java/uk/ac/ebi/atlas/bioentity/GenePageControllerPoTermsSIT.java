package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GenePageControllerPoTermsSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "AT3G11340";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for AT3G11340"));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("F11B9.23 Arabidopsis thaliana UDP-Glycosyltransferase superfamily protein"));
        assertThat(subject.getPropertiesTableSize(), is(7));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Ontology", "leaf senescence, glucosyltransferase activity, defense response to fungus (... and 15 more)"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "LP.02 two leaves visible stage, petal differentiation and expansion stage, flowering stage (... and 8 more)"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("InterPro", "UDP-glucuronosyl/UDP-glucosyltransferase (family)"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("Ensembl Gene", "AT3G11340"));
        assertThat(subject.getPropertiesTableRow(4), hasItems("Entrez", "820307"));
        assertThat(subject.getPropertiesTableRow(5), hasItems("UniProt", "Q8GWA0, Q9C768, Q9CAY9"));
        assertThat(subject.getPropertiesTableRow(6), hasItems("Design Element", "256252_at"));

        assertThat(subject.getLinksInTableRow(1).get(0), is("http://plantontology.org/amigo/go.cgi?view=details&search_constraint=terms&depth=0&query=PO%3A0007098"));
        assertThat(subject.getLinksInTableRow(1).get(1), is("http://plantontology.org/amigo/go.cgi?view=details&search_constraint=terms&depth=0&query=PO%3A0007611"));
    }

    @Test
    public void showMoreAndLessPlantOntologyTerms() {
        subject.clickShowMorePoLinks();
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "LP.02 two leaves visible stage, petal differentiation and expansion stage, flowering stage, root, vascular leaf, stamen, sepal, petal, hypocotyl, collective leaf structure, pollen (show less)"));

        subject.clickShowLessPoLinks();
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "LP.02 two leaves visible stage, petal differentiation and expansion stage, flowering stage (... and 8 more)"));
    }
}
