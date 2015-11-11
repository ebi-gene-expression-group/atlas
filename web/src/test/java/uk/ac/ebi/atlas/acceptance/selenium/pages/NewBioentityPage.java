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
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBioentityPage extends HeatmapTableWidgetPage {

    static final String PAGE_LOCATION = "/gxa/new/";

    private String bioentityIdentifier;

    private String type;

    @FindBy(id = "gxaSearchHeaderSection")
    private WebElement searchSection;

    @FindBy(id = "gxaBioentityHeaderSection")
    private WebElement headerSection;

    @FindBy(id = "gxaBioentityTabsSection")
    private WebElement tabsSection;

    public NewBioentityPage(WebDriver driver) {
        super(driver, null);
    }

    public NewBioentityPage(WebDriver driver, String bioentityIdentifier, String type) {
        super(driver, null);
        this.bioentityIdentifier = bioentityIdentifier;
        this.type = type;
    }

    public NewBioentityPage(WebDriver driver, String bioentityIdentifier, String type, String httpParameters) {
        super(driver, null, httpParameters);
        this.bioentityIdentifier = bioentityIdentifier;
        this.type = type;
    }
}