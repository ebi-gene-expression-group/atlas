
package uk.ac.ebi.atlas.bioentity.properties;

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named("bioEntityCardProperties")
@Scope("singleton")
public class BioEntityCardProperties {
    public static final String PROPERTY_PREFIX = "property.";
    public static final String LINK_PREFIX = "link.";

    private Properties bioEntityCardProperties;

    @Inject
    public BioEntityCardProperties(@Named("bioEntityCardPropertyFile") Properties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    public String getPropertyName(String propertyType) {
        return bioEntityCardProperties.getProperty(PROPERTY_PREFIX + propertyType, propertyType);
    }

    public String getLinkTemplate(String propertyType) {
        return bioEntityCardProperties.getProperty(LINK_PREFIX + propertyType);
    }
}
