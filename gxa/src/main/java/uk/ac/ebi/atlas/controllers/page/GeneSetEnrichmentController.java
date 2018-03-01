package uk.ac.ebi.atlas.controllers.page;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.util.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.controllers.SpeciesUnknownException;
import uk.ac.ebi.atlas.experiments.ExperimentMetadataEnrichmentService;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.GeneSetEnrichmentClient;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public class GeneSetEnrichmentController extends HtmlExceptionHandlingController {

    private final Gson gson = new Gson();
    private final ExperimentMetadataEnrichmentService experimentMetadataEnrichmentService;
    private final GeneSetEnrichmentClient geneSetEnrichmentClient;
    private final SpeciesInferrer speciesInferrer;

    @Inject
    public GeneSetEnrichmentController(ExperimentTrader experimentTrader,
                                       GeneSetEnrichmentClient geneSetEnrichmentClient,
                                       SpeciesInferrer speciesInferrer) {
        this.experimentMetadataEnrichmentService = new ExperimentMetadataEnrichmentService(experimentTrader);
        this.geneSetEnrichmentClient = geneSetEnrichmentClient;
        this.speciesInferrer = speciesInferrer;
    }


    @RequestMapping(value = "/genesetenrichment", method = RequestMethod.GET)
    public String getExperimentsListParameters(@RequestParam(defaultValue = "") String query, Model model) {
        List<String> bioentityIdentifiers = Arrays.asList(query.trim().split("\\W+"));
        checkArgument(
                !bioentityIdentifiers.isEmpty() && !isBlank(query),
                "Please pass a list of genes separated by whitespace: ?query=gene_1 gene_2 ...");

        Set<SemanticQueryTerm> queryTerms = bioentityIdentifiers.stream().map(SemanticQueryTerm::create).collect(Collectors.toSet());

        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(queryTerms));

        if (species.isUnknown()) {
            throw new SpeciesUnknownException();
        }

        Pair<Optional<Exception>, Optional<JsonArray>> result =
                geneSetEnrichmentClient.fetchEnrichedGenes(species.getEnsemblName().toLowerCase(), bioentityIdentifiers);

        if (GeneSetEnrichmentClient.isSuccess(result)) {
            model.addAttribute("species", StringUtils.capitalizeFirstLetter(species.getReferenceName()));
            model.addAttribute("queryShort", Joiner.on(" ").join(Arrays.asList(query.split("\\W+")).subList(0,3)));
            model.addAttribute("query", query);
            model.addAttribute(
                    "data", gson.toJson(experimentMetadataEnrichmentService.enrich(result.getRight().get())));
            return "gene-set-enrichment-results";
        } else {
            throw new RuntimeException(result.getLeft().get().getMessage());
        }
    }
}
