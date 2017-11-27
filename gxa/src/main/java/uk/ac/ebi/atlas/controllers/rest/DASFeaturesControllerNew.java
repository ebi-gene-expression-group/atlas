package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchDao;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsQueryBuilder;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public class DASFeaturesControllerNew {


    private ExperimentTrader experimentTrader;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private DifferentialJsonResultsParser differentialJsonResultsParser;


    @Inject
    public DASFeaturesControllerNew(ExperimentTrader experimentTrader,
                                    DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                    DifferentialJsonResultsParser differentialJsonResultsParser) {

        this.experimentTrader = experimentTrader;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.differentialJsonResultsParser = differentialJsonResultsParser;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @RequestMapping(value = "/das/s4/features/test")
    public String getDifferentialJsonResults(@RequestParam(value = "segment", required = false, defaultValue = "")
                                                     String geneId,
                                             @RequestParam(value = "conditionQuery", required = false, defaultValue = "")
                                                     SemanticQuery conditionQuery,
                                             Model model) {

        try {

            List<DiffAnalytics> diffAnalyticsList;
            SetMultimap<String, String> factorValuesByType = HashMultimap.create();

            JsonObject result = differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(geneId), conditionQuery);

            diffAnalyticsList = differentialJsonResultsParser.parseDifferentialResults(result);

            String geneName = diffAnalyticsList.isEmpty() ? geneId : diffAnalyticsList.get(0).getBioentityName();

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
        catch (Exception e)
        {
            throw e;
        }
    }

    private String formatFactorValuesLabel(Set<String> factorValues) {
        if (factorValues.isEmpty()) {
            return "Not studied or no differential expression found for this gene";
        }
        return Joiner.on(", ").join(factorValues);
    }

}
