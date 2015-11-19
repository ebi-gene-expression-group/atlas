package uk.ac.ebi.atlas.search.analyticsindex.differential;

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
public class DifferentialResultsReader {

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
    public DifferentialResultsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader, FoldChangeRounder foldChangeRounder, ColourGradient colourGradient) {
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
                commonFacetItems.put(facetField, items);
            }
            resultsWithLevels.put("commonFacetItems", commonFacetItems);

        }

        resultsWithLevels.put("results", filteredDocuments);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
}
