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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeatmapTableWithTranscriptBreakdownPage extends HeatmapTablePage {

    @FindBy(id = "heatmap-table")
    private WebElement heatmapTable;

    public HeatmapTableWithTranscriptBreakdownPage(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithTranscriptBreakdownPage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public HeatmapTableWithTranscriptBreakdownPage clickOnCell(int profileIndex, int expressionIndex) {
        getGeneProfileCell(profileIndex, expressionIndex).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return driver.findElement(By.id("transcript-breakdown-title")) != null;
            }
        });
        return this;
    }

    public String getTranscriptBreakdownTitle() {

        WebElement breakdownTitleElement = driver.findElement(By.id("transcript-breakdown-title"));
        return breakdownTitleElement.getText();
    }

    public List<String> getTranscriptBreakdownLegendLabels() {

        List<String> results = Lists.newArrayList();

        WebElement transcriptPie = driver.findElement(By.id("transcripts-pie"));
        WebElement legend = transcriptPie.findElement(By.className("legend"));
        List<WebElement> elements = legend.findElements(By.className("legendLabel"));
        for (WebElement element : elements) {
            try{
                results.add(element.findElement(By.xpath("a")).getText());
            }catch(NoSuchElementException e){
                results.add(element.getText());
            }
        }
        return results;
    }

}