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

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class URLBuilder {

    public static final String SELENIUM_TEST_HOST_PROPERTY_KEY = "selenium.test.host";

    public static final String SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY = "selenium.test.portnumber";

    private final String pageURI;

    public URLBuilder(String pageURI) {
        this.pageURI = pageURI;
    }

    public String buildURL(String httpParameters) {
        String hostname = System.getProperty(SELENIUM_TEST_HOST_PROPERTY_KEY);
        if (StringUtils.isBlank(hostname)) {
            hostname = getLocalHostAddress();
        }

        String portNumber = System.getProperty(SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        if (StringUtils.isBlank(portNumber)) {
            portNumber = "9090";
        }
        StringBuilder stringBuilder = new StringBuilder("http://")
                .append(hostname)
                .append(":")
                .append(portNumber)
                .append(pageURI);
        if (!Strings.isNullOrEmpty(httpParameters)) {
            stringBuilder.append("?").append(httpParameters);
        }
        System.out.println("<buildURL> Running on page: " + stringBuilder.toString());
        return stringBuilder.toString();

    }

    public String getLocalHostAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public String getPageURI() {
        return pageURI;
    }
}