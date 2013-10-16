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
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BioEntitiesPage extends BioEntityPage {

    private String queryParams;

    public BioEntitiesPage(WebDriver driver) {
        super(driver);
    }

    public BioEntitiesPage(WebDriver driver, String queryParams) {
        super(driver);
        this.queryParams = queryParams;
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + "query?" + queryParams;
    }

    @Override
    protected int getContrastColumnIndex() {
        return 4;
    }

    public List<BaselineBioentitiesCount> getBaselineCounts() {
        List<BaselineBioentitiesCount> baselineCounts = Lists.newArrayList();

        By byBaselineCountsTableId = By.id("baselineCountsTable");
        FluentWait wait = new WebDriverWait(driver, 25L).pollingEvery(20, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byBaselineCountsTableId));

        List<WebElement> linkElements = driver.findElements(By.className("bioEntityCardLink"));

        List<WebElement> countElements = driver.findElements(By.className("count"));

        for(int i = 0; i < linkElements.size(); i++){
            baselineCounts.add(buildBaselineEntityCount(linkElements.get(i), countElements.get(i)));
        }

        return baselineCounts;

    }

    private BaselineBioentitiesCount buildBaselineEntityCount(WebElement linkElement, WebElement countElement){

        String linkText = linkElement.getText();
        String linkHref = linkElement.getAttribute("href");

        String species = StringUtils.substringBefore(linkText, "-").trim();
        String experimentName = StringUtils.substringAfter(linkText, "-").trim();

        String experimentAccession = StringUtils.substringAfterLast(linkHref, "/");
        experimentAccession = StringUtils.substringBeforeLast(experimentAccession, "?");

        int baselineCount = Integer.parseInt(StringUtils.substringBetween(countElement.getText(),"(",")"));

        return new BaselineBioentitiesCount(experimentName, species, experimentAccession, baselineCount);
    }


    // note - page may have baseline & differential heatmap display levels button
    public void clickDifferentialDisplayLevelsButton() {
        WebElement displayLevelsButton = getDifferentialDisplayLevelsButton();
        displayLevelsButton.click();
    }

    public WebElement getDifferentialDisplayLevelsButton() {
        new FluentWait<>(driver)
                .withTimeout(4, TimeUnit.MINUTES)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#diffProfileBody #display-levels")));

        return driver.findElement(By.cssSelector("#diffProfileBody #display-levels"));
    }



}

