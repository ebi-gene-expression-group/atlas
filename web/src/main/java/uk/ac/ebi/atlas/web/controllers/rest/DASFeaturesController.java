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

package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.GeneQueryDifferentialService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Controller
@Scope("request")
public class DASFeaturesController {

    private GeneQueryDifferentialService geneQueryDifferentialService;
    private ExperimentTrader experimentTrader;

    @Inject
    public DASFeaturesController(GeneQueryDifferentialService geneQueryDifferentialService, ExperimentTrader experimentTrader) {
        this.geneQueryDifferentialService = geneQueryDifferentialService;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/das/s4/features")
    public String dasFeatures(@RequestParam(value = "segment") String geneId, Model model) {

        List<DifferentialBioentityExpression> differentialBioentityExpressions = geneQueryDifferentialService.queryWithoutCount(geneId);

        String geneName = differentialBioentityExpressions.isEmpty() ? geneId : differentialBioentityExpressions.get(0).getBioentityName();

        SetMultimap<String, String> factorValuesByType = HashMultimap.create();

        for (DifferentialBioentityExpression dbe : differentialBioentityExpressions) {
            AssayGroup testAssayGroup = dbe.getContrastTestAssayGroup();
            Experiment experiment = experimentTrader.getPublicExperiment(dbe.getExperimentAccession());

            FactorSet factorsForAssayGroup = FactorSet.create(experiment.getExperimentDesign().getFactors(testAssayGroup.getFirstAssayAccession()));
            for (Factor factor : factorsForAssayGroup) {
                factorValuesByType.put(factor.getType(), factor.getValue());
            }
        }

        model.addAttribute("geneId", geneId);
        model.addAttribute("geneName", geneName);
        model.addAttribute("factorValues_ORGANISM_PART", formatFactorValuesLabel(factorValuesByType.get("ORGANISM_PART")));
        model.addAttribute("factorValues_DISEASE", formatFactorValuesLabel(factorValuesByType.get("DISEASE")));
        model.addAttribute("factorValues_CELL_TYPE", formatFactorValuesLabel(factorValuesByType.get("CELL_TYPE")));
        model.addAttribute("factorValues_CELL_LINE", formatFactorValuesLabel(factorValuesByType.get("CELL_LINE")));
        model.addAttribute("factorValues_COMPOUND", formatFactorValuesLabel(factorValuesByType.get("COMPOUND")));
        model.addAttribute("factorValues_DEVELOPMENTAL_STAGE", formatFactorValuesLabel(factorValuesByType.get("DEVELOPMENTAL_STAGE")));
        model.addAttribute("factorValues_INFECT", formatFactorValuesLabel(factorValuesByType.get("INFECT")));
        model.addAttribute("factorValues_PHENOTYPE", formatFactorValuesLabel(factorValuesByType.get("PHENOTYPE")));
        return "das-features";
    }

    private String formatFactorValuesLabel(Set<String> factorValues) {
        if (factorValues.isEmpty()) {
            return "Not studied or no differential expression found for this gene";
        }
        return Joiner.on(", ").join(factorValues);
    }

}
