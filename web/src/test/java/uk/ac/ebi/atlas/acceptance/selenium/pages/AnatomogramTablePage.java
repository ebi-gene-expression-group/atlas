package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public abstract class AnatomogramTablePage extends TablePage {

    @FindBy(id = "anatomogram")
    private WebElement anatomogram;

    public AnatomogramTablePage(WebDriver driver) {
        super(driver);
    }

    public AnatomogramTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public WebElement getAnatomogram() {
        return anatomogram;
    }

}
