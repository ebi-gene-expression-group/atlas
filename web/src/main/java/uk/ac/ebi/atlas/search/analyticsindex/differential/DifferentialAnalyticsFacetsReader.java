package uk.ac.ebi.atlas.search.analyticsindex.differential;

import autovalue.shaded.com.google.common.common.collect.Maps;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class DifferentialAnalyticsFacetsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;
    private final FoldChangeRounder foldChangeRounder;

    private static final String[] FACET_FIELDS = {"kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"};

    private static final String DOCS_PATH                  = "$.response.docs[*]";
    private static final String EXPERIMENT_TYPE_FIELD      = "experimentType";
    private static final String EXPERIMENT_ACCESSION_FIELD = "experimentAccession";
    private static final String CONTRAST_ID_FIELD          = "contrastId";
    private static final String LOG2_FOLD_CHANGE_FIELD     = "foldChange";

    private final ColourGradient colourGradient;

    @Inject
    public DifferentialAnalyticsFacetsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader, FoldChangeRounder foldChangeRounder, ColourGradient colourGradient) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
        this.foldChangeRounder = foldChangeRounder;
        this.colourGradient = colourGradient;
    }


    public String extractResultsAsJson(String solrResponseAsJson) {
        Map<String, Object> resultsWithLevels = new HashMap<>(3);

        double minUpLevel = Double.POSITIVE_INFINITY;
        double maxUpLevel = 0.0;
        double minDownLevel = Double.NEGATIVE_INFINITY;
        double maxDownLevel = 0.0;

        List<Map<String, Object>> filteredDocuments = Lists.newArrayList();
        TreeMultimap<String, String> experimentContrastMap = TreeMultimap.create();

        Configuration jsonPathConfiguration = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST);
        ReadContext jsonReadContext = JsonPath.using(jsonPathConfiguration).parse(solrResponseAsJson);
        List<Map<String, Object>> documents = jsonReadContext.read(DOCS_PATH);

        if(!documents.isEmpty()) {
            Map<String, Set<String>> commonFacetItems = new HashMap<>();
            for (String facetField : FACET_FIELDS) {
                commonFacetItems.put(facetField, stringOrStringCollectionAsSet(documents.get(0).get(facetField)));
            }

            for (Map<String, Object> document : documents) {
                String experimentAccession = (String) document.get(EXPERIMENT_ACCESSION_FIELD);
                String contrastId = (String) document.get(CONTRAST_ID_FIELD);
                if (experimentContrastMap.put(experimentAccession, contrastId)) {
                    intersectMaps(commonFacetItems, document);

                    ExperimentType experimentType = ExperimentType.get((String) document.get(EXPERIMENT_TYPE_FIELD));

                    Object foldChangeSymbol = document.get(LOG2_FOLD_CHANGE_FIELD);
                    double foldChange = foldChangeSymbol instanceof Double ? (double) foldChangeSymbol : Double.parseDouble((String) foldChangeSymbol);

                    document.put("foldChange", foldChange);
                    document.put("comparison", contrastTrader.getContrastFromCache(experimentAccession, experimentType, contrastId).getDisplayName());
                    document.put("experimentName", experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getDescription());

                    if (foldChange > 0.0) {
                        minUpLevel = Math.min(minUpLevel, foldChange);
                        maxUpLevel = Math.max(maxUpLevel, foldChange);
                    } else {
                        minDownLevel = Math.max(minDownLevel, foldChange);
                        maxDownLevel = Math.min(maxDownLevel, foldChange);
                    }

                    filteredDocuments.add(document);
                }
            }

            for (Map<String, Object> document : filteredDocuments) {
                double foldChange = (Double) document.get("foldChange");
                String colour = foldChange > 0.0 ? colourGradient.getGradientColour(foldChange, minUpLevel, maxUpLevel, "pink", "red") : colourGradient.getGradientColour(foldChange, minDownLevel, maxDownLevel, "lightGray", "blue");
                document.put("colour", colour);
                document.put("foldChange", foldChangeRounder.format(foldChange));
            }

            resultsWithLevels.put("maxDownLevel", foldChangeRounder.format(maxDownLevel));
            resultsWithLevels.put("minDownLevel", foldChangeRounder.format(minDownLevel));
            resultsWithLevels.put("minUpLevel", foldChangeRounder.format(minUpLevel));
            resultsWithLevels.put("maxUpLevel", foldChangeRounder.format(maxUpLevel));

            String[] keySet = commonFacetItems.keySet().toArray(new String[commonFacetItems.keySet().size()]);
            for (String facetField : keySet) {
                Set<String> items = commonFacetItems.remove(facetField);
                commonFacetItems.put(FacetFieldMapConverter.get(facetField), items);
            }
            resultsWithLevels.put("commonFacetItems", commonFacetItems);

        }

        resultsWithLevels.put("results", filteredDocuments);

        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
        return gson.toJson(resultsWithLevels);
    }

    private void intersectMaps(Map<String, Set<String>> thisMap, Map<String, Object> document) {
        for (String key : thisMap.keySet()) {
            if (!thisMap.get(key).isEmpty()) {
                Set<String> otherMapValueAsStringSet = stringOrStringCollectionAsSet(document.get(key));
                thisMap.get(key).retainAll(otherMapValueAsStringSet);
            }
        }
    }

    private Set<String> stringOrStringCollectionAsSet(Object stringOrStringCollection) {
        Set<String> stringSet = Sets.newHashSet();

        if (stringOrStringCollection instanceof String) {
            stringSet.add((String) stringOrStringCollection);
        } else if (stringOrStringCollection instanceof Collection) {
            for (Object o : (Collection) stringOrStringCollection) {
                if (o instanceof String) {
                    stringSet.add((String) o);
                }
            }
        }

        return stringSet;
    }

    public String generateFacetsTreeJson(String solrResponseAsJson) {
        JsonObject facets = new JsonObject();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String facetField : FACET_FIELDS) {
            JsonArray facet = new JsonArray();
            List<Object> facetFieldValues = jsonReadContext.read("$.." + facetField + "..val");
            for (Object facetFieldValue : facetFieldValues) {
                JsonObject facetItem = new JsonObject();
                facetItem.addProperty("name", facetFieldValue.toString());
                if(facetField.equals("experimentType")) {
                    facetItem.addProperty("value", ExperimentsTypeMapConverter.get(facetFieldValue.toString()));
                } else {
                    facetItem.addProperty("value", facetFieldValue.toString().toLowerCase());
                }
                facet.add(facetItem);
            }

            facets.add(FacetFieldMapConverter.get(facetField), facet);
        }

        return facets.toString();
    }

    protected static class ExperimentsTypeMapConverter {
        private static final Logger LOGGER = LogManager.getLogger(ExperimentsTypeMapConverter.class);

        private static final Map<String,String> EXPERIMENTS_TYPE_MAP = ImmutableMap.<String, String>builder()
                .put("rnaseq_mrna_baseline", "RNA-seq mRNA baseline")
                .put("rnaseq_mrna_differential", "RNA-seq mRNA differential")
                .put("proteomics_baseline", "proteomics baseline")
                .put("microarray_1colour_microrna_differential", "microarray 1-colour microRNA differential")
                .put("microarray_1colour_mrna_differential", "microarray 1-colour mRNA differential")
                .put("microarray_2colour_mrna_differential", "microarray 2-colour mRNA differential")
                .build();

        public static String get(String type) {
            if(EXPERIMENTS_TYPE_MAP.get(type) != null) {
                return EXPERIMENTS_TYPE_MAP.get(type);
            } else {
                LOGGER.warn(String.format("Experiment type not found %s", type));
                return type;
            }
        }
    }

    protected static class FacetFieldMapConverter {
        private static final Logger LOGGER = LogManager.getLogger(FacetFieldMapConverter.class);

        private static final Map<String,String> FACET_FIELDS_MAP = ImmutableMap.<String, String>builder()
                .put("kingdom", "kingdom")
                .put("species", "species")
                .put("experimentType", "experiment type")
                .put("factors", "experimental variables")
                .put("numReplicates", "number of replicates")
                .put("regulation", "regulation")
                .build();

        public static String get(String type) {
            if(FACET_FIELDS_MAP.get(type) != null) {
                return FACET_FIELDS_MAP.get(type);
            } else {
                LOGGER.warn(String.format("Facet field not found %s", type));
                return type;
            }
        }
    }
}
