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

package uk.ac.ebi.atlas.model;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Named
public class ConfigurationTrader {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationTrader.class);

    @Value("#{configuration['experiment.factors.path.template']}")
    private String baselineConfigurationPathTemplate;

    @Value("#{configuration['diff.experiment.configuration.path.template']}")
    private String differentialConfigurationPathTemplate;

    public BaselineExperimentConfiguration getFactorsConfiguration(String experimentAccession) {
        XMLConfiguration xmlConfiguration = getXmlConfiguration(baselineConfigurationPathTemplate, experimentAccession);
        return new BaselineExperimentConfiguration(xmlConfiguration);
    }

    public DifferentialExperimentConfiguration getDifferentialExperimentConfiguration(String experimentAccession) {
        return getDifferentialExperimentConfiguration(experimentAccession, false);
    }

    private DifferentialExperimentConfiguration getDifferentialExperimentConfiguration(String experimentAccession, boolean isMicroarray) {

        XMLConfiguration xmlConfiguration = getXmlConfiguration(differentialConfigurationPathTemplate, experimentAccession);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());
        Document document = parseConfigurationXml(experimentAccession);
        if (isMicroarray) {
            return new MicroarrayExperimentConfiguration(xmlConfiguration, document);
        }
        return new DifferentialExperimentConfiguration(xmlConfiguration, document);
    }


    public MicroarrayExperimentConfiguration getMicroarrayExperimentConfiguration(String experimentAccession) {
        return (MicroarrayExperimentConfiguration) getDifferentialExperimentConfiguration(experimentAccession, true);
    }

    private Document parseConfigurationXml(String experimentAccession) {
        Path path = Paths.get(MessageFormat.format(differentialConfigurationPathTemplate, experimentAccession));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(Files.newInputStream(path));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Problem parsing configuration file: " + path.toString(), e);
        }
    }

    private XMLConfiguration getXmlConfiguration(String pathTemplate, String experimentAccession) {
        Path path = Paths.get(MessageFormat.format(pathTemplate, experimentAccession));
        try {
            return new XMLConfiguration(path.toFile());
        } catch (ConfigurationException cex) {
            LOGGER.error(cex.getMessage(), cex);
            throw new IllegalStateException("Cannot read configuration from path " + path.toString(), cex);
        }
    }

}