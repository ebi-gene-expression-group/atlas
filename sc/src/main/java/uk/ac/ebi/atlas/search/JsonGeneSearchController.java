package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.search.geneids.GeneIdSearchService;
import uk.ac.ebi.atlas.search.geneids.GeneQuery;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.ID_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonGeneSearchController extends JsonExceptionHandlingController {

    private final GeneIdSearchService geneIdSearchService;
    private final SpeciesFactory speciesFactory;
    private final GeneSearchService geneSearchService;
    private final ScxaExperimentTrader experimentTrader;
    private final ExperimentAttributesService experimentAttributesService;

    public JsonGeneSearchController(GeneIdSearchService geneIdSearchService,
                                    SpeciesFactory speciesFactory,
                                    GeneSearchService geneSearchService,
                                    ScxaExperimentTrader experimentTrader,
                                    ExperimentAttributesService experimentAttributesService) {
        this.geneIdSearchService = geneIdSearchService;
        this.speciesFactory = speciesFactory;
        this.geneSearchService = geneSearchService;
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
    }

    @RequestMapping(value = "/json/search",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String search(@RequestParam MultiValueMap<String, String> requestParams) {
        Optional<Species> species = Optional.ofNullable(requestParams.getFirst("species")).map(speciesFactory::create);

        Stream<String> validQueryFields =
                Stream.concat(Stream.of("q"), ID_PROPERTY_NAMES.stream().map(BioentityPropertyName::name));

        // We support currently only one query term; in the unlikely case that somebody fabricates a URL with more than
        // one we’ll build the query with the first match. Remember that in order to support multiple terms we’ll
        // likely need to change GeneQuery and use internally a SemanticQuery
        String category =
                requestParams.keySet().stream()
                        .filter(
                                actualField ->
                                        validQueryFields.anyMatch(
                                                validField -> validField.equalsIgnoreCase(actualField)))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Error parsing query"));

        String queryTerm = requestParams.getFirst(category);

        GeneQuery geneQuery;
        if (category.equals("q")) {
            geneQuery =
                    species.map(_species -> GeneQuery.create(queryTerm, _species))
                           .orElseGet(() -> GeneQuery.create(queryTerm));
        } else {
            geneQuery =
                    species.map(
                            _species ->
                                    GeneQuery.create(queryTerm, BioentityPropertyName.getByName(category), _species))
                           .orElseGet(() -> GeneQuery.create(queryTerm, BioentityPropertyName.getByName(category)));

        }
        Optional<ImmutableSet<String>> geneIds = geneIdSearchService.search(geneQuery);

        if (!geneIds.isPresent()) {
            return GSON.toJson(
                    ImmutableMap.of(
                            "results", ImmutableList.of(),
                            "reason", "Gene unknown"));
        }

        if (geneIds.get().isEmpty()) {
            return GSON.toJson(
                    ImmutableMap.of(
                            "results", ImmutableList.of(),
                            "reason", "No expression found"));
        }

        // We found expressed gene IDs, let’s get to it now...

        Map<String, Map<String, List<String>>> geneIds2ExperimentAndCellIds =
                geneSearchService.getCellIdsInExperiments(geneIds.get().toArray(new String[0]));

        List<Map.Entry<String, Map<String, List<String>>>> expressedGeneIdEntries =
                geneIds2ExperimentAndCellIds.entrySet().stream()
                        .filter(entry -> !entry.getValue().isEmpty())
                        .collect(toList());

        Map<String, Map<String, Map<Integer, List<Integer>>>> markerGeneFacets =
                geneSearchService.getMarkerGeneProfile(
                        expressedGeneIdEntries.stream()
                                .map(Map.Entry::getKey)
                                .toArray(String[]::new));

        // geneSearchServiceDao guarantees that values in the inner maps can’t be empty. The map itself may be empty
        // but if there’s an entry the list will have at least on element
        ImmutableList<ImmutableMap<String, Object>> results =
                expressedGeneIdEntries.stream()
                        // TODO Measure in production if parallelising the stream below results in any speedup
                        // TODO (the more experiments we have the better)
                        .flatMap(entry -> entry.getValue().entrySet().stream().map(exp2cells -> {

                            // Inside this map-within-a-flatMap we unfold expressedGeneIdEntries to triplets of...
                            String geneId = entry.getKey();
                            String experimentAccession = exp2cells.getKey();
                            List<String> cellIds = exp2cells.getValue();

                            Map<String, Object> experimentAttributes =
                                    getExperimentInformation(experimentAccession, geneId);
                            List<Map<String, String>> facets =
                                    unfoldFacets(geneSearchService.getFacets(cellIds)
                                            .getOrDefault(experimentAccession, ImmutableMap.of()));

                            if (markerGeneFacets.containsKey(geneId) &&
                                markerGeneFacets.get(geneId).containsKey(experimentAccession)) {
                                facets.add(
                                        ImmutableMap.of(
                                                "group", "Marker genes",
                                                "value", "experiments with marker genes",
                                                "label", "Experiments with marker genes",
                                                "description", FacetsToTooltipMapping.MARKER_GENE.getTooltip()));
                                experimentAttributes.put(
                                        "markerGenes",
                                        convertMarkerGeneModel(
                                                experimentAccession,
                                                geneId,
                                                markerGeneFacets.get(geneId).get(experimentAccession)));
                            }

                            return ImmutableMap.of("element", experimentAttributes, "facets", facets);

                        })).collect(toImmutableList());

        return GSON.toJson(
                ImmutableMap.of(
                        "results", results,
                        "checkboxFacetGroups", ImmutableList.of("Marker genes", "Species")));
    }

    private <K, V> List<SimpleEntry<K, V>> unfoldListMultimap(Map<K, List<V>> multimap) {
        return multimap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> new SimpleEntry<>(entry.getKey(), value)))
                .collect(toList());
    }

    private List<Map<String, String>> unfoldFacets(Map<String, List<String>> model) {

        List<SimpleEntry<String, String>> unfoldedModel = unfoldListMultimap(model);
        List<Map<String, String>> results = new ArrayList<>();

        for (Map.Entry<String, String> entry : unfoldedModel) {
            ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.<String, String>builder()
                    .put("group", entry.getKey())
                    .put("value", entry.getKey())
                    .put("label", StringUtils.capitalize(entry.getValue()));

            if(!isNullOrEmpty(getTooltipText(entry.getKey()))) {
                mapBuilder.put("description", getTooltipText(entry.getKey()));
            }

            results.add(mapBuilder.build());
        }
        return results;
    }

    private String getTooltipText(String group){
        for (FacetsToTooltipMapping tooltip : FacetsToTooltipMapping.values()) {
            if(tooltip.getTitle().equalsIgnoreCase(group)) {
               return tooltip.getTooltip();
            }
        }
        return null;
    }

    private Map<String, Object> getExperimentInformation(String experimentAccession, String geneId) {
        Experiment<Cell> experiment = experimentTrader.getPublicExperiment(experimentAccession);
        Map<String, Object> experimentAttributes = experimentAttributesService.getAttributes(experiment);
        experimentAttributes.put("url", createExperimentPageURL(experimentAccession, geneId));

        return experimentAttributes;
    }

    private List<Map<String, Object>> convertMarkerGeneModel(String experimentAccession,
                                                             String geneId,
                                                             Map<Integer, List<Integer>> model) {
        return model.entrySet().stream()
                .map(entry ->
                        ImmutableMap.of(
                                "k", entry.getKey(),
                                "clusterIds", entry.getValue(),
                                "url", createResultsPageURL(
                                        experimentAccession, geneId, entry.getKey(), entry.getValue())))
                .collect(toList());
    }

    private static String createExperimentPageURL(String experimentAccession, String geneId) {
       return ServletUriComponentsBuilder.fromCurrentContextPath()
               .path("/experiments/{experimentAccession}")
               .query("geneId={geneId}")
               .buildAndExpand(experimentAccession, geneId)
               .toUriString();
    }

    private static String createResultsPageURL(String experimentAccession,
                                               String geneId,
                                               Integer k,
                                               List<Integer> clusterId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{experimentAccession}/Results")
                .query("geneId={geneId}")
                .query("k={k}")
                .query("clusterId={clusterId}")
                .buildAndExpand(experimentAccession, geneId, k, clusterId)
                .toUriString();
    }
}
