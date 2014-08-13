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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("request")
public class ProteinPageController extends BioEntityPageController {

    public static final String GENE_NAME_PROPERTY_TYPE = "uniprot";

    private String[] proteinPagePropertyTypes;

    @Value("#{configuration['index.property_names.proteinpage']}")
    void setProteinPagePropertyTypes(String[] proteinPagePropertyTypes) {
        this.proteinPagePropertyTypes = proteinPagePropertyTypes;
    }

    @RequestMapping(value = "/proteins/{identifier:.*}")
    public String showBioentityPage(@PathVariable String identifier, Model model) {
        //NB: when we query for proteins the bioentity page there is no Differential Expression panel

        addWidgetHasBaselineProfiles(identifier, model);

        return super.showBioentityPage(identifier, model, true);
    }

    @Override
    String[] getPagePropertyTypes() {
        return proteinPagePropertyTypes;
    }

    @Override
    String getBioentityPropertyName() {
        return GENE_NAME_PROPERTY_TYPE;
    }

    @Override
    protected boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(BioEntityPageController.PROPERTY_TYPE_DESCRIPTION);
    }
}