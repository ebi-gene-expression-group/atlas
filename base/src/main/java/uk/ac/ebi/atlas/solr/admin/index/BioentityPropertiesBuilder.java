package uk.ac.ebi.atlas.solr.admin.index;

import uk.ac.ebi.atlas.solr.BioentityProperty;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BioentityPropertiesBuilder {
    private static final String DESIGN_ELEMENT_PROPERTY_NAME = "design_element";
    private static final String SEPARATOR = "@@";
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile(SEPARATOR);

    private String bioentityIdentifier;
    private List<String> propertyNames;
    private List<String> propertyValues;
    private String species;

    public BioentityPropertiesBuilder forSpecies(String species){
        this.species = species;
        return this;
    }

    public BioentityPropertiesBuilder forPropertyNames(List<String> propertyNames){
        this.propertyNames = propertyNames;
        return this;
    }

    public BioentityPropertiesBuilder withBioentityIdentifier(String bioentityIdentifier){
        this.bioentityIdentifier = bioentityIdentifier;
        return this;
    }

    public BioentityPropertiesBuilder withPropertyValues(List<String> propertyValues){
        this.propertyValues = propertyValues;
        return this;
    }

    public List<BioentityProperty> build(){
        checkState(CollectionUtils.isNotEmpty(propertyNames)
                && StringUtils.isNotBlank(species)
                && StringUtils.isNotBlank(bioentityIdentifier)
                && CollectionUtils.isNotEmpty(propertyValues));

        if (isDesignElementProperty()) {
            return buildDesignMappingProperty();
        }
        return buildBioentityProperties();
    }

    private List<BioentityProperty> buildDesignMappingProperty() {
        List<BioentityProperty> designMappingProperty = Lists.newArrayList();
        String designMapping = propertyValues.get(0);
        if (StringUtils.isNotBlank(designMapping)) {
            designMappingProperty.add(new BioentityProperty(bioentityIdentifier,
                    species, DESIGN_ELEMENT_PROPERTY_NAME, propertyValues.get(0)));
        }
        return designMappingProperty;
    }

    private List<BioentityProperty> buildBioentityProperties() {
        List<BioentityProperty> bioentityProperties = Lists.newArrayList();
        for (int i = 0; i < propertyValues.size(); i++) {

            String [] values = SEPARATOR_PATTERN.split(propertyValues.get(i));
            for (String value: values){
                if (StringUtils.isNotBlank(value)){
                    String propertyName = propertyNames.get(i);

                    // TODO Sometimes PO: terms appear under go header (this seems to be a legacy thing that could go away)
//                    if(value.startsWith("PO:") && propertyNames.get(i).equals("go")){
//                        propertyName = "po";
//                    }

                    BioentityProperty bioentityProperty =
                            new BioentityProperty(bioentityIdentifier,
                                    species, propertyName, value);
                    bioentityProperties.add(bioentityProperty);
                }
            }
        }

        return bioentityProperties;
    }

    private boolean isDesignElementProperty() {
        return DESIGN_ELEMENT_PROPERTY_NAME.equals(propertyNames.get(0));
    }

}
