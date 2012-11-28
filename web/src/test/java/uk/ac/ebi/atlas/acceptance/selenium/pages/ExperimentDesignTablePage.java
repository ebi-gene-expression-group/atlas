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

    @FindBy(id = "isOnlyAnalysed")
    WebElement onlyAnalysedBox;

    @FindBy(id = "download-experiment-design-link")
    WebElement downloadExperimentDesignLink;

    @FindBy(xpath = "//div[@id='experiment-design-table_filter']/label/input")
    WebElement searchField;

    @FindBy(xpath = "//thead/tr[1]/th[1]")
    WebElement firstColumnHeader;

    @FindBy(xpath = "//thead/tr[1]/th[3]")
    WebElement thirdColumnHeader;

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public String getDownloadExperimentDesignLink() {
        return downloadExperimentDesignLink.getAttribute("href");
    }

    public void clickFirstColumnHeader() {
        firstColumnHeader.click();
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

    public List<String> getFirstExperimentDesign() {
        return getRowValues(experimentDesignTable, 1);
    }

    public List<String> getLastExperimentDesign() {
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
