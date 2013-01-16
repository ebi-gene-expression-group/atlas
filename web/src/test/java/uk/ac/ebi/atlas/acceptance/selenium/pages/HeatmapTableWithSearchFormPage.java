package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HeatmapTableWithSearchFormPage extends HeatmapTablePage {

    protected static final String DEFAULT_PAGE_URI = "/gxa/experiments/E-MTAB-513";

    @FindBy(id = "geneQuery")
    private WebElement geneQuery;

    @FindBy(id = "queryFactorValues")
    WebElement queryFactorValues;

    @FindBy(id = "cutoff")
    private WebElement cutoff;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public HeatmapTableWithSearchFormPage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTableWithSearchFormPage(WebDriver driver, String parameters) {
        super(driver, parameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public String getGeneQuery() {
        return geneQuery.getText();
    }

    public String getCutoff() {
        return cutoff.getAttribute("value");
    }

    public List<String> getQueryFactorValues() {
        Select queryFactorValuesSelect = new Select(queryFactorValues);
        List<String> selectedValues = new ArrayList<>();
        for (WebElement webElement : queryFactorValuesSelect.getAllSelectedOptions()) {
            selectedValues.add(webElement.getAttribute("value"));
        }
        return selectedValues;
    }

    public HeatmapTableWithSearchFormPage submit() {
        submitButton.click();
        return new HeatmapTableWithSearchFormPage(driver);
    }

    public void setCutoff(double value) {
        cutoff.clear();
        cutoff.sendKeys("" + value);
    }

    private String selectOptionScript(String option) {
        return "$('#queryFactorValues option').each(function() {\n" +
                "            if ($(this).text() == '" + option + "') {\n" +
                "                $(this).attr('selected',true);\n" +
                "            }" +
                "        });" +
                "$('#queryFactorValues').trigger('liszt:updated').trigger('change')";
    }

    public void selectQueryFactorValue(String... queryFactorValues) {

        for (String queryFactorValue : queryFactorValues) {
            ((JavascriptExecutor) driver).executeScript(selectOptionScript(queryFactorValue));
        }
    }
}