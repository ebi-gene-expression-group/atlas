package uk.ac.ebi.atlas.commons.readers;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;

import javax.inject.Named;
import java.nio.file.Path;

@Named
public class XmlReaderFactory {

    public XmlReader create(Path xmlFilePath, boolean splitOnComma) {
        PropertiesBuilderParameters builderParams = new Parameters().properties().setFile(xmlFilePath.toFile());
        if (splitOnComma) {
            builderParams.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        }

        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<>(XMLConfiguration.class).configure(builderParams);

        try {
            XMLConfiguration xmlConfiguration = builder.getConfiguration();
            xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());
            return new XmlReader(xmlConfiguration);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
