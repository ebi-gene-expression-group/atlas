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

package uk.ac.ebi.atlas.acceptance.selenium.utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public abstract class SinglePageSeleniumFixture {

    private static final String SELENIUM_SERVER_URL = "http://ma-selenium:4444/wd/hub";

    //Uncomment this if you want to use local firefox browser
    //protected FirefoxDriver driver;

    //comment this if you want to use local firefox browser
    protected WebDriver driver;


    @Before
    public void bootstrapTest() {
        initializeFirefoxDriver();
        getStartingPage();
    }

    @After
    public void closeDriver() {
        driver.quit();
    }

    private void initializeFirefoxDriver() {

        //uncomment this if you want to use local firefox driver
        this.driver = new FirefoxDriver();


        //comment this if you want to use local firefox browser
        /*
        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setJavascriptEnabled(true);
            capabilities.setBrowserName("firefox");

            this.driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capabilities);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }
        */
        //


    }

    protected abstract void getStartingPage();

}
