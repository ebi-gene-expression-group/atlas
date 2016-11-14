
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DifferentialExperimentDesignTablePage extends ExperimentDesignTablePage {

    public static String EXPERIMENT_ACCESSION = "E-GEOD-38400";

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design";

    @FindBy(id = "selectedContrast")
    private WebElement contrastSelection;

    public DifferentialExperimentDesignTablePage(WebDriver driver) {
        super(driver);
    }

    public DifferentialExperimentDesignTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public String getSelectedContrast() {
        Select select = new Select(contrastSelection);
        return select.getFirstSelectedOption().getText();
    }

    public void selectContrast(String value) {
        Select select = new Select(contrastSelection);
        select.selectByValue(value);
    }

    public String getLineColor(int rowIndex) {
        List<WebElement> row = getRow(experimentDesignTable, rowIndex);
        WebElement element = row.get(0);
        return element.getCssValue("background-color");
    }
}