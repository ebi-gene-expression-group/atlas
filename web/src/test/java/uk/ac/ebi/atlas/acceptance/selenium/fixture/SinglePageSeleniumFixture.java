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

package uk.ac.ebi.atlas.acceptance.selenium.fixture;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.PhantomJSDriverFactory;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.RemoteDriverFactory;


public abstract class SinglePageSeleniumFixture {

    private static final String SELENIUM_SERVER_URL = "http://ma-selenium:4444/wd/hub";

    protected WebDriver driver;


    @Before
    public void initDriver() {
        // change the factory to FirefoxDriverFactory or PhantomJSDriverFactory
        // if you want to use a local browser
        driver = new RemoteDriverFactory().create();
        getStartingPage();
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    protected abstract void getStartingPage();

}
