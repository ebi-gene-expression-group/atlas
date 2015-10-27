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

package uk.ac.ebi.atlas.trader;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Named
public class ConfigurationTrader {

    private static final Logger LOGGER = LogManager.getLogger(ConfigurationTrader.class);

    @Value("#{configuration['experiment.factors.path.template']}")
    private String baselineFactorsConfigurationPathTemplate;

    @Value("#{configuration['experiment.configuration.path.template']}")
    private String configurationPathTemplate;

    public BaselineExperimentConfiguration getFactorsConfiguration(String experimentAccession) {
        XMLConfiguration xmlConfiguration = getXmlConfiguration(baselineFactorsConfigurationPathTemplate, experimentAccession, true);
        return new BaselineExperimentConfiguration(xmlConfiguration);
    }

    public ExperimentConfiguration getExperimentConfiguration(String experimentAccession) {
        return getExperimentConfiguration(experimentAccession, false);
    }

    public MicroarrayExperimentConfiguration getMicroarrayExperimentConfiguration(String experimentAccession) {
        return (MicroarrayExperimentConfiguration) getExperimentConfiguration(experimentAccession, true);
    }

    private ExperimentConfiguration getExperimentConfiguration(String experimentAccession, boolean isMicroarray) {

        XMLConfiguration xmlConfiguration = getXmlConfiguration(configurationPathTemplate, experimentAccession, false);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());
        Document document = parseConfigurationXml(experimentAccession);
        if (isMicroarray) {
            return new MicroarrayExperimentConfiguration(xmlConfiguration, document);
        }
        return new ExperimentConfiguration(xmlConfiguration, document);
    }


    private Document parseConfigurationXml(String experimentAccession) {
        Path path = Paths.get(MessageFormat.format(configurationPathTemplate, experimentAccession));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(Files.newInputStream(path));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Problem parsing configuration file: " + path.toString(), e);
        }
    }

    private XMLConfiguration getXmlConfiguration(String pathTemplate, String experimentAccession, boolean splitOnComma) {
        Path path = Paths.get(MessageFormat.format(pathTemplate, experimentAccession));

        File file = path.toFile();

        if (!file.exists()) {
            throw new IllegalStateException("Configuration file " + path.toString() + " does not exist");
        }

        try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration();
            if (!splitOnComma) {
                xmlConfiguration.setDelimiterParsingDisabled(true);
            }
            xmlConfiguration.load(path.toFile());
            return xmlConfiguration;
        } catch (ConfigurationException cex) {
            LOGGER.error(cex.getMessage(), cex);
            throw new IllegalStateException("Cannot read configuration from path " + path.toString(), cex);
        }

    }

}