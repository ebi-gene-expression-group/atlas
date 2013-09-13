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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.DifferentialBioentityExpressionsBuilder;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;

@Controller
@Scope("request")
public class BioentitiesController {

    private DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder;

    @Inject
    public BioentitiesController(DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder) {
        this.differentialBioentityExpressionsBuilder = differentialBioentityExpressionsBuilder;
    }

    @RequestMapping(value = "/query")
    public String showResultPage(@RequestParam (value="condition", required = false) String condition, Model model) {

        model.addAttribute("entityIdentifier", condition);

        DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.build(condition);

        model.addAttribute("bioentities", bioentityExpressions);

        model.addAttribute("preferences", new DifferentialRequestPreferences());

        return "bioEntities";
    }

    private DifferentialBioentityExpressions mockDifferentialExpressions() {
        DifferentialBioentityExpressions differentialBioentityExpressions = new DifferentialBioentityExpressions(2011);
        differentialBioentityExpressions.add(mockDifferentialBioentityExpression(1, true));
        differentialBioentityExpressions.add(mockDifferentialBioentityExpression(2, true));
        differentialBioentityExpressions.add(mockDifferentialBioentityExpression(3, false));
        return differentialBioentityExpressions;
    }

    private DifferentialBioentityExpression mockDifferentialBioentityExpression(int index, boolean upRegulated) {
        DifferentialExpression differentialExpression = mockDifferentialExpression(index, upRegulated);

        return new DifferentialBioentityExpression("bioentityId" + index, "experimentAccession" + index,
                                                    differentialExpression, "species" + index, "designElement" + index);
    }

    private DifferentialExpression mockDifferentialExpression(int index, boolean upRegulated) {
        return new DifferentialExpression(index,upRegulated? index : -index,
                    new Contrast("contrastId" + index,"arrayDesignAccession" + index, new AssayGroup("referenceAssayAccession" + index)
                            ,new AssayGroup("testAssayAccession" + index),"contrastDisplayName" + index));
    }
}
