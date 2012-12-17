package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

abstract class TablePage extends AtlasPage {

    private static final String CELL_XPATH_TEMPLATE = "tbody/tr[%d]/td[%d]";
    private static final String BOTTOM_CELL_XPATH_TEMPLATE = "tbody/tr[last()]/td[%d]";
    private static final String ALL_TABLE_ROWS_XPATH = "tbody/tr";
    private static final String ROW_CELLS_XPATH_TEMPLATE = "tbody/tr[%d]/td";
    private static final String LAST_ROW_CELLS_XPATH = "tbody/tr[last()]/td";
    private static final String LAST_COLUMN_CELLS_XPATH = "tbody//td[last()]";
    private static final String FIRST_COLUMN_CELLS_XPATH = "tbody//td[1]";
    private static final String SECOND_COLUMN_CELLS_XPATH = "tbody//td[2]";
    private static final String COLUMN_CELLS_XPATH = "tbody//td[%d]";
    private static final String TABLE_HEADERS_XPATH = "thead/tr/th";

    @FindBy(id = "experimentDescription")
    private WebElement experimentDescription;

    TablePage(WebDriver driver) {
        super(driver);
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
        List<WebElement> tableCells = table.findElements(By.xpath(FIRST_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    protected List<String> getSecondColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(SECOND_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    protected List<String> getRowValues(WebElement table, int index) {
        List<WebElement> tableCells = getTableRow(table, index);
        return Lists.transform(tableCells, getText);
    }

    protected List<WebElement> getTableRow(WebElement table, int index) {
        String xPath = String.format(ROW_CELLS_XPATH_TEMPLATE, index);
        return table.findElements(By.xpath(xPath));
    }

    protected List<WebElement> getFirstTableRow(WebElement table) {
        return getTableRow(table, 1);
    }

    protected List<String> getLastRowValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_ROW_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    protected List<String> getLastColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    protected WebElement getCellFromFirstTableRow(WebElement table, int columnIndex) {
        return getCell(table, 1, columnIndex);
    }

    protected List<WebElement> getNonEmptyCellsFromFirstTableRow(WebElement table) {
        List<WebElement> nonEmptyCells = new ArrayList<>();
        for (WebElement cell : getFirstTableRow(table)) {
            if (!cell.getCssValue("background-color").equals("rgba(255, 255, 255, 1)")) {
                nonEmptyCells.add(cell);
            }
        }
        return nonEmptyCells;
    }

    protected List<String> getColumnValues(WebElement table, int columnIndex) {
        String xPath = String.format(COLUMN_CELLS_XPATH, columnIndex);
        List<WebElement> tableCells = table.findElements(By.xpath(xPath));
        return Lists.transform(tableCells, getText);
    }

    protected String getTableBottomCellValue(WebElement table, int columnIndex) {
        String xPath = String.format(BOTTOM_CELL_XPATH_TEMPLATE, columnIndex);
        return getCellValue(table, xPath);
    }

    protected WebElement getCell(WebElement table, int rowIndex, int columnIndex) {
        String xPath = String.format(CELL_XPATH_TEMPLATE, rowIndex, columnIndex);
        return getCell(table, xPath);
    }

    protected List<String> getTableHeaders(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(TABLE_HEADERS_XPATH));
        return Lists.transform(tableCells, getText);
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

    private Function<WebElement, String> getText = new Function<WebElement, String>() {
        public String apply(WebElement tableCell) {
            return tableCell.getText();
        }
    };

    public String getExperimentDescription() {
        return experimentDescription.getText();
    }
}