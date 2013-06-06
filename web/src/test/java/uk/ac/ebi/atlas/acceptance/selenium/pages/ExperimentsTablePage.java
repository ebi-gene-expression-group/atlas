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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ExperimentsTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments";

    @FindBy(id = "experiments-table")
    protected WebElement experimentsTable;

    @FindBy(id = "experiments-table_info")
    private WebElement experimentsTableInfo;

    @FindBy(xpath = "//div[@id='experiments-table_filter']/label/input")
    private WebElement searchField;

    @FindBy(xpath = "//thead/tr[1]/th[1]")
    private WebElement firstColumnHeader;

    @FindBy(xpath = "//thead/tr[1]/th[2]")
    private WebElement secondColumnHeader;

    @FindBy(xpath = "//thead/tr[1]/th[3]")
    private WebElement thirdColumnHeader;

    public ExperimentsTablePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public void clickFirstColumnHeader() {
        firstColumnHeader.click();
    }

    public void clickSecondColumnHeader() {
        secondColumnHeader.click();
    }

    public void clickThirdColumnHeader() {
        thirdColumnHeader.click();
    }

    public String getSearchFieldValue() {
        return searchField.getAttribute("value");
    }

    public void setSearchFieldValue(String value) {
        searchField.click();
        searchField.sendKeys(value);
    }

    public List<String> getExperimentsTableHeader() {
        return getTableHeaders(experimentsTable);
    }

    public List<String> getFirstExperimentInfo() {
        return getRowValues(experimentsTable, 1);
    }

    public List<String> getLastExperimentInfo() {
        return getLastRowValues(experimentsTable);
    }

    public String getExperimentsTableInfo() {
        return experimentsTableInfo.getText();
    }

}