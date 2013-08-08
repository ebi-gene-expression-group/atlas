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
import com.google.common.collect.Sets;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private Properties speciesToExperimentProperties;

    private Properties configurationProperties;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties,
                          @Named("speciesToExperimentPropertyFile") Properties speciesToExperimentProperties) {
        this.speciesToExperimentProperties = speciesToExperimentProperties;
        this.configurationProperties = configurationProperties;
    }

    public String getAnatomogramFileName(String specie, boolean isMale) {
        String key = "organism.anatomogram." + specie.toLowerCase();
        String fileName = configurationProperties.getProperty(key + (isMale ? ".male" : ".female"));
        return fileName != null ? fileName : configurationProperties.getProperty(key);
    }

    //This is invoked from jsp el
    public String getArrayExpressURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
    }

    //This is invoked from jsp el
    public String getArrayExpressArrayURL(String arrayAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.arrays.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, arrayAccession);
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

    public Set<String> getTestCaseExperimentAccessions() {
        Set<String> testCaseExperimentAccessions = Sets.newHashSet();
        if (!StringUtils.isNullOrEmpty(configurationProperties.getProperty("integration.experiment.identifiers"))) {
            testCaseExperimentAccessions.addAll(getStringValues("integration.experiment.identifiers"));
        }
        return testCaseExperimentAccessions;
    }

    public Set<String> getArrayDesignAccessions() {
        return getStringValues("arraydesign.accessions");
    }

    private Set<String> getStringValues(String propertyKey) {
        return Sets.newHashSet(configurationProperties.getProperty(propertyKey).trim().split(","));
    }

    public String getExperimentAccessionBySpecies(String species) {
        return speciesToExperimentProperties.getProperty(species.replace(" ", "_"));
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
        return Joiner.on("?").skipNulls()
                .join(new String[]{request.getAttribute("javax.servlet.forward.request_uri") + TSV_FILE_EXTENSION, queryString}).toString();
    }

}
