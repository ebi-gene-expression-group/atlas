
package uk.ac.ebi.atlas.solr.admin.index;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.BioentityProperty;

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
    private String bioentityType;
    private List<String> propertyNames;
    private List<String> propertyValues;
    private String species;

    private boolean addIdentifierAsProperty = true;

    public BioentityPropertiesBuilder forBioentityType(String bioentityType){
        this.bioentityType = bioentityType;
        return this;
    }

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

    public BioentityPropertiesBuilder withIdentifierAsProperty(boolean withIdentifierAsProperty){
        this.addIdentifierAsProperty = withIdentifierAsProperty;
        return this;
    }

    public List<BioentityProperty> build(){

        checkState(CollectionUtils.isNotEmpty(propertyNames)
                && StringUtils.isNotBlank(bioentityType)
                && StringUtils.isNotBlank(species)
                && StringUtils.isNotBlank(bioentityIdentifier)
                && CollectionUtils.isNotEmpty(propertyValues));


        if (isDesignElementProperty()) {
            return buildDesignMappingProperty();
        }
        return buildBioentityProperties();
    }

    private List<BioentityProperty> buildDesignMappingProperty() {
        List designMappingProperty = Lists.newArrayList();
        String designMapping =  propertyValues.get(0);
        if (StringUtils.isNotBlank(designMapping)){
            designMappingProperty.add(new BioentityProperty(bioentityIdentifier, bioentityType,
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

                    //Split the input from ensembl into separate PO property when the accession starts with PO:
                    if(value.startsWith("PO:") && propertyNames.get(i).equals("go")){
                        propertyName = "po";
                    }
                    BioentityProperty bioentityProperty =
                            new BioentityProperty(bioentityIdentifier, bioentityType,
                                    species, propertyName, value);
                    bioentityProperties.add(bioentityProperty);
                }
            }
        }

        if (addIdentifierAsProperty) {
            bioentityProperties.add(new BioentityProperty(bioentityIdentifier, bioentityType, species, bioentityType, bioentityIdentifier));
        }

        return bioentityProperties;
    }

    boolean isDesignElementProperty() {
        return DESIGN_ELEMENT_PROPERTY_NAME.equals(propertyNames.get(0));
    }

}
