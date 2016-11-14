
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HeatmapTableWithSearchFormPage extends HeatmapTablePage {

    @FindBy(id = "geneQuery")
    private WebElement geneQuery;

    @FindBy(id = "queryFactorValues")
    WebElement queryFactorValues;

    @FindBy(id = "cutoff")
    private WebElement cutoff;

    @FindBy(id = "preferences.errors")
    private WebElement preferencesErrors;

    @FindBy(id = "heatmap-message")
    private WebElement heatmapMessage;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public HeatmapTableWithSearchFormPage(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithSearchFormPage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public String getGeneQuery() {
        return geneQuery.getText();
    }

    public String getCutoff() {
        return cutoff.getAttribute("value");
    }

    public String getPreferencesErrors(){
        return preferencesErrors.getText();
    }

    public String getHeatmapMessage() {
        return heatmapMessage.getText();
    }

    public List<String> getFactorValueHeaders() {
        Select queryFactorValuesSelect = new Select(queryFactorValues);
        List<String> selectedValues = new ArrayList<>();
        for (WebElement webElement : queryFactorValuesSelect.getAllSelectedOptions()) {
            selectedValues.add(webElement.getAttribute("value"));
        }
        return selectedValues;
    }

    public HeatmapTableWithSearchFormPage submit() {
        submitButton.click();
        return new HeatmapTableWithSearchFormPage(driver, getExperimentAccession());
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