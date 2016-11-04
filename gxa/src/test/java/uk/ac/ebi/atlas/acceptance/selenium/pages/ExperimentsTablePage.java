
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ExperimentsTablePage extends AtlasPage {

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments";

    private static final String TABLE_HEADERS_XPATH = "thead/tr/th";

    private static final String ROW_CELLS_XPATH_TEMPLATE = "tbody/tr[%d]/td";
    private static final String LAST_ROW_CELLS_XPATH = "tbody/tr[last()]/td";

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

    @FindBy(id="gxaExperimentsTableKingdomSelect")
    private WebElement plantsSelectWebElement;

    public ExperimentsTablePage(WebDriver driver) {
        super(driver);
    }

    public ExperimentsTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public void clickFirstColumnHeader() {
        getExperimentTable();
        firstColumnHeader.click();
    }

    public void clickSecondColumnHeader() {
        getExperimentTable();
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

    protected List<String> getTableHeaders(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(TABLE_HEADERS_XPATH));
        return toStrings(tableCells);
    }

    private List<String> toStrings(List<WebElement> tableCells){
        List<String> strings = Lists.newArrayList();
        for (WebElement webElement: tableCells){
            strings.add(webElement.getText());
        }
        return strings;
    }

    private WebElement getExperimentTable(){
        waitForExperimentTableToLoad();
        return driver.findElement(By.id("experiments-table"));
    }

    // wait for the ajax table to load
    private void waitForExperimentTableToLoad() {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[@id=\"experiments-table\"]/tbody/tr/td"), "Loading..."));
    }

    public List<String> getExperimentsTableHeader() {
        return getTableHeaders(getExperimentTable());
    }

    public List<String> getFirstExperimentInfo() {
        return getRowValues(getExperimentTable(), 1);
    }

    public List<String> getLastExperimentInfo() {
        return getLastRowValues(getExperimentTable());
    }

    public String getExperimentsTableInfo() {
        return experimentsTableInfo.getText();
    }

    protected List<String> getRowValues(WebElement table, int oneBasedRowIndex) {
        List<WebElement> tableCells = getRow(table, oneBasedRowIndex);
        return toStrings(tableCells);
    }

    protected List<String> getLastRowValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_ROW_CELLS_XPATH));
        return toStrings(tableCells);
    }

    protected List<WebElement> getRow(WebElement table, int oneBasedRowIndex) {
        String xPath = String.format(ROW_CELLS_XPATH_TEMPLATE, oneBasedRowIndex);
        return table.findElements(By.xpath(xPath));
    }

    public void selectPlantsExperiments() {
        Select plantsSelect = new Select(plantsSelectWebElement);
        plantsSelect.selectByValue("plants");
    }

    public void selectAnimalsAndFungiExperiments() {
        Select plantsSelect = new Select(plantsSelectWebElement);
        plantsSelect.selectByValue("animals-fungi");
    }

    public void selectPlantAnimalAndFungiExperiments() {
        Select plantsSelect = new Select(plantsSelectWebElement);
        plantsSelect.selectByValue("");
    }
}