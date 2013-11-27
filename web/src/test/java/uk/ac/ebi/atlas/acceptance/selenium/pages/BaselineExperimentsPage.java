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

public class BaselineExperimentsPage extends GlobalSearchPage {

    @FindBy(id = "species-nav")
    private WebElement speciesNav;

    private static final String DEFAULT_PAGE_URI = "/gxa/baseline/experiments";

    public BaselineExperimentsPage(WebDriver driver) {
        super(driver);
    }

    public BaselineExperimentsPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public List<WebElement> getAllSpeciesItems() {
        return speciesNav.findElements(By.className("item"));
    }

    public String getNameOfSpecies(int i) {
        WebElement specie = getAllSpeciesItems().get(i);
        return specie.findElement(By.xpath("div/h2")).getText();
    }

    public List<WebElement> getAllExperimentsOfSpecies(int i) {
        WebElement specie = getAllSpeciesItems().get(i);
        return specie.findElements(By.xpath("div/ul/li"));
    }

    public List<String> getAllExperimentLinksOfSpecies(int i){
        List<String> result = new ArrayList<>();
        List<WebElement> allExperimentsOfSpecies = getAllExperimentsOfSpecies(i);
        for (WebElement link : allExperimentsOfSpecies) {
            result.add(link.findElement(By.xpath("a")).getAttribute("href"));
        }

        return result;
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

}