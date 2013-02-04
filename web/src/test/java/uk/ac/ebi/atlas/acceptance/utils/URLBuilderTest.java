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

package uk.ac.ebi.atlas.acceptance.utils;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class URLBuilderTest {

    private static final String GXA_EXPERIMENT = "/gxa/experiment";

    URLBuilder subject = new URLBuilder(GXA_EXPERIMENT);

    String currentSeleniumHost;

    String currentSeleniumPort;

    @Before
    public void setup() {
        currentSeleniumHost = System.getProperty(URLBuilder.SELENIUM_TEST_HOST_PROPERTY_KEY);
        currentSeleniumPort = System.getProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
    }

    @After
    public void shutdown() {
        if (currentSeleniumHost != null) {
            System.setProperty(URLBuilder.SELENIUM_TEST_HOST_PROPERTY_KEY, currentSeleniumHost);
        } else {
            System.clearProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        }
        if (currentSeleniumPort != null) {
            System.setProperty(URLBuilder.SELENIUM_TEST_HOST_PROPERTY_KEY, currentSeleniumPort);
        } else {
            System.clearProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        }
    }

    @Test
    public void testEmptyParameters() {

        // given
        System.setProperty(URLBuilder.SELENIUM_TEST_HOST_PROPERTY_KEY, "");
        System.setProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY, "9090");
        String host = subject.getLocalHostAddress();

        // then
        assertThat(subject.buildURL(""), is("http://" + host + ":9090" + GXA_EXPERIMENT));
    }

    @Test
    public void testSetSeleniumHost() {

        // given
        System.setProperty(URLBuilder.SELENIUM_TEST_HOST_PROPERTY_KEY, "bla");
        System.setProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY, "");

        // then
        assertThat(subject.buildURL(""), is("http://bla:9090" + GXA_EXPERIMENT));
    }

    @Test
    public void testSetSeleniumPortNumber() {

        // given
        String host = StringUtils.isBlank(currentSeleniumHost) ? subject.getLocalHostAddress() : currentSeleniumHost;
        System.setProperty(URLBuilder.SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY, "1234");

        // then
        assertThat(subject.buildURL(""), is("http://" + host + ":1234" + GXA_EXPERIMENT));
    }

    @Test
    public void testHttpParameters() {

        // given
        String host = StringUtils.isBlank(currentSeleniumHost) ? subject.getLocalHostAddress() : currentSeleniumHost;
        String port = StringUtils.isBlank(currentSeleniumPort) ? "9090" : currentSeleniumPort;
        String parameters = "geneQuery=protein_coding&filter=abc&cutoff=10";

        // then
        assertThat(subject.buildURL(parameters), is("http://" + host + ":" + port + GXA_EXPERIMENT + "?" + parameters));
    }

}