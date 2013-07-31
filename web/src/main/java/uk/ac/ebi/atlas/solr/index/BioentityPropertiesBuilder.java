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

package uk.ac.ebi.atlas.solr.index;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BioentityPropertiesBuilder {
    private static final String DESIGN_ELEMENT_PROPERTY_NAME = "design_element";

    private String bioentityIdentifier;
    private String bioentityType;
    private List<String> propertyNames;
    private List<String> propertyValues;
    private String species;

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

    public List<BioentityProperty> build(){

        checkState(CollectionUtils.isNotEmpty(propertyNames)
                && StringUtils.isNotBlank(bioentityType)
                && StringUtils.isNotBlank(species)
                && StringUtils.isNotBlank(bioentityIdentifier)
                && CollectionUtils.isNotEmpty(propertyValues));

        if (isDesignElementProperty()) {
            return Lists.newArrayList(new BioentityProperty(bioentityIdentifier, bioentityType,
                    species, DESIGN_ELEMENT_PROPERTY_NAME, propertyValues.get(0)));
        }
        return buildBioentityPropertiesForConcatenatedValues();
    }



    List<BioentityProperty> buildBioentityPropertiesForConcatenatedValues() {
        List<BioentityProperty> bioentityProperties = Lists.newArrayList();
        for (int i = 0; i < propertyValues.size(); i++) {
            for (String value: Splitter.on("@@").split(propertyValues.get(i))){
                BioentityProperty bioentityProperty =
                        new BioentityProperty(bioentityIdentifier, bioentityType,
                                species, propertyNames.get(i), value);
                bioentityProperties.add(bioentityProperty);
            }
        }
        return bioentityProperties;
    }

    boolean isDesignElementProperty() {
        return DESIGN_ELEMENT_PROPERTY_NAME.equals(propertyNames.get(0));
    }

}
