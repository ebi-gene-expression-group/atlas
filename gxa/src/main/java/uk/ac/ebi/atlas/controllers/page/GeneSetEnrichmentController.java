package uk.ac.ebi.atlas.controllers.page;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.util.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.experiments.ExperimentMetadataEnrichmentService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.GeneSetEnrichmentClient;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;


@Controller
@Scope("request")
public class GeneSetEnrichmentController {

    private final Gson gson = new Gson();
    private final ExperimentMetadataEnrichmentService experimentMetadataEnrichmentService;
    private final GeneSetEnrichmentClient geneSetEnrichmentClient;
    private final SpeciesFactory speciesFactory;
    private final SpeciesLookupService speciesLookupService;

    @Inject
    public GeneSetEnrichmentController(ExperimentTrader experimentTrader,
                                       GeneSetEnrichmentClient geneSetEnrichmentClient,SpeciesFactory speciesFactory,SpeciesLookupService speciesLookupService){
        this.experimentMetadataEnrichmentService = new ExperimentMetadataEnrichmentService(experimentTrader);
        this.geneSetEnrichmentClient = geneSetEnrichmentClient;
        this.speciesFactory = speciesFactory;
        this.speciesLookupService = speciesLookupService;
    }


    @RequestMapping(value = "/genesetenrichment", method = RequestMethod.GET)
    public String getExperimentsListParameters(
            @RequestParam(defaultValue = "") String query,
            Model model) {
        List<String> bioentityIdentifiers = Arrays.asList(query.split("\\W+"));
        Validate.notEmpty(bioentityIdentifiers, "Please pass a list of genes separated by whitespace: ?query=gene_1 gene_2 ...");

        Species species = speciesFactory.create(
                speciesLookupService.
                        fetchFirstSpeciesForBioentityIdentifiers(bioentityIdentifiers)
                        .or("could not be determined for query")
        );

        Pair<Optional<String>, Optional<JsonArray>> result = geneSetEnrichmentClient.fetchEnrichedGenes(species, bioentityIdentifiers);

        if(GeneSetEnrichmentClient.isSuccess(result)){
            model.addAttribute("species", StringUtils.capitalizeFirstLetter(species.getReferenceName()));
            model.addAttribute("queryShort", Joiner.on(" ").join(Arrays.asList(query.split("\\W+")).subList(0,3)));
            model.addAttribute("query", query);
            model.addAttribute("data", gson.toJson(experimentMetadataEnrichmentService.enrich(result.getRight().get())));
            return "gene-set-enrichment-results";
        } else {
            model.addAttribute("exceptionMessage", result.getLeft().get());
            return "error-page";
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
