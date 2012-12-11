/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import com.thoughtworks.selenium.condition.Text;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeatmapTableWithSearchFormAndBarChartPage extends HeatmapTableWithSearchFormPage{

    private static final String XPATH_LAST_YAXIS_TICK = "//div[@class='yAxis y1Axis']/div[@class='tickLabel'][last()]";
    private static final String XPATH_LAST_XAXIS_TICK = "//div[@class='xAxis x1Axis']/div[@class='tickLabel'][last()]";

    @FindBy(id = "geneCount")
    private WebElement geneCountDiv;

    @FindBy(className = "x1Axis")
    private WebElement xAxisDiv;

    @FindBy(className = "y1Axis")
    private WebElement yAxisDiv;

    @FindBy(id = "display-chart")
    private WebElement displayChartButton;

    public HeatmapTableWithSearchFormAndBarChartPage(WebDriver driver){
        super(driver);
    }

    public HeatmapTableWithSearchFormAndBarChartPage(WebDriver driver, String parameters){
        super(driver, parameters);
    }

    public void clickDisplayChartButton(){
        displayChartButton.click();
        waitForAjaxDataToLoad();
    }

    private void waitForAjaxDataToLoad(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                String lastYTick = webDriver.findElement(By.xpath(XPATH_LAST_YAXIS_TICK)).getText();
                String lastXTick = webDriver.findElement(By.xpath(XPATH_LAST_XAXIS_TICK)).getText();
                return !lastXTick.isEmpty() && !lastYTick.isEmpty();
            }
        });
    }

    public String getMaxXAxisValue(){
        return getLastTickValue(xAxisDiv);
    }

    public String getMaxYAxisValue(){
        return getLastTickValue(yAxisDiv);
    }

    public String getXAxisValue(int index){
        return getTickValue(xAxisDiv, index);
    }

    public String getYAxisValue(int index){
        return getTickValue(yAxisDiv, index);
    }

    private String getTickValue(WebElement axisDiv, int index){
        List<WebElement> webElements = getTicks(axisDiv);
        return webElements.get(index).getText();
    }

    private String getLastTickValue(WebElement axisDiv){
        List<WebElement> webElements = getTicks(axisDiv);
        return webElements.get(webElements.size()-1).getText();
    }

    private List<WebElement> getTicks(WebElement axisDiv){
        return axisDiv.findElements(By.className("tickLabel"));
    }

}
