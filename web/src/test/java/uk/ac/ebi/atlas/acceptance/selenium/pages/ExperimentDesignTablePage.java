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

import java.text.MessageFormat;
import java.util.List;


public class ExperimentDesignTablePage extends TablePage {

    public static String DEFAULT_EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String PAGE_URI_TEMPLATE = "/gxa/experiments/{0}/experiment-design";

    @FindBy(id = "experiment-design-table")
    protected WebElement experimentDesignTable;

    @FindBy(id = "experiment-design-table_info")
    private WebElement experimentDesignTableInfo;

    @FindBy(id = "showOnlyAnalysedRuns")
    private WebElement onlyAnalysedBox;

    @FindBy(id = "download-experiment-design-link")
    private WebElement downloadExperimentDesignLink;

    @FindBy(xpath = "//div[@id='experiment-design-table_filter']/label/input")
    private WebElement searchField;

    @FindBy(xpath = "//thead/tr[2]/th[1]")
    private WebElement firstColumnHeader;

    @FindBy(xpath = "//thead/tr[2]/th[2]")
    private WebElement secondColumnHeader;

    @FindBy(xpath = "//thead/tr[2]/th[3]")
    private WebElement thirdColumnHeader;

    private String experimentAccession;

    public ExperimentDesignTablePage(WebDriver driver) {
        this(driver, null);
    }

    public ExperimentDesignTablePage(WebDriver driver, String experimentAccession) {
        super(driver, null);
        this.experimentAccession = experimentAccession == null? DEFAULT_EXPERIMENT_ACCESSION : experimentAccession;
    }

    public ExperimentDesignTablePage(WebDriver driver, String experimentAccession, String queryString) {
        super(driver, queryString);
        this.experimentAccession = experimentAccession == null? DEFAULT_EXPERIMENT_ACCESSION : experimentAccession;
    }

    @Override
    protected String getPageURI() {
        return MessageFormat.format(PAGE_URI_TEMPLATE, experimentAccession);
    }

    public String getDownloadExperimentDesignLink() {
        return downloadExperimentDesignLink.getAttribute("href");
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

    public List<String> getExperimentDesignTableHeader() {
        return getTableHeaders(experimentDesignTable);
    }

    public List<String> getFirstExperimentDesignTableLine() {
        return getRowValues(experimentDesignTable, 1);
    }

    public List<String> getLastExperimentDesignTableLine() {
        return getLastRowValues(experimentDesignTable);
    }

    public String getExperimentDesignTableInfo() {
        return experimentDesignTableInfo.getText();
    }

    public void clickOnlyAnalysedBox() {
        onlyAnalysedBox.click();
    }

    public boolean isSelectedOnlyAnalysedBox() {
        return onlyAnalysedBox.isSelected();
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
