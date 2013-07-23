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

package uk.ac.ebi.atlas.acceptance.selenium.fixture.internal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.DriverFactory;

import java.net.MalformedURLException;
import java.net.URL;


/*
In order to use this fixture you must have PhantomJS installed on your machine and you must start it:
as a webrunner server with the following command line:
phantomjs --webdriver=4444
 */
public class PhantomJSDriverFactory implements DriverFactory {

    private static final String SELENIUM_SERVER_URL = "http://127.0.0.1:4444/wd/hub";

    protected WebDriver driver;


    @Override
    public WebDriver create() {
        return initializeDriver();
    }

    private WebDriver initializeDriver() {

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setJavascriptEnabled(true);
            capabilities.setBrowserName("firefox");

            return new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capabilities);
        } catch (MalformedURLException e) {

            e.printStackTrace();
            throw new IllegalStateException(e);

        }

    }


}
