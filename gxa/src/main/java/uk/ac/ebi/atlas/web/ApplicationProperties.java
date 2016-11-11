package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

@Named("applicationProperties")
public class ApplicationProperties {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private Properties speciesToExperimentProperties;

    private Properties configurationProperties;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties,
                          @Named("speciesToExperimentPropertyFile") Properties speciesToExperimentProperties,
                          ArrayDesignTrader arrayDesignTrader) {
        this.speciesToExperimentProperties = speciesToExperimentProperties;
        this.configurationProperties = configurationProperties;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    //This is invoked from jsp el
    public String getArrayExpressURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
    }

    //This is invoked from jsp el
    public String getArrayExpressArrayURL(String arrayAccession) {
        String arrayDesign = arrayDesignTrader.getArrayDesignAccession(arrayAccession);  //getKey from arrayDesignMap
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.arrays.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, arrayDesign);
    }

    //This is invoked from jsp el
    public String getPubMedURL(String pubMedId) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.pubmed.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, pubMedId);
    }

    public String getFeedbackEmailAddress() {
        return configurationProperties.getProperty("feedback.email");
    }

    public String getResourcesVersion() {
        return configurationProperties.getProperty("resources.version");
    }

    public Set<String> getArrayDesignAccessions() {
        return getStringValues("arraydesign.accessions");
    }

    private Set<String> getStringValues(String propertyKey) {
        return Sets.newHashSet(configurationProperties.getProperty(propertyKey).trim().split(","));
    }

    public String getBaselineReferenceExperimentAccession(String species) {
        return speciesToExperimentProperties.getProperty(species.toLowerCase().replace(" ", "_"));
    }

    public String buildServerURL(HttpServletRequest request) throws MalformedURLException {
        String spec = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        if (request.isSecure()) {
            spec = "https://" + spec;
        } else {
            spec = "http://" + spec;
        }

        URL url = new URL(spec);
        return url.toExternalForm();
    }

    public String buildDownloadURL(HttpServletRequest request) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String uri = StringUtils.remove(requestUri, request.getContextPath());

        return Joiner.on("?").skipNulls()
                .join(new String[]{uri.replace("/json/experiments","/experiments") + TSV_FILE_EXTENSION, queryString});
    }

    public String buildDownloadURLForWidget(HttpServletRequest request, String experimentAccession) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        return Joiner.on("?").skipNulls()
                .join(new String[]{"/experiments/" + experimentAccession + TSV_FILE_EXTENSION, queryString});
    }

    /* Used in bioentities.jsp and bioentities-search-results.jsp */
    public String urlParamEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    /*
    <c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
    <c:set var="protocol" value="${pageContext.request.scheme}://"/>
    <c:set var="atlasHost" value="${pageContext.request.serverName.concat(serverPort)}"/>
     */
    public String buildAtlasHostURL(HttpServletRequest request){
        String serverPort = request.getServerPort() == 80 ? "" : ":".concat(Integer.toString(request.getServerPort()));
        String atlasHost = request.getServerName().concat(serverPort);
        return (atlasHost.contains("ebi.ac.uk")? "https": request.getScheme())+"://" + atlasHost;
    }

}
