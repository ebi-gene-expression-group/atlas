package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract class TablePage extends AtlasPage {

    private static final String TABLE_CELL_XPATH_TEMPLATE = "tbody/tr[%d]/td[%d]";
    private static final String TABLE_BOTTOM_CELL_XPATH_TEMPLATE = "tbody/tr[last()]/td[%d]";
    public static final String ALL_TABLE_ROWS_XPATH = "tbody/tr";
    public static final String LAST_COLUMN_CELLS_XPATH = "tbody//td[last()]";
    public static final String FIRST_COLUMN_CELLS_XPATH = "tbody//td[1]";
    public static final String SECOND_COLUMN_CELLS_XPATH = "tbody//td[2]";
    public static final String COLUMN_CELLS_XPATH = "tbody//td[%d]";
    public static final String TABLE_HEADERS_XPATH = "thead/tr/th";

    TablePage(WebDriver driver) {
        super(driver);
    }


    int getTableRowCount(WebElement table) {
        return table.findElements(By.xpath(ALL_TABLE_ROWS_XPATH)).size();
    }

    List<String> getFirstColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(FIRST_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    List<String> getSecondColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(SECOND_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    List<String> getLastColumnValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_COLUMN_CELLS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    String getTableTopCellValue(WebElement table, int columnIndex) {
        return getCellValue(table, 1, columnIndex);
    }

    List<String> getColumnValues(WebElement table, int columnIndex) {
        String xPath = String.format(COLUMN_CELLS_XPATH, columnIndex);
        List<WebElement> tableCells = table.findElements(By.xpath(xPath));
        return Lists.transform(tableCells, getText);
    }

    String getTableBottomCellValue(WebElement table, int columnIndex) {
        String xPath = String.format(TABLE_BOTTOM_CELL_XPATH_TEMPLATE, columnIndex);
        return getCellValue(table, xPath);
    }

    String getCellValue(WebElement table, int rowIndex, int columnIndex) {
        String xPath = String.format(TABLE_CELL_XPATH_TEMPLATE, rowIndex, columnIndex);
        return getCellValue(table, xPath);
    }

    List<String> getTableHeaders(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(TABLE_HEADERS_XPATH));
        return Lists.transform(tableCells, getText);
    }

    int getTableColumnsCount(WebElement table) {
        return getTableHeaders(table).size();
    }

    private String getCellValue(WebElement table, String xPath) {
        return table.findElement(By.xpath(xPath)).getText();
    }

    Function<WebElement, String> getText = new Function<WebElement, String>() {
        public String apply(WebElement tableCell) {
            return tableCell.getText();
        }
    };
}