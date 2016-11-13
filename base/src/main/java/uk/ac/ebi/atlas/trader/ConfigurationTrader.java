package uk.ac.ebi.atlas.trader;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationTrader.class);

    @Value("#{configuration['experiment.factors.path.template']}")
    private String baselineFactorsConfigurationPathTemplate;

    @Value("#{configuration['experiment.configuration.path.template']}")
    private String configurationPathTemplate;

    public BaselineExperimentConfiguration getBaselineFactorsConfiguration(String experimentAccession) {
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
        Path pathToConfigurationFile = Paths.get(MessageFormat.format(configurationPathTemplate, experimentAccession));
        XMLConfiguration xmlConfiguration = getXmlConfiguration(configurationPathTemplate, experimentAccession, false);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());
        Document document = parseConfigurationXml(experimentAccession);
        if (isMicroarray) {
            return new MicroarrayExperimentConfiguration(xmlConfiguration, document, pathToConfigurationFile);
        }
        return new ExperimentConfiguration(xmlConfiguration, document, pathToConfigurationFile);
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
            PropertiesBuilderParameters builderParams =
                    new Parameters().properties()
                            .setFile(path.toFile());
            if (splitOnComma) {
                builderParams.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
            }

            FileBasedConfigurationBuilder<XMLConfiguration> builder =
                    new FileBasedConfigurationBuilder<>(XMLConfiguration.class).configure(builderParams);
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot read configuration from path " + path.toString(), e);
        }

    }

}