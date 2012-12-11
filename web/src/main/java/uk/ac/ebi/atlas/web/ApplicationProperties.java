/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Properties;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private Properties configurationProperties;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    public String getAnatomogramFileName(String specie, boolean isMale) {
        String key = "organism.anatomogram." + specie.toLowerCase();
        String fileName = configurationProperties.getProperty(key + (isMale ? ".male" : ".female"));
        return fileName != null ? fileName : configurationProperties.getProperty(key);
    }

    public String getArrayExpressURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
    }

    public String getArrayExpressRestURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.rest.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
    }

    public String getAnalisysMethodCsvFilePath(String experimentAccession) {
        return MessageFormat.format(configurationProperties.getProperty("experiment.analysis-method.path.template"), experimentAccession);
    }

    public String getExperimentDesignCsvFilePath(String experimentAccession) {
        return MessageFormat.format(configurationProperties.getProperty("experiment.experiment-design.path.template"), experimentAccession);
    }

    public String[] getExperimentIdentifiers() {
        return configurationProperties.getProperty("experiment.identifiers").trim().split(",");
    }

    public String getFeedbackEmail() {
        return configurationProperties.getProperty("feedback.email");
    }

    public Properties getMailServerProperties() {
        Properties mailServerProperties = new Properties();
        mailServerProperties.put("mail.smtp.host", configurationProperties.getProperty("mail.smtp.host"));
        mailServerProperties.put("mail.smtp.port", configurationProperties.getProperty("mail.smtp.port"));
        return mailServerProperties;
    }
}
