package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.DescribesDataColumns;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

/*
I'd prefer to be in experiment design but I need to do
experiment.propertiesForAssay(runOrAssay)
One idea: pass in a function to the constructor of experiment design, made from the list of contrasts or assay groups, that does this instead
 */
public class ExperimentDesignTable {
    private final Experiment<? extends DescribesDataColumns> experiment;

    public ExperimentDesignTable(Experiment<? extends DescribesDataColumns> experiment){
        this.experiment = experiment;
    }

    public JsonObject asJson() {

        JsonArray headers = threeElementArray(
                headerGroup("", experiment.getExperimentDesign().getAssayHeaders()),
                headerGroup("Sample Characteristics", experiment.getExperimentDesign().getSampleHeaders()),
                headerGroup("Experimental Variables", experiment.getExperimentDesign().getFactorHeaders())
        );

        JsonArray data = new JsonArray();
        for(String runOrAssay: experiment.getExperimentDesign().getAllRunOrAssay()){
            data.add(dataRow(runOrAssay));
        }

        JsonObject result = new JsonObject();
        result.add("headers", headers);
        result.add("data", data);

        return result;
    }


    JsonObject headerGroup(String name, Collection<String> members){
        JsonObject result = new JsonObject();
        result.addProperty("name", name);
        result.add("values", GSON.toJsonTree(members));
        return result;
    }

    JsonArray threeElementArray(JsonElement assay, JsonElement sample, JsonElement factor){
        JsonArray result = new JsonArray();
        result.add(assay);
        result.add(sample);
        result.add(factor);
        return result;
    }

    JsonObject dataRow(final String runOrAssay){
        JsonObject result = new JsonObject();

        result.add("properties", experiment.propertiesForAssay(runOrAssay));

        result.add("values", threeElementArray(
                GSON.toJsonTree(Strings.isNullOrEmpty(experiment.getExperimentDesign().getArrayDesign(runOrAssay)) ?
                        ImmutableList.of(runOrAssay) :
                        ImmutableList.of(
                                runOrAssay,
                                experiment.getExperimentDesign().getArrayDesign(runOrAssay))),
                GSON.toJsonTree(
                        Stream.of(experiment.getExperimentDesign().getSampleHeaders().toArray(new String[0]))
                              .map(sampleHeader -> experiment.getExperimentDesign().getSampleCharacteristic(runOrAssay, sampleHeader).value())
                              .collect(toList())),
                GSON.toJsonTree(
                        Stream.of(experiment.getExperimentDesign().getFactorHeaders().toArray(new String[0]))
                              .map(factorHeader -> experiment.getExperimentDesign().getFactorValue(runOrAssay, factorHeader))
                              .collect(toList()))));

        return result;
    }

}
