package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenePageControllerDifferentialResultColorGradientBarSIT extends SinglePageSeleniumFixture {

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
    }

    @Test
    public void checkColourBarGradientRed() {
        subject = new BioEntityPage(driver, "AT3G29644", "genes");
        subject.get();
        assertThat(subject.isFirstColorGradientBarPresent(), is(true));
        assertThat(subject.isSecondColorGradientBarPresent(), is(false));
        assertThat(subject.getFirstColorGradientBar(), is("-moz-linear-gradient(0% 50%, rgb(255, 175, 175), rgb(255, 0, 0))"));
    }

    @Test
    public void checkColourBarGradientBlue() {
        subject = new BioEntityPage(driver, "ENSG00000132604", "genes");
        subject.get();
        assertThat(subject.isFirstColorGradientBarPresent(), is(true));
        assertThat(subject.isSecondColorGradientBarPresent(), is(false));
        assertThat(subject.getFirstColorGradientBar(), is("-moz-linear-gradient(0% 50%, rgb(192, 192, 192), rgb(0, 0, 255))"));
    }

    @Test
    public void checkColourBarGradientBothColors() {
        subject = new BioEntityPage(driver, "AT1G01940", "genes");
        subject.get();
        assertThat(subject.isFirstColorGradientBarPresent(), is(true));
        assertThat(subject.isSecondColorGradientBarPresent(), is(true));
        assertThat(subject.getFirstColorGradientBar(), is("-moz-linear-gradient(0% 50%, rgb(192, 192, 192), rgb(0, 0, 255))"));
        assertThat(subject.getSecondColorGradientBar(), is("-moz-linear-gradient(0% 50%, rgb(255, 175, 175), rgb(255, 0, 0))"));

    }
}
