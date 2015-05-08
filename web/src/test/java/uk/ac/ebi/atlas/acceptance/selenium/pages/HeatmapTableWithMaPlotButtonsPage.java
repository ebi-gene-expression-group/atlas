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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeatmapTableWithMaPlotButtonsPage extends HeatmapTablePage {

    @FindBy(className = "ma-button")
    private List<WebElement> maPlotButtons;

    public HeatmapTableWithMaPlotButtonsPage(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithMaPlotButtonsPage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public HeatmapTableWithMaPlotButtonsPage clickMaPlotButton(int zeroBasedButtonIndex) {
        maPlotButtons.get(zeroBasedButtonIndex).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return driver.findElement(By.className("fancybox-image")) != null;
            }
        });
        return this;
    }

    public String getMaPlotImageAnchor(){

        WebElement maPlotImageElement = driver.findElement(By.className("fancybox-image"));
        return maPlotImageElement.getAttribute("src");
    }
}