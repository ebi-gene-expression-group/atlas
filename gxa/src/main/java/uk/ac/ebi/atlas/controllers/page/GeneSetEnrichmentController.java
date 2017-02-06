package uk.ac.ebi.atlas.controllers.page;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.experiments.ExperimentMetadataEnrichmentService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.GeneSetEnrichmentClient;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Controller
@Scope("request")
public class GeneSetEnrichmentController {

    private final Gson gson = new Gson();
    private final ExperimentMetadataEnrichmentService experimentMetadataEnrichmentService;
    private final GeneSetEnrichmentClient geneSetEnrichmentClient;
    private final SpeciesFactory speciesFactory;

    @Inject
    public GeneSetEnrichmentController(ExperimentTrader experimentTrader,
                                       GeneSetEnrichmentClient geneSetEnrichmentClient,SpeciesFactory speciesFactory){
        this.experimentMetadataEnrichmentService = new ExperimentMetadataEnrichmentService(experimentTrader);
        this.geneSetEnrichmentClient = geneSetEnrichmentClient;
        this.speciesFactory = speciesFactory;
    }


    @RequestMapping(value = "/gse/{species}/{query}", method = RequestMethod.GET)
    public String getExperimentsListParameters(
            @PathVariable String species,
            @PathVariable String query,
            HttpServletRequest request,
            Model model) {

        Species speciesObject = speciesFactory.create(species);

        Pair<Optional<String>, Optional<JsonArray>> result = geneSetEnrichmentClient.fetchEnrichedGenes
                (speciesObject, Arrays.asList(query.split(" ")));

        if(GeneSetEnrichmentClient.isSuccess(result)){
            model.addAttribute("species", speciesObject.getReferenceName());
            model.addAttribute("query", query);
            model.addAttribute("data", gson.toJson(experimentMetadataEnrichmentService.enrich(result.getRight().get())));
            return "gene-set-enrichment-results";
        } else {
            model.addAttribute("exceptionMessage", result.getLeft().get());
            return "query-error-page";
        }
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
