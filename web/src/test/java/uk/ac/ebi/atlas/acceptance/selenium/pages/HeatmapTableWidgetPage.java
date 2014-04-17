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

public class HeatmapTableWidgetPage extends HeatmapTableWithTranscriptBreakdownPage {
    private static final String PAGE_LOCATION = "/gxa/widgets/heatmap/protein";

    @FindBy(id = "download-profiles-link")
    private WebElement downloadProfiles;

    public HeatmapTableWidgetPage(WebDriver driver, String httpParameters) {
        super(driver, null, httpParameters);
    }

    public String downloadProfilesLink() {
        return downloadProfiles.getAttribute("href");
    }


    @Override
    protected String getPageURI() {
        return PAGE_LOCATION;
    }
}