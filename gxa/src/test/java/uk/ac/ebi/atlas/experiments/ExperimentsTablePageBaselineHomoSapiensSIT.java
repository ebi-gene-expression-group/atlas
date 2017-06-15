
package uk.ac.ebi.atlas.experiments;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;

import java.util.concurrent.TimeUnit;

public class ExperimentsTablePageBaselineHomoSapiensSIT extends SinglePageSeleniumFixture {

    private ExperimentsTablePage subject;
    private static final String PLANTS_KINGDOM_PARAMETER = "experimentType=baseline&organism=Homo+sapiens";

    @Override
    protected void getStartingPage() {

        subject = new ExperimentsTablePage(driver, PLANTS_KINGDOM_PARAMETER);
        subject.get();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.pollingEvery(5, TimeUnit.SECONDS).until((ExpectedCondition<Boolean>) webDriver -> !subject.getExperimentsTableInfo().startsWith("Showing 0"));
    }

    @Test
    public void countBaselineHomoSapiensExperiments() {
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.NUMBER_OF_HOMO_SAPIENS_BASELINE_EXPERIMENTS + " entries");
    }

}