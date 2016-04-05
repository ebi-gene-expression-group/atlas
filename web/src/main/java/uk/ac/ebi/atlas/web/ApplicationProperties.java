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

package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.model.AnatomogramType;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Named("applicationProperties")
@Scope("singleton")
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

    public String getAnatomogramFileName(String species, AnatomogramType anatomogramType) {
        String key = "organism.anatomogram." + species.toLowerCase();
        String ending = "";
        if(anatomogramType.equals(AnatomogramType.MALE)) {
            ending = ".male";
        } else if (anatomogramType.equals(AnatomogramType.FEMALE)) {
            ending = ".female";
        } else if (anatomogramType.equals(AnatomogramType.BRAIN)) {
            ending = ".brain";
        }

        return configurationProperties.getProperty(key + ending);
    }

    public Map<String, ?> getAnatomogramProperties(String species) {
        Map<String, Object> result = new HashMap<>();
        String maleAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.MALE);
        result.put("maleAnatomogramFile", getAnatomogramFileName(species, AnatomogramType.MALE));

        String femaleAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.FEMALE);
        result.put("femaleAnatomogramFile", femaleAnatomogramFileName);

        String brainAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.BRAIN);
        result.put("brainAnatomogramFile", brainAnatomogramFileName);

        result.put("hasAnatomogram", maleAnatomogramFileName != null || femaleAnatomogramFileName != null || brainAnatomogramFileName != null);

        return result;
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

    public String getArrayExpressRestURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.rest.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
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
                .join(new String[]{uri + TSV_FILE_EXTENSION, queryString}).toString();
    }

    public String buildDownloadURLForWidget(HttpServletRequest request, String experimentAccession) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        return Joiner.on("?").skipNulls()
                .join(new String[]{"/experiments/" + experimentAccession + TSV_FILE_EXTENSION, queryString}).toString();
    }

    // eg: "red,green,blue"
    public <T> String encodeMultiValues(Iterable<T> iterable) {
        return Joiner.on(",").join(iterable);
    }

    /*
    <c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="protocol" value="${pageContext.request.scheme}://"/>
<c:set var="atlasHost" value="${pageContext.request.serverName.concat(serverPort)}"/>
     */
    public String buildAtlasHostURL(HttpServletRequest request){
        String serverPort = request.getServerPort() ==80? "" : ":".concat(new Integer(request.getServerPort()).toString());
        String atlasHost = request.getServerName().concat(serverPort);
        return request.getScheme()+"://" + atlasHost;
    }

}
