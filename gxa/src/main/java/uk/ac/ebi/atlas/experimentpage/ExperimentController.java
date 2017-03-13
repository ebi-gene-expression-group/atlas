package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedListMultimap;
import com.google.gson.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTable;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class ExperimentController extends ExperimentPageController{

    private final ExperimentTrader experimentTrader;
    private final ApplicationProperties applicationProperties;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();

    @Inject
    public ExperimentController(ExperimentTrader experimentTrader,
                                ApplicationProperties applicationProperties,
                                DataFileHub dataFileHub){
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.dataFileHub = dataFileHub;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}")
    public String showExperimentPage(Model model,
                                     HttpServletRequest request,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(required = false) String accessKey) {


        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        model.addAllAttributes(experiment.getAttributes());
        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));

        model.addAttribute("content", gson.toJson(
                experimentPageContentForExperiment(
                        experiment
                )
        ));

        return "foundation-experiment-page";
    }

    JsonObject experimentPageContentForExperiment(Experiment experiment){
        JsonObject result = new JsonObject();

        // the client can't know that otherwise and it needs that!
        result.addProperty("experimentAccession", experiment.getAccession());
        result.addProperty("experimentType", experiment.getType().name());
        result.addProperty("species", experiment.getSpecies().getReferenceName());

        JsonArray availableTabs = new JsonArray();
        // everything wants to have a heatmap
        availableTabs.add(heatmapTab(groupingsForHeatmap(experiment)));

        availableTabs.add(customContentTab("dummy", "Gene Expression"));

        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
            availableTabs.add(customContentTab("experiment-design", "Experiment Design", new ExperimentDesignTable(experiment).asJson()));
        }
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.exists()){
            availableTabs.add(customContentTab("static-table", "Analysis Methods", "data",
                    formatTable(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get().readLine(0),
                                dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get().readLine(1)
                            )
            ));
        }

        availableTabs.add(customContentTab("dummy", "Download"));
        // and so on ! :)

        /*
        Some of these will be generic - ones that just show a picture.
        Some will always be there and I'm not sure if it's not too complicated to set the order up here
        but I like the loose coupling and simplicity.
        */

        result.add("tabs", availableTabs);

        return result;
    }

    JsonArray formatTable(String [] properties, String [] values){
        Preconditions.checkState(properties.length == values.length);

        JsonArray result = new JsonArray();
        for(int i = 0 ; i< properties.length ; i ++){
            result.add(twoElementArray(properties[i], values[i]));
        }

        return result;
    }

    JsonArray twoElementArray(String x, String y){
        JsonArray result = new JsonArray();
        result.add(new JsonPrimitive(x));
        result.add(new JsonPrimitive(y));
        return result;
    }

    JsonObject customContentTab(String tabType, String name, String onlyPropName, JsonElement value){
        JsonObject props =  new JsonObject();
        props.add(onlyPropName, value);
        return customContentTab(tabType, name, props);
    }

    JsonObject customContentTab(String tabType, String name){
        return customContentTab(tabType, name, new JsonObject());
    }

    JsonObject customContentTab(String tabType, String name, JsonObject props){
        JsonObject result = new JsonObject();
        result.addProperty("type", tabType);
        result.addProperty("name", name);
        result.add("props", props);
        return result;
    }

    JsonObject heatmapTab(JsonArray groups){
        return customContentTab("heatmap", "Heatmap", "groups", groups);
    }

    private JsonObject groupForFilterType(String filterType, List<String> defaultValues,
                                          Map<String, Collection<String>> groupingValuesPerGrouping){
        JsonObject result = new JsonObject();
        result.addProperty("name", filterType);
        result.add("selected",
                defaultValues.size() == 0
                        ? new JsonPrimitive("all")
                        : gson.toJsonTree(defaultValues)
        );

        JsonArray groupings = new JsonArray();
        for(Map.Entry<String, Collection<String>> e: groupingValuesPerGrouping.entrySet()){
            JsonArray grouping = new JsonArray();
            grouping.add(new JsonPrimitive(e.getKey()));
            JsonArray groupingValues = new JsonArray();
            for(String groupingValue : uniqueSublist(e.getValue())){
                groupingValues.add(new JsonPrimitive(groupingValue));
            }
            grouping.add(groupingValues);
            groupings.add(grouping);
        }

        result.add("groupings", groupings);

        return result;
    }

    private List<String> uniqueSublist(Collection<String> collection){
        List<String> result = new ArrayList<>();
        for(String element: collection){
            if(!result.contains(element)){
                result.add(element);
            }
        }
        return result;
    }

    JsonArray groupingsForHeatmap(Experiment<? extends DescribesDataColumns> experiment){
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();
        ExperimentDisplayDefaults experimentDisplayDefaults = experiment.getDisplayDefaults();

        LinkedListMultimap<String, LinkedListMultimap<String, String>> filtersByType = LinkedListMultimap.create();

        //populate the keys in the order we want later (Linked map preserves insertion order)
        for(String factorHeader: experimentDisplayDefaults.prescribedOrderOfFilters()){
            filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
        }
        for(String factorHeader: experimentDesign.getFactorHeaders()){
            if(! experimentDisplayDefaults.prescribedOrderOfFilters().contains(Factor.normalize(factorHeader))){
                filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
            }
        }
        for(String sampleHeader: experimentDesign.getSampleHeaders()){
            if(! experimentDesign.getFactorHeaders().contains(sampleHeader)){
                filtersByType.put(Factor.normalize(sampleHeader), LinkedListMultimap.<String, String>create());
            }
        }

        // add the information about which headers go to which categories
        for(DescribesDataColumns dataColumnDescriptor: experiment.getDataColumnDescriptors()){
            for(String assayAnalyzedForThisDataColumn : dataColumnDescriptor.assaysAnalyzedForThisDataColumn()){
                for(Factor factor : experimentDesign.getFactors(assayAnalyzedForThisDataColumn)){
                    filtersByType.get(Factor.normalize(factor.getHeader())).get(0)
                            .put(factor.getValue(), dataColumnDescriptor.getId());
                }
                for(SampleCharacteristic sampleCharacteristic
                        : experimentDesign.getSampleCharacteristics(assayAnalyzedForThisDataColumn)){
                    if(filtersByType.containsKey(Factor.normalize(sampleCharacteristic.header()))){
                        filtersByType.get(Factor.normalize(sampleCharacteristic.header())).get(0)
                                .put(sampleCharacteristic.value(), dataColumnDescriptor.getId());
                    }
                }
            }
        }
        JsonArray result = new JsonArray();
        for(Map.Entry<String, LinkedListMultimap<String, String>> e : filtersByType.entries()){
            result.add(groupForFilterType(e.getKey(), experimentDisplayDefaults.defaultFilterValuesForFactor(e.getKey
                    ()), e.getValue().asMap()));
        }


        return result;
    }

}
