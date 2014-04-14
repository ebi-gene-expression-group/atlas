/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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

    @FindBy(id = "exactMatch")
    private WebElement exactMatch;

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

    public boolean isExactMatch() {
        return exactMatch.isSelected();
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