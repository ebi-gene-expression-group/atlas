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

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.List;

@Controller
@Scope("request")
public class BioentitiesController {

    @RequestMapping(value = "/query")
    public String showResultPage(@RequestParam (required = false) String condition, Model model) {

        model.addAttribute("entityIdentifier", condition);

        List<DifferentialBioentityExpression> differentialBioentityExpressions =
                    Lists.newArrayList(mockDifferentialBioentityExpression(1), mockDifferentialBioentityExpression(2));

        model.addAttribute("differentialBioentityExpressions", differentialBioentityExpressions);

        DifferentialExpressionLimits differentialExpressionLimits = mockDifferentialExpressionLimits();
        model.addAttribute("geneProfiles", differentialExpressionLimits);

        model.addAttribute("preferences", new DifferentialRequestPreferences());
        return "bioEntities";
    }

    private DifferentialExpressionLimits mockDifferentialExpressionLimits() {
        return new DifferentialExpressionLimits(1,10,2,200);
    }

    private DifferentialBioentityExpression mockDifferentialBioentityExpression(int index) {
        DifferentialExpression differentialExpression = mockDifferentialExpression(index);

        return new DifferentialBioentityExpression("bioentityId" + index, "experimentAccession" + index,
                                                    differentialExpression, "species" + index, "designElement" + index);
    }

    private DifferentialExpression mockDifferentialExpression(int index) {
        return new DifferentialExpression(index,index,
                    new Contrast("contrastId" + index,"arrayDesignAccession" + index, new AssayGroup("referenceAssayAccession" + index)
                            ,new AssayGroup("testAssayAccession" + index),"contrastDisplayName" + index));
    }
}
