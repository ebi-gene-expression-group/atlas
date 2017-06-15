
package uk.ac.ebi.atlas.experiments;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;

import java.util.concurrent.TimeUnit;

public class ExperimentsTablePagePlantsSIT extends SinglePageSeleniumFixture {

    private ExperimentsTablePage subject;
    private static final String PLANTS_KINGDOM_PARAMETER = "kingdom=plants";

    @Override
    protected void getStartingPage() {

        subject = new ExperimentsTablePage(driver, PLANTS_KINGDOM_PARAMETER);
        subject.get();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.pollingEvery(5, TimeUnit.SECONDS).until((ExpectedCondition<Boolean>) webDriver -> !subject.getExperimentsTableInfo().startsWith("Showing 0"));
    }

    @Test
    public void countPlantsExperiments() {
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.NUMBER_OF_PLANTS_EXPERIMENTS + " entries");
    }

}