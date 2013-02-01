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

package uk.ac.ebi.atlas.web.controllers.rest.pages;

import com.google.common.base.Strings;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.jayway.restassured.RestAssured.get;

public abstract class RestPage {

    private String pageURL;

    RestPage(String httpParameters) {
        pageURL = buildURL(httpParameters);
    }

    String buildURL(String httpParameters) {
        String hostname = System.getProperty("selenium.test.host");
        if (StringUtils.isBlank(hostname)) {
            System.out.println("selenium.test.host is null, so tests will be executed against local machine");
            hostname = getLocalHostAddress();
        }

        System.out.println("running tests on local host address: " + hostname);

        String portNumber = System.getProperty("selenium.test.portnumber");
        if (portNumber == null) {
            portNumber = "9090";
        }
        StringBuilder stringBuilder = new StringBuilder("http://")
                .append(hostname)
                .append(":")
                .append(portNumber)
                .append(getPageURI());
        if (!Strings.isNullOrEmpty(httpParameters)) {
            stringBuilder.append("?").append(httpParameters);
        }
        System.out.println("<buildURL> Running on page: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private String getLocalHostAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public Response getResponse() {
        return get(pageURL);
    }

    public ResponseBody getResponseBody() {
        return get(pageURL).getBody();
    }

    protected abstract String getPageURI();
}