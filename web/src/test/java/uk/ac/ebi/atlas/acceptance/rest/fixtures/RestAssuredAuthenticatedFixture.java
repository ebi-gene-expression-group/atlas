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

package uk.ac.ebi.atlas.acceptance.rest.fixtures;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.BasicAuthScheme;
import com.jayway.restassured.builder.RequestSpecBuilder;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

public class RestAssuredAuthenticatedFixture {

    public static final String SELENIUM_TEST_HOST_PROPERTY_KEY = "selenium.test.host";

    public static final String SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY = "selenium.test.portnumber";

    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    @BeforeClass
    public static void initRestAssured(){
        BasicAuthScheme authScheme = new BasicAuthScheme();
        authScheme.setUserName(USERNAME);
        authScheme.setPassword(PASSWORD);

        String hostname = System.getProperty(SELENIUM_TEST_HOST_PROPERTY_KEY);
        if (StringUtils.isNotBlank(hostname)) {
            RestAssured.baseURI = "http://"+hostname;
        }
        System.out.println("<initRestAssured> RestAssured.baseURI = " + RestAssured.baseURI);


        RestAssured.basePath = "/gxa/admin";

        String portNumber = System.getProperty(SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        if (StringUtils.isBlank(portNumber)) {
            portNumber = "9090";
        }

        RestAssured.port = new Integer(portNumber);

        RestAssured.requestSpecification = new RequestSpecBuilder().setAuth(authScheme).build();

    }



}
