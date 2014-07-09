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

package uk.ac.ebi.atlas.bioentity;

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
