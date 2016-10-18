
package uk.ac.ebi.atlas.experiments;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineExperimentsPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class BaselineExperimentsControllerSIT extends SinglePageSeleniumFixture {

    private BaselineExperimentsPage subject;

    public void getStartingPage() {
        subject = new BaselineExperimentsPage(driver);
        subject.get();
    }

    @Test
    public void countNumberOfSpecies() {
        assertThat(subject.getAllSpeciesItems().size(), is(NumberOfExperiments.NUMBER_OF_BASELINE_SPECIES));
    }

    @Test
    public void checkFirstSpeciesName() {
        assertThat(subject.getNameOfSpecies(0), is("Homo sapiens"));
    }

    @Test
    public void checkSecondSpeciesName() {
        assertThat(subject.getNameOfSpecies(1), is("Anolis carolinensis"));
    }

    @Test
    public void homoSapiensTotal() {
        assertThat(subject.getAllExperimentsOfSpecies(0).size(), is(NumberOfExperiments.NUMBER_OF_HOMO_SAPIENS_BASELINE_EXPERIMENTS));
    }

    @Test
    public void mouseTotal() {
        assertThat(subject.getAllExperimentsOfSpecies(8).size(), is(3));
    }

    @Test
    public void gorillaGorillaLink() {
        List<String> allExperimentLinksOfSpecies = subject.getAllExperimentLinksOfSpecies(4);
        assertThat(allExperimentLinksOfSpecies.get(0), containsString("experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Gorilla%20gorilla"));
    }

    @Test
    public void mouseLinks() {
        List<String> allExperimentLinksOfSpecies = subject.getAllExperimentLinksOfSpecies(8);
        assertThat(allExperimentLinksOfSpecies.get(0), containsString("experiments/E-MTAB-599"));
        assertThat(allExperimentLinksOfSpecies.get(1), containsString("experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:Mus%20musculus"));
    }
}