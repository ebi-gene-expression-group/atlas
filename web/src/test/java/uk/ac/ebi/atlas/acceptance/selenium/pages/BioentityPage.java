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

public class BioentityPage extends AtlasPage {

    private static final String PAGE_LOCATION = "/gxa/";

    private String geneIdentifier;

    private String type;

    @FindBy(id = "accordion")
    private WebElement accordion;

    @FindBy(id = "geneCardTable")
    private WebElement table;

    public BioentityPage(WebDriver driver) {
        super(driver);
    }

    public BioentityPage(WebDriver driver, String geneIdentifier, String type) {
        super(driver);
        this.geneIdentifier = geneIdentifier;
        this.type = type;
    }

    BioentityPage(WebDriver driver, String geneIdentifier, String type, String httpParameters) {
        super(driver, httpParameters);
        this.geneIdentifier = geneIdentifier;
        this.type = type;
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION +type + "/" + geneIdentifier;
    }

    protected String getGeneIdentifier() {
        return geneIdentifier;
    }

    public String getGeneCardTitle() {
        WebElement header = accordion.findElement(By.className("geneCardHeader"));
        return header.getText();
    }

    public boolean isCardExpanded() {
        WebElement body = accordion.findElement(By.tagName("div"));
        return body.isDisplayed();
    }

    public void hideCard() {
        WebElement header = accordion.findElement(By.className("geneCardHeader"));
        header.click();

        By byGeneCardClass = By.className("geneCard");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(byGeneCardClass));
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