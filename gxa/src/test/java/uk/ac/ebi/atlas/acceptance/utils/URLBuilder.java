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

    public String buildURL() {
        return buildURL(null);
    }

    public String buildURL(String httpParameters) {
        String hostname = System.getProperty(SELENIUM_TEST_HOST_PROPERTY_KEY);
        if (StringUtils.isBlank(hostname)) {
            hostname = getLocalHostAddress();
        }

        String portNumber = System.getProperty(SELENIUM_TEST_PORTNUMBER_PROPERTY_KEY);
        if (StringUtils.isBlank(portNumber)) {
            portNumber = "8080";
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