package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import javax.annotation.Nullable;
import java.util.Collection;

/*
I'd prefer to be in experiment design but I need to do
experiment.propertiesForAssay(runOrAssay)
One idea: pass in a function to the constructor of experiment design, made from the list of contrasts or assay groups, that does this instead
 */
public class ExperimentDesignTable {

    private final Experiment<DescribesDataColumns> experiment;
    private final Gson gson = new Gson();

    public ExperimentDesignTable(Experiment<DescribesDataColumns> experiment){
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
        result.add("values", gson.toJsonTree(members));
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
                gson.toJsonTree(Strings.isNullOrEmpty(experiment.getExperimentDesign().getArrayDesign(runOrAssay))
                        ? ImmutableList.of(runOrAssay) : ImmutableList.of(runOrAssay, experiment.getExperimentDesign().getArrayDesign(runOrAssay))
                ),
                gson.toJsonTree(FluentIterable.from(experiment.getExperimentDesign().getSampleHeaders())
                        .transform(new Function<String, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable String sampleHeader) {
                                return experiment.getExperimentDesign().getSampleCharacteristic(runOrAssay, sampleHeader).value();
                            }
                        }).toList()
                ),
                gson.toJsonTree(FluentIterable.from(experiment.getExperimentDesign().getFactorHeaders())
                        .transform(new Function<String, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable String factorHeader) {
                                return experiment.getExperimentDesign().getFactorValue(runOrAssay, factorHeader);
                            }
                        }).toList()
                )
        ));
        return result;
    }

}
