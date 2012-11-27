/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class ExperimentDesignTablePage extends TablePage {

    public static String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments/" + EXPERIMENT_ACCESSION + "-experiment-design";

    public ExperimentDesignTablePage(WebDriver driver) {
        super(driver);
    }

    public ExperimentDesignTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @FindBy(id = "experiment-design-table")
    WebElement experimentDesignTable;

    @FindBy(id = "experiment-design-table_info")
    WebElement experimentDesignTableInfo;

    @FindBy(id = "togglebutton")
    WebElement toggleButton;

    @FindBy(id = "experiment-design-table_next")
    WebElement experimentDesignTableNext;

    @FindBy(id = "experiment-design-table_previous")
    WebElement experimentDesignTablePrevious;

    @FindBy(name = "experiment-design-table_length")
    WebElement experimentDesignTableLength;

    @FindBy(xpath = "//div[@id='experiment-design-table_filter']/label/input")
    WebElement searchField;

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public String getSearchFieldValue() {
        return searchField.getAttribute("value");
    }

    public void setSearchFieldValue(String value) {
        searchField.click();
        searchField.sendKeys(value);
    }

    public String getLenghtValue() {
        experimentDesignTableLength.click();
        Select select = new Select(experimentDesignTableLength);
        return select.getFirstSelectedOption().getText();
    }

    public void setLengthValue(String value) {
        experimentDesignTableLength.click();
        Select select = new Select(experimentDesignTableLength);
        select.selectByValue(value);
    }

    public List<String> getExperimentDesignTableHeader() {
        return getTableHeaders(experimentDesignTable);
    }

    public List<String> getFirstExperimentDesign() {
        return getRowValues(experimentDesignTable, 1);
    }

    public List<String> getLastExperimentDesign() {
        return getLastRowValues(experimentDesignTable);
    }

    public String getExperimentDesignTableInfo() {
        return experimentDesignTableInfo.getText();
    }

    public void clickToggleButton() {
        toggleButton.click();
    }

    public String getToggleButtonValue() {
        return toggleButton.getText();
    }

    public void clickPreviousButton() {
        experimentDesignTablePrevious.click();
    }

    public void clickNextButton() {
        experimentDesignTableNext.click();
    }

    public boolean isTextInBoldFace() {
        for (int i = 1; i < getTableRowCount(experimentDesignTable); i++) {
            WebElement row = getTableRowElement(experimentDesignTable, i);
            if (row.getAttribute("class").contains("analysed"))
                return true;
        }
        return false;
    }

}
