
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

        RestAssured.basePath = "/gxa/admin";

        String portNumber = System.getProperty(SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        if (StringUtils.isBlank(portNumber)) {
            portNumber = "8080";
        }

        RestAssured.port = new Integer(portNumber);

        RestAssured.requestSpecification = new RequestSpecBuilder().setAuth(authScheme).build();

        System.out.println(String.format("<initRestAssured> base = %s:%s%s", RestAssured.baseURI, RestAssured.port, RestAssured.basePath));

    }



}
