package uk.ac.ebi.atlas.bioentity.properties;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named
public class BioEntityCardProperties {
    private static final String PROPERTY_PREFIX = "property.";
    private static final String LINK_PREFIX = "link.";

    private Properties bioEntityCardProperties;

    @Inject
    public BioEntityCardProperties(@Named("bioEntityCardPropertyFile") Properties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    public String getPropertyName(String propertyType) {
        return bioEntityCardProperties.getProperty(PROPERTY_PREFIX + propertyType, propertyType);
    }

    String getLinkTemplate(String propertyType) {
        return bioEntityCardProperties.getProperty(LINK_PREFIX + propertyType);
    }
}
