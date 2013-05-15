package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named("bioEntityCardProperties")
@Scope("singleton")
public class BioentityPageProperties {
    public static final String PROPERTY_PREFIX = "property.";
    public static final String LINK_PREFIX = "link.";

    private Properties properties;

    @Inject
    public BioentityPageProperties(@Named("genecard") Properties geneCardProperties) {
        this.properties = geneCardProperties;
    }

    public String getPropertyName(String propertyType) {
        String name = properties.getProperty(PROPERTY_PREFIX + propertyType);
        return name == null? propertyType:name;
    }

    public String getLink(String propertyType) {
        return properties.getProperty(LINK_PREFIX + propertyType);
    }
}
