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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends GlobalSearchPage {

    @FindBy(id = "geneQuery")
    private WebElement geneQuery;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    private static final String DEFAULT_PAGE_URI = "/gxa/home";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public void setGeneQuery(String value) {
        geneQuery.clear();
        geneQuery.sendKeys(value);
    }

    public BioEntitiesPage submitSearch(){
        submitButton.click();
        return new BioEntitiesPage(driver);
    }

}