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

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeatmapTableWithSearchFormAndBarChartPage extends HeatmapTableWithSearchFormPage{

    @FindBy(id = "geneCount")
    private WebElement geneCountDiv;

    @FindBy(className = "x1Axis")
    private WebElement xAxisDiv;

    @FindBy(className = "y1Axis")
    private WebElement yAxisDiv;

    @FindBy(id = "display-chart")
    private WebElement displayChartButton;

    @FindBy(className = "legendLabel")
    private WebElement legendLabel;

    public HeatmapTableWithSearchFormAndBarChartPage(WebDriver driver, String experimentAccession){
        super(driver, experimentAccession);
    }

    public HeatmapTableWithSearchFormAndBarChartPage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public void clickDisplayChartButton(){
        displayChartButton.click();
        waitForAjaxDataToLoad();
    }

    public String getLegendLabel() {
        return legendLabel.getText();
    }

    private void waitForAjaxDataToLoad(){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                String lastYTick = webDriver.findElement(By.className("y1Axis")).findElement(By.className("tickLabel")).getText();
                String lastXTick = webDriver.findElement(By.className("x1Axis")).findElement(By.className("tickLabel")).getText();
                return !lastXTick.isEmpty() && !lastYTick.isEmpty();
            }
        });
    }

    public String getMaxXAxisValue(){
        return getLastXTickValue(xAxisDiv);
    }

    public String getMaxYAxisValue(){
        return getLastYTickValue(yAxisDiv);
    }

    public String getXAxisValue(int index){
        return getXTickValue(xAxisDiv, index);
    }

    public String getYAxisValue(int index){
        return getYTickValue(yAxisDiv, index);
    }

    private String getXTickValue(WebElement axisDiv, int index){
        List<WebElement> webElements = getXTicks(axisDiv);
        return webElements.get(index).getText();
    }

    private String getYTickValue(WebElement axisDiv, int index){
        List<WebElement> webElements = getYTicks(axisDiv);
        return webElements.get(index).getText();
    }

    private String getLastXTickValue(WebElement axisDiv){
        List<WebElement> webElements = getXTicks(axisDiv);
        return webElements.get(webElements.size()-1).getText();
    }

    private String getLastYTickValue(WebElement axisDiv){
        List<WebElement> webElements = getYTicks(axisDiv);
        return webElements.get(webElements.size()-1).getText();
    }

    private List<WebElement> getXTicks(WebElement axisDiv){
        List<WebElement> ticks = axisDiv.findElements(By.className("tickLabel"));

        //This is a patch because in some browser the X axis tick divs are ordered randomly,
        //but we can use the distance from X=0 to reorder the tick values
        Comparator distanceFromZeroComparator = Ordering.natural().onResultOf(new Function<WebElement, Integer>(){
            @Override
            public Integer apply(org.openqa.selenium.WebElement webElement) {
                String distanceFromZero = StringUtils.substringBefore(webElement.getCssValue("left"), "px");
                return Integer.parseInt(distanceFromZero);
            }
        });
        Collections.sort(ticks, distanceFromZeroComparator);

        return ticks;
    }

    private List<WebElement> getYTicks(WebElement axisDiv){
        Wait<WebDriver> wait = new WebDriverWait(driver, 4L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tickLabel")));
        List<WebElement> ticks = axisDiv.findElements(By.className("tickLabel"));
        return ticks;
    }

}
