package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.base.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.experiment.baseline.Quartiles;

public class BaselineExpressionViewModel {

    private final String factorName;
    private final String color;
    private final double value;
    private final String svgPathId;
    private Quartiles quartiles;

    public BaselineExpressionViewModel(String factorName, String color, double value, String svgPathId,
                                       Optional<Quartiles> quartiles) {
        this.factorName = factorName;
        this.color = color;
        this.value = value;
        this.svgPathId = svgPathId;

        if (quartiles.isPresent()) {
            this.quartiles = quartiles.get();
        }
    }

    public JsonElement toJson(){
        JsonObject result = new JsonObject();
        result.addProperty("factorName", factorName);
        result.addProperty("color", color);
        result.add("value", Double.isNaN(value) ? JsonNull.INSTANCE: new JsonPrimitive(value));
        result.addProperty("svgPathId", svgPathId);
        if(quartiles!=null){
            result.add("quartiles",quartiles.toJson());
        }
        return result;
    }

}
