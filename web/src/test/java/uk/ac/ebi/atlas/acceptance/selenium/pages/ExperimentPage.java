package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class ExperimentPage extends AtlasPage {

    private static final String DEFAULT_PAGE_URI = "/atlas/experiment";

    @FindBy(id = "expressionsTable")
    WebElement tableElement;

    private String pageUri = DEFAULT_PAGE_URI;

    public ExperimentPage(WebDriver driver) {
        super(driver);
    }

    public ExperimentPage(WebDriver driver, String parameters) {
        super(driver);

        pageUri = pageUri.concat(parameters);
    }

    public int getTableRowCount() {
        List<WebElement> tableRows = tableElement.findElements(By.xpath("tbody/tr"));
        return tableRows.size();
    }

    public String getTranscriptIdForGreatestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr/td[1]")).getText();
    }

    public String getGreatestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr/td[3]")).getText();
    }

    public String getOrganismPartForGreatestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr/td[2]")).getText();
    }

    public String getSpecificityForGreatestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr/td[4]")).getText();
    }

    public String getTranscriptIdForSmallestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr[10]/td[1]")).getText();
    }

    public String getSmallestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr[10]/td[3]")).getText();
    }

    public String getOrganismPartForSmallestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr[10]/td[2]")).getText();
    }

    public String getSpecificityForSmallestRPKMValue() {
        return tableElement.findElement(By.xpath("tbody/tr[10]/td[4]")).getText();
    }


    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    protected String getPageURI() {
        return pageUri;
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(pageUri));
    }

}
