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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class GlobalSearchPage extends AtlasPage {

    @FindBy(id = "local-searchbox")
    private WebElement globalSearchBox;

    @FindBy(id = "submit-searchbox")
    private WebElement globalSearchSubmit;

    @FindBy(id = "error-content")
    private WebElement errorContent;

    GlobalSearchPage(WebDriver driver) {
        this(driver, null);
    }

    public GlobalSearchPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public BioEntityPage globalSearchSubmit(){
        getGlobalSearchSubmit().click();
        return new BioEntityPage(driver);
    }

    public boolean isResourceNotFound(){
        return "Resource not found.".equals(errorContent.getText());
    }

    public WebElement getGlobalSearchSubmit() {
        return globalSearchSubmit;
    }

    public void setGlobalSearchText(String value) {
        globalSearchBox.clear();
        globalSearchBox.sendKeys(value);
    }
}
