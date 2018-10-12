package uk.ac.ebi.atlas.model.resource;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.nio.file.Path;
import java.text.MessageFormat;

public abstract class XmlFile<T> extends AtlasResource<T> {
    public XmlFile(Path parentDirectory, String template, String... args) {
        super(parentDirectory.resolve(MessageFormat.format(template, (Object[]) args)));
    }

    public static class ReadOnly extends XmlFile<XmlReader> {
        public ReadOnly(Path parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public XmlReader get() {
            PropertiesBuilderParameters builderParams = new Parameters().properties().setFile(path.toFile());

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
}
