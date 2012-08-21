package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class ExperimentPage extends AtlasPage {

    private static final String PAGE_URI = "/atlas/experiment";

    @FindBy(id = "expressionTable")
    WebElement tableElement;


    public ExperimentPage(WebDriver driver) {
        super(driver);
    }

    public int getTableRowCount() {
        List<WebElement> tableRows = tableElement.findElements(By.xpath("tbody/tr"));
        return tableRows.size();
    }

    public String getMostExpressedTranscriptId() {
        return tableElement.findElement(By.xpath("tbody/tr/td[1]")).getText();
    }

    public String getTitle() {
        return "Experiment";
    }

    @Override
    protected String getPageURI() {
        return PAGE_URI;
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(PAGE_URI));
    }

}
