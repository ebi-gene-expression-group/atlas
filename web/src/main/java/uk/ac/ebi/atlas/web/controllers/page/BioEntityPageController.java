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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.web.BioEntityCardProperties;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BioEntityPageController {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    public static final String PROPERTY_TYPE_SYMBOL = "symbol";

    private BioEntityPropertyService bioEntityPropertyService;

    private BioEntityCardProperties bioEntityCardProperties;

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    public String showGenePage(String identifier, Model model) {

        bioEntityPropertyService.init(identifier, getPagePropertyTypes());

        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        //there should be only one element of this kind
        model.addAttribute(PROPERTY_TYPE_SYMBOL, bioEntityPropertyService.getFirstValueOfProperty(getSymbolType()));

        return "bioEntity";
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        List<String> filteredPropertyTypes = getFilteredPropertyTypes();
        for (String propertyType : filteredPropertyTypes) {
            result.put(propertyType, bioEntityCardProperties.getPropertyName(propertyType));
        }

        return result;
    }

    List<String> getFilteredPropertyTypes() {
        List<String> propertyTypes = Lists.newArrayList(getPagePropertyTypes());
        propertyTypes.removeAll(Lists.newArrayList(PROPERTY_TYPE_SYMBOL, PROPERTY_TYPE_DESCRIPTION));
        return propertyTypes;
    }

    abstract String[] getPagePropertyTypes();

    abstract String getSymbolType();

}