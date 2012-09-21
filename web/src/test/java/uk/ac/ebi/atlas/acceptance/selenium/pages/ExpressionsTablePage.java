package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class ExpressionsTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/atlas/experiment";

    public static final int EXPRESSION_LEVEL_COLUMN_INDEX = 3;
    public static final int TRANSCRIPT_ID_COLUMN_INDEX = 1;
    public static final int ORGANISM_PART_INDEX = 2;
    public static final int SPECIFICITY_COLUMN_INDEX = 4;

    @FindBy(id = "expressionsTable")
    WebElement expressionsTable;

    private String pageUri = DEFAULT_PAGE_URI;

    ExpressionsTablePage(WebDriver driver) {
        super(driver);
    }

    public ExpressionsTablePage(WebDriver driver, String parameters) {
        this(driver);
        pageUri = pageUri.concat(parameters);
    }

    public int getExpressionsTableRowCount() {
        return getTableRowCount(expressionsTable);
    }

    public String getTranscriptIdForGreatestExpressionLevel() {
        return getTableTopCellValue(expressionsTable, TRANSCRIPT_ID_COLUMN_INDEX);
    }

    public String getGreatestExpressionLevel() {
        return getTableTopCellValue(expressionsTable, EXPRESSION_LEVEL_COLUMN_INDEX);
    }

    public String getOrganismPartForGreatestExpressionLevel() {
        return getTableTopCellValue(expressionsTable, ORGANISM_PART_INDEX);
    }

    public String getSpecificityForGreatestExpressionLevel() {
        return getTableTopCellValue(expressionsTable, SPECIFICITY_COLUMN_INDEX);
    }

    public String getTranscriptIdForSmallestExpressionLevel() {
        return getTableBottomCellValue(expressionsTable, TRANSCRIPT_ID_COLUMN_INDEX);
    }

    public String getSmallestExpressionLevel() {
        return getTableBottomCellValue(expressionsTable, EXPRESSION_LEVEL_COLUMN_INDEX);
    }

    public String getOrganismPartForSmallestExpressionLevel() {
        return getTableBottomCellValue(expressionsTable, ORGANISM_PART_INDEX);
    }

    public String getSpecificityForSmallestExpressionLevel() {
        return getTableBottomCellValue(expressionsTable, SPECIFICITY_COLUMN_INDEX);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    protected String getPageURI() {
        return pageUri;
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(pageUri));
    }

}
