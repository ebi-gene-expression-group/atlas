//package uk.ac.ebi.atlas.web.controllers.page;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import uk.ac.ebi.atlas.commands.GenesNotFoundException;
//import uk.ac.ebi.atlas.commands.RankBaselineProfilesCommand;
//import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
//import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
//import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
//import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
//import uk.ac.ebi.atlas.model.baseline.Factor;
//import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
//import uk.ac.ebi.atlas.web.ApplicationProperties;
//import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
//import uk.ac.ebi.atlas.web.FilterFactorsConverter;
//import uk.ac.ebi.atlas.web.controllers.BaselineQueryController;
//
//import javax.inject.Inject;
//import javax.validation.Valid;
//import java.util.Set;
//import java.util.SortedSet;
//
//@Controller
//@Scope("request")
//public class ProteinWidgetController extends BaselineQueryController {
//
//    private static final String E_MTAB_513 = "E-MTAB-513";
//    private BaselineExperimentsCache baselineExperimentsCache;
//
//    private RankBaselineProfilesCommand rankCommand;
//
//    private ApplicationProperties applicationProperties;
//
//    @Inject
//    public ProteinWidgetController(BaselineExperimentsCache baselineExperimentsCache
//            , RankBaselineProfilesCommand rankCommand
//            , ApplicationProperties applicationProperties
//            , BaselineRequestContextBuilder baselineRequestContextBuilder,
//                                   FilterFactorsConverter filterFactorsConverter) {
//        super(baselineRequestContextBuilder, filterFactorsConverter);
//        this.baselineExperimentsCache = baselineExperimentsCache;
//        this.rankCommand = rankCommand;
//        this.applicationProperties = applicationProperties;
//    }
//
//    @RequestMapping(value = "/heatmap-widget/protein/{identifier}")
//    public String getHomePage(@PathVariable String identifier,
//                              @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
//            , Model model, BindingResult result) {
//        model.addAttribute("experimentAccession", E_MTAB_513);
//        model.addAttribute("type", "BASELINE");
//
//        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(E_MTAB_513);
//
//        model.addAttribute("experimentDescription", experiment.getDescription());
//
//        model.addAttribute("allSpecies", experiment.getSpecies());
//
//        model.addAttribute("queryFactorName", StringUtils.capitalize(experiment.getDefaultQueryFactorType()));
//
//        initPreferences(preferences, experiment);
//        preferences.setGeneSetMatch(false);
//        preferences.setGeneQuery(identifier);
//
//
//        BaselineRequestContext requestContext = initRequestContext(experiment, preferences);
//
//        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();
//        SortedSet<Factor> allQueryFactors = experiment.getExperimentalFactors().getFilteredFactors(selectedFilterFactors);
//
//        // this is currently required for the request requestPreferences filter drop-down multi-selection box
//        model.addAttribute("allQueryFactors", allQueryFactors);
//
//        try {
//
//            BaselineProfilesList baselineProfiles = rankCommand.execute(experiment.getAccession());
//
//            model.addAttribute("geneProfiles", baselineProfiles);
//
//            //ToDo: check if this can be externalized in the view with a cutom EL or tag function
//            if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
//                model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(requestContext.getFilteredBySpecies(), true));
//
//                model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(requestContext.getFilteredBySpecies(), false));
//            }
//
//
//        } catch (GenesNotFoundException e) {
//            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
//        }
//
//        return "heatmap-widget";
//    }
//}
