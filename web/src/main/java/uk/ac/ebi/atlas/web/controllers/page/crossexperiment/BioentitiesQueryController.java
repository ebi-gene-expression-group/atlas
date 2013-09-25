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

package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.DifferentialBioentityExpressionsBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;

@Controller
@Scope("request")
public class BioentitiesQueryController {

    private DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder;

    @Inject
    public BioentitiesQueryController(DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder) {
        this.differentialBioentityExpressionsBuilder = differentialBioentityExpressionsBuilder;
    }

    @RequestMapping(value = "/query")
    public String showResultPage(@RequestParam (value="condition", required = false) String condition, Model model) {

        if(StringUtils.isNotBlank(condition)){

            model.addAttribute("entityIdentifier", condition);

            DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.build(condition);

            model.addAttribute("bioentities", bioentityExpressions);

            model.addAttribute("preferences", new DifferentialRequestPreferences());

        }

        return "bioEntities";
    }

}
