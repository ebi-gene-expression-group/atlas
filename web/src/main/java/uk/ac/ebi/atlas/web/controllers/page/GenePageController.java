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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@Scope("request")
public class GenePageController extends BioEntityPageController {

    public static final String PROPERTY_TYPE_SYMBOL = "symbol";

    // needs changing to DifferentialRequestPreferences.DEFAULT_CUTOFF
    public static final double CUTOFF = 0.5;

    @Value("#{configuration['index.types.genepage']}")
    private String genePagePropertyTypes;

    private DifferentialGeneProfileService differentialGeneProfileService;

    @Inject
    public void setDifferentialGeneProfileService(DifferentialGeneProfileService differentialGeneProfileService) {
        this.differentialGeneProfileService = differentialGeneProfileService;
    }

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {
        differentialGeneProfileService.getDifferentialProfilesListMapForIdentifier(identifier, CUTOFF);
        return super.showGenePage(identifier, model);
    }

    @Override
    String[] getPagePropertyTypes() {
        return genePagePropertyTypes.split(",");
    }

    @Override
    String getSymbolType() {
        return PROPERTY_TYPE_SYMBOL;
    }

    void setGenePagePropertyTypes(String genePagePropertyTypes) {
        this.genePagePropertyTypes = genePagePropertyTypes;
    }
}