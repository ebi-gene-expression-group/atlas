
package uk.ac.ebi.atlas.web.controllers.rest;


import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("request")
public class DASFeaturesController {

    private DiffAnalyticsSearchService diffAnalyticsSearchService;
    private ExperimentTrader experimentTrader;

    @Inject
    public DASFeaturesController(DiffAnalyticsSearchService diffAnalyticsSearchService, ExperimentTrader experimentTrader) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
        this.experimentTrader = experimentTrader;
    }


    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public String handleException(Exception e) {
        return e.getMessage();
    }

    @RequestMapping(value = "/das/s4/features")
    public String dasFeatures(@RequestParam(value = "segment") String geneId, Model model) {

        checkArgument(geneId.length() <= 255, "Segment parameter is too long");

        List<DiffAnalytics> diffAnalyticsList = diffAnalyticsSearchService.fetchTopWithoutCountAnySpecies(geneId);

        String geneName = diffAnalyticsList.isEmpty() ? geneId : diffAnalyticsList.get(0).getBioentityName();

        SetMultimap<String, String> factorValuesByType = HashMultimap.create();

        for (DiffAnalytics dbe : diffAnalyticsList) {
            AssayGroup testAssayGroup = dbe.getContrastTestAssayGroup();
            Experiment experiment = experimentTrader.getPublicExperiment(dbe.getExperimentAccession());

            FactorSet factorsForAssayGroup = FactorSet.create(experiment.getExperimentDesign().getFactorValues(testAssayGroup.getFirstAssayAccession()));
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
