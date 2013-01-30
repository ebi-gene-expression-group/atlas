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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod26284;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.Arrays;
import java.util.List;

public class Geod26284HeatmapTablePage extends HeatmapTablePage {

    private final Actions builder;

    @FindBy(xpath = "//ul[@id = 'filterBy']/li")
    private WebElement filterByMenu;

    public Geod26284HeatmapTablePage(WebDriver driver) {
        super(driver);
        builder = new Actions(driver);
    }

    public Geod26284HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
        builder = new Actions(driver);
    }

    protected String getPageURI() {
        return "/gxa/experiments/E-GEOD-26284";
    }

    protected List<WebElement> moveMouseOntoMenuElement(final WebElement menuElement) {

        WebElement linkElement = menuElement.findElement(By.xpath("a"));
        Action mouseMove = builder.moveToElement(linkElement).build();
        mouseMove.perform();

        System.out.println(menuElement + " " + menuElement.getTagName() + " " + menuElement.getText());
        for (WebElement element : menuElement.findElements(By.xpath("ul/li"))) {
            System.out.println(element + " " + element.getTagName() + " " + element.getText());
        }

        // wait until jQuery has rendered the child menu
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                // this is the text of the first child
                return !menuElement.findElement(By.xpath("ul/li")).getText().equals("");
            }
        });

        for (WebElement element : menuElement.findElements(By.xpath("ul/li"))) {
            System.out.println(element + " " + element.getTagName() + " " + element.getText());
        }

        System.out.println();

        // return all child elements in current menu element
        return menuElement.findElements(By.xpath("ul/li"));
    }

    protected WebElement recurseThroughMenu(WebElement menuElement, int[] indices) {

        int index = indices[0];
        WebElement childElement = moveMouseOntoMenuElement(menuElement).get(index);

        // this is the base case
        if (indices.length == 1) {
            return childElement;
        }

        // this is the recursion case
        int[] newIndices = Arrays.copyOfRange(indices, 1, indices.length);
        return recurseThroughMenu(childElement, newIndices);
    }

    public String getFilterByMenuTopText() {
        return filterByMenu.getText();
    }

    public String getFilterByMenuText(int... indicies) {

        WebElement element = recurseThroughMenu(filterByMenu, indicies);
        System.out.println(element + " " + element.getTagName() + " " + element.getText());

        return element.getText();
    }
}