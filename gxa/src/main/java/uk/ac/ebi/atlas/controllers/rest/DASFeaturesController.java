package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.solr.analytics.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
@Scope("request")
public class DASFeaturesController extends HtmlExceptionHandlingController {
    private final ExperimentTrader experimentTrader;
    private final DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private final DifferentialJsonResultsParser differentialJsonResultsParser;

    @Inject
    public DASFeaturesController(ExperimentTrader experimentTrader,
                                    DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                    DifferentialJsonResultsParser differentialJsonResultsParser) {

        this.experimentTrader = experimentTrader;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.differentialJsonResultsParser = differentialJsonResultsParser;
    }

    @RequestMapping(value = "/das/s4/features")
    public String getDifferentialJsonResults(
            @RequestParam(value = "segment", required = false, defaultValue = "") String geneId,
            @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
            Model model) {

        checkArgument(isNotBlank(geneId) || conditionQuery.isNotEmpty());

        List<DiffAnalytics> diffAnalyticsList =
                differentialJsonResultsParser.parseDifferentialResults(
                        differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(geneId), conditionQuery));

        String geneName = diffAnalyticsList.isEmpty() ? geneId : diffAnalyticsList.get(0).getBioentityName();

        SetMultimap<String, String> factorValuesByType = HashMultimap.create();
        for (DiffAnalytics dbe : diffAnalyticsList) {
            AssayGroup testAssayGroup = dbe.getContrastTestAssayGroup();
            Experiment experiment = experimentTrader.getPublicExperiment(dbe.getExperimentAccession());

            FactorSet factorsForAssayGroup =
                    FactorSet.create(experiment.getExperimentDesign()
                             .getFactorValues(testAssayGroup.getFirstAssayAccession()));
            for (Factor factor : factorsForAssayGroup) {
                factorValuesByType.put(factor.getType(), factor.getValue());
            }
        }

        model.addAttribute("geneId", geneId);
        model.addAttribute("geneName", geneName);

        for (String factorValue:
                ImmutableList.of("ORGANISM_PART", "DISEASE", "CELL_TYPE", "CELL_LINE", "COMPOUND",
                                 "DEVELOPMENTAL_STAGE", "INFECT", "PHENOTYPE")) {
            model.addAttribute(
                    "factorValues_" + factorValue,
                    formatFactorValuesLabel(factorValuesByType.get(factorValue)));
        }

        return "das-features";
    }

    private String formatFactorValuesLabel(Set<String> factorValues) {
        if (factorValues.isEmpty()) {
            return "Not studied or no differential expression found for this gene";
        }
        return Joiner.on(", ").join(factorValues);
    }

}
