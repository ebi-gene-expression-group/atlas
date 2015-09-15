package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class    GenePageControllerPoTermsSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "AT4G23810";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for AT4G23810"));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("WRKY53 Arabidopsis thaliana Probable WRKY transcription factor 53"));
        assertThat(subject.getPropertiesTableSize(), is(8));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Ontology", "regulation of transcription, DNA-templated, positive regulation of transcription, DNA-templated, transcription, DNA-templated, chloroplast, defense response to bacterium, incompatible interaction, leaf senescence, response to hydrogen peroxide, sequence-specific DNA binding (... and 7 more)"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "cauline leaf, shoot apex, inflorescence meristem (... and 29 more)"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("InterPro", "DNA-binding WRKY (domain)"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("Ensembl Gene", "AT4G23810"));
        assertThat(subject.getPropertiesTableRow(4), hasItems("Entrez", "828481"));
        assertThat(subject.getPropertiesTableRow(5), hasItems("UniProt", "Q9SUP6"));
        assertThat(subject.getPropertiesTableRow(6), hasItems("Gene Biotype", "protein_coding"));
        assertThat(subject.getPropertiesTableRow(7), hasItems("Design Element", "254231_at"));

        assertThat(subject.getLinksInTableRow(1).get(0), is("http://plantontology.org/amigo/go.cgi?view=details&search_constraint=terms&depth=0&query=PO%3A0000013"));
        assertThat(subject.getLinksInTableRow(1).get(1), is("http://plantontology.org/amigo/go.cgi?view=details&search_constraint=terms&depth=0&query=PO%3A0000037"));
    }

    @Test
    public void showMoreAndLessPlantOntologyTerms() {
        subject.clickShowMorePoLinks();
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "cauline leaf, shoot apex, inflorescence meristem, 4 leaf senescence stage, F mature embryo stage, C globular stage, D bilateral stage, LP.12 twelve leaves visible stage, LP.08 eight leaves visible stage, LP.02 two leaves visible stage, LP.10 ten leaves visible stage, LP.04 four leaves visible stage, LP.06 six leaves visible stage, petal differentiation and expansion stage, flowering stage, leaf lamina base, root, plant embryo, seed, vascular leaf, stamen, carpel, sepal, petal, flower, stem, pedicel, cotyledon, petiole, hypocotyl, leaf apex, collective leaf structure (show less)"));

        subject.clickShowLessPoLinks();
        assertThat(subject.getPropertiesTableRow(1), hasItems("Plant Ontology", "cauline leaf, shoot apex, inflorescence meristem (... and 29 more)"));
    }
}
