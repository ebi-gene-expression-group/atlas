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

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

abstract class TablePage extends GlobalSearchPage {

    private static final String CELL_XPATH_TEMPLATE = "tbody/tr[%d]/td[%d]";
    private static final String BOTTOM_CELL_XPATH_TEMPLATE = "tbody/tr[last()]/td[%d]";
    private static final String ALL_TABLE_ROWS_XPATH = "tbody/tr";
    private static final String ROW_CELLS_XPATH_TEMPLATE = "tbody/tr[%d]/td";
    private static final String LAST_ROW_CELLS_XPATH = "tbody/tr[last()]/td";
    private static final String LAST_COLUMN_CELLS_XPATH = "tbody//td[last()]";
    private static final String FIRST_COLUMN_CELLS_XPATH = "tbody//th[1]";
    private static final String GENE_ANCHOR_XPATH_TEMPLATE = "tbody/tr[%d]/th/div/span/a";
    private static final String SECOND_COLUMN_CELLS_XPATH = "tbody//td[2]";
    private static final String COLUMN_CELLS_XPATH = "tbody//td[%d]";
    protected static final String TABLE_HEADERS_XPATH = "thead[1]/tr/th";

    @FindBy(id = "experimentDescription")
    private WebElement experimentDescription;

    @FindBy(id = "goto-experiment")
    private WebElement experimentDescriptionLink;

    @FindBy(id = "experimentOrganisms")
    private WebElement experimentOrganisms;

    @FindBy(id = "experimentReferences")
    private WebElement experimentReferences;

    @FindBy(id = "experimentArrayDesigns")
    private WebElement experimentArrayDesigns;


    TablePage(WebDriver driver) {
        this(driver, null);
    }

    public TablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    protected int getTableRowCount(WebElement table) {
        return table.findElements(By.xpath(ALL_TABLE_ROWS_XPATH)).size();
    }

    protected WebElement getTableRowElement(WebElement table, int index) {
        return table.findElements(By.xpath(ALL_TABLE_ROWS_XPATH)).get(index);
    }

    protected List<String> getFirstColumnValues(WebElement table) {
        List<WebElement> tableCells = getFirstColumnElements(table);
        return toStrings(tableCells);
    }

    protected List<WebElement> getFirstColumnElements(WebElement table) {
        return table.findElements(By.xpath(FIRST_COLUMN_CELLS_XPATH));
    }

    protected List<String> getSecondColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(SECOND_COLUMN_CELLS_XPATH));
        return toStrings(tableCells);
    }

    protected List<WebElement> getRow(WebElement table, int oneBasedRowIndex) {
        String xPath = String.format(ROW_CELLS_XPATH_TEMPLATE, oneBasedRowIndex);
            return table.findElements(By.xpath(xPath));
    }

    protected List<WebElement> getFirstTableRow(WebElement table) {
        return getRow(table, 1);
    }

    protected List<String> getRowValues(WebElement table, int oneBasedRowIndex) {
        List<WebElement> tableCells = getRow(table, oneBasedRowIndex);
        return toStrings(tableCells);
    }

    protected List<String> getLastRowValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_ROW_CELLS_XPATH));
        return toStrings(tableCells);
    }

    protected List<String> getLastColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_COLUMN_CELLS_XPATH));
        return toStrings(tableCells);
    }

    protected WebElement getCellFromFirstTableRow(WebElement table, int columnIndex) {
        return getCell(table, 1, columnIndex);
    }

    protected List<WebElement> getNonEmptyCellsFromFirstTableRow(WebElement table) {
        List<WebElement> nonEmptyCells = new ArrayList<>();
        for (WebElement cell : getFirstTableRow(table)) {
            if (!cell.getCssValue("background-color").equals("transparent") && !cell.getCssValue("background-color").equals("rgba(0, 0, 0, 0)")) {
                nonEmptyCells.add(cell);
            }
        }
        return nonEmptyCells;
    }

    protected List<String> getColumnValues(WebElement table, int columnIndex) {
        String xPath = String.format(COLUMN_CELLS_XPATH, columnIndex);
        List<WebElement> tableCells = table.findElements(By.xpath(xPath));
        return toStrings(tableCells);
    }

    protected String getTableBottomCellValue(WebElement table, int columnIndex) {
        String xPath = String.format(BOTTOM_CELL_XPATH_TEMPLATE, columnIndex);
        return getCellValue(table, xPath);
    }

    protected WebElement getCell(WebElement table, int rowIndex, int columnIndex) {
        String xPath = String.format(CELL_XPATH_TEMPLATE, rowIndex, columnIndex);
        return getCell(table, xPath);
    }

    protected WebElement getGeneAnchor(WebElement table, int geneProfileIndex) {
        String xPath = String.format(GENE_ANCHOR_XPATH_TEMPLATE, geneProfileIndex);
        return getCell(table, xPath);
    }

    protected boolean hasGeneAnchor(WebElement table, int geneProfileIndex) {
        String xPath = String.format(GENE_ANCHOR_XPATH_TEMPLATE, geneProfileIndex);
        return !table.findElements(By.xpath(xPath)).isEmpty();
    }

    protected List<String> getTableHeaders(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(TABLE_HEADERS_XPATH));
        return toStrings(tableCells);
    }

    protected int getTableColumnsCount(WebElement table) {
        return getTableHeaders(table).size();
    }

    private String getCellValue(WebElement table, String xPath) {
        return getCell(table, xPath).getText();
    }

    private WebElement getCell(WebElement table, String xPath) {
        return table.findElement(By.xpath(xPath));
    }

    private List<String> toStrings(List<WebElement> tableCells){
        List<String> strings = Lists.newArrayList();
        for (WebElement webElement: tableCells){
             strings.add(webElement.getText());
        }
        return strings;
    }

    public String getExperimentDescription() {
        return experimentDescription.getText();
    }

    public String getExperimentDescriptionLink() {
        return experimentDescriptionLink.getAttribute("href");
    }

    public String getExperimentOrganisms() {
        return experimentOrganisms.getText();
    }

    public String getExperimentReferences() {
        return experimentReferences.getText();
    }

    public String getExperimentArrayDesigns() {
        return experimentArrayDesigns.getText();
    }
}