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

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.web.BioEntityCardProperties;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BioEntityPageController {

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

        String entityNamePropertyType = getEntityNamePropertyType();

        bioEntityPropertyService.init(identifier, entityNamePropertyType, getPagePropertyTypes());

        model.addAttribute("entityIdentifier", identifier);

        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntity";
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyType : getPagePropertyTypes()) {
            result.put(propertyType, bioEntityCardProperties.getPropertyName(propertyType));
        }
        return result;
    }

    abstract List<String> getPagePropertyTypes();

    abstract String getEntityNamePropertyType();

}