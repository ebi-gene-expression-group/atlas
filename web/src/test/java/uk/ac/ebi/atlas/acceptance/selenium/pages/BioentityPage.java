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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

// this class is making some problems with GIT
public class BioEntityPage extends AtlasPage {

    private static final String PAGE_LOCATION = "/gxa/";

    private String bioEntityIdentifier;

    private String type;

    @FindBy(id = "accordion")
    private WebElement accordion;

    @FindBy(id = "bioEntityCardTable")
    private WebElement table;

    public BioEntityPage(WebDriver driver) {
        super(driver);
    }

    public BioEntityPage(WebDriver driver, String bioEntityIdentifier, String type) {
        super(driver);
        this.bioEntityIdentifier = bioEntityIdentifier;
        this.type = type;
    }

    BioEntityPage(WebDriver driver, String bioEntityIdentifier, String type, String httpParameters) {
        super(driver, httpParameters);
        this.bioEntityIdentifier = bioEntityIdentifier;
        this.type = type;
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + type + "/" + bioEntityIdentifier;
    }

    protected String getBioEntityIdentifier() {
        return bioEntityIdentifier;
    }

    public String getBioEntityCardTitle() {
        WebElement header = accordion.findElement(By.className("bioEntityCardHeader"));
        return header.getText();
    }

    public boolean isCardExpanded() {
        WebElement body = accordion.findElement(By.tagName("div"));
        return body.isDisplayed();
    }

    public void hideCard() {
        WebElement header = accordion.findElement(By.className("bioEntityCardHeader"));
        header.click();

        By byBioEntityCardClass = By.className("bioEntityCard");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(byBioEntityCardClass));
    }

    public int getTableSize() {
        return table.findElements(By.tagName("tr")).size();
    }

    public List<String> getTableRow(int index) {
        List<String> row = Lists.newArrayList();
        WebElement rowElement = table.findElements(By.tagName("tr")).get(index);
        for (WebElement dataElement : rowElement.findElements(By.tagName("td"))) {
            row.add(dataElement.getText());
        }
        return row;
    }

    public List<String> getLinksInTableRow(int index) {
        List<String> row = Lists.newArrayList();
        WebElement rowElement = table.findElements(By.tagName("tr")).get(index);
        for (WebElement dataElement : rowElement.findElements(By.tagName("td"))) {
            for (WebElement linkElement : dataElement.findElements(By.tagName("a"))) {
                row.add(linkElement.getAttribute("href"));
            }
        }
        return row;
    }

}
