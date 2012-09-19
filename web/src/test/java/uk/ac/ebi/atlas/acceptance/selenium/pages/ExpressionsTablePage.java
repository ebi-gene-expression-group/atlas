package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class ExpressionsTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/atlas/experiment";

    public static final int RPKM_COLUMN_INDEX = 3;
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

    public String getTranscriptIdForGreatestRPKMValue() {
        return getTableTopCellValue(expressionsTable, TRANSCRIPT_ID_COLUMN_INDEX);
    }

    public String getGreatestRPKMValue() {
        return getTableTopCellValue(expressionsTable, RPKM_COLUMN_INDEX);
    }

    public String getOrganismPartForGreatestRPKMValue() {
        return getTableTopCellValue(expressionsTable, ORGANISM_PART_INDEX);
    }

    public String getSpecificityForGreatestRPKMValue() {
        return getTableTopCellValue(expressionsTable, SPECIFICITY_COLUMN_INDEX);
    }

    public String getTranscriptIdForSmallestRPKMValue() {
        return getTableBottomCellValue(expressionsTable, TRANSCRIPT_ID_COLUMN_INDEX);
    }

    public String getSmallestRPKMValue() {
        return getTableBottomCellValue(expressionsTable, RPKM_COLUMN_INDEX);
    }

    public String getOrganismPartForSmallestRPKMValue() {
        return getTableBottomCellValue(expressionsTable, ORGANISM_PART_INDEX);
    }

    public String getSpecificityForSmallestRPKMValue() {
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
