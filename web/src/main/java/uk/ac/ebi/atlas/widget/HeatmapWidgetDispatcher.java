package uk.ac.ebi.atlas.widget;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@Controller
@Scope("request")
public class HeatmapWidgetDispatcher extends HeatmapWidgetErrorHandler {

    private static final String ORIGINAL_GENEQUERY = "geneQuery";

    private SpeciesLookupService speciesLookupService;
    private ApplicationProperties applicationProperties;
    private ExperimentTrader experimentTrader;

    @Inject
    public HeatmapWidgetDispatcher(SpeciesLookupService speciesLookupService, ApplicationProperties applicationProperties,
                                   ExperimentTrader experimentTrader){
        this.speciesLookupService = speciesLookupService;
        this.applicationProperties = applicationProperties;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = {"/widgets/heatmap/referenceExperiment"})
    public String dispatchWidget(HttpServletRequest request,
                                 @RequestParam(value = "geneQuery") String geneQueryString,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @RequestParam(value = "species", required = false) String species,
                                 @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences) {

        try {
            if (isBlank(species)) {
                species = speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQueryString);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException( "No genes found matching query: " + geneQueryString);
        }

        String experimentAccession = applicationProperties.getBaselineReferenceExperimentAccession(species);

        if (isBlank(experimentAccession)) {
            throw new ResourceNotFoundException("No baseline experiment for species " + species);
        }

        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        request.setAttribute("experiment", experiment);

        //TODO: hacky, fix this, see RnaSeqBaselineExperimentPageController
        request.setAttribute(ORIGINAL_GENEQUERY, geneQueryString);

        // forward to /widgets/heatmap/referenceExperiment?type=RNASEQ_MRNA_BASELINE in BaselineExperimentPageService
        // the model attributes are also preserved by a forward
        return "forward:" + getRequestURL(request) + buildQueryString(experiment);
    }

    private String getRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        return substringAfter(requestURI, contextPath);
    }

    private String buildQueryString(Experiment experiment) {
        return "?type=" + experiment.getType().getParent();
    }

}
