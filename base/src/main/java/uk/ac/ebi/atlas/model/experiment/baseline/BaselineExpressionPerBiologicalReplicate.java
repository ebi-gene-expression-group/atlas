package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.math.stat.descriptive.rank.Median;
import org.apache.commons.math.stat.descriptive.rank.Percentile;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.Expression;

import java.util.Arrays;
import java.util.Map;

public class BaselineExpressionPerBiologicalReplicate implements Expression {

    private final Map<BiologicalReplicate, BaselineExpression> data;

    public BaselineExpressionPerBiologicalReplicate(Map<BiologicalReplicate, BaselineExpression> data){
        this.data = data;
    }

    double[] expressionValuesSorted(){
        double[] result = new double[data.size()];
        int i = 0;
        for(BaselineExpression baselineExpression : data.values()){
            result[i++]=baselineExpression.getLevel();
        }
        Arrays.sort(result);
        return result;
    }

    @Override
    public double getLevel() {
        return new Median().evaluate(expressionValuesSorted());
    }

    @Override
    public JsonObject toJson() {
        /*
        WIP
        Indicate when
         */
        JsonObject result = new JsonObject();

        double[] expressionValuesSorted = expressionValuesSorted();
        JsonObject stats = new JsonObject();
        stats.addProperty("min", expressionValuesSorted[0]);
        stats.addProperty("lower_quartile", new Percentile(0.25).evaluate(expressionValuesSorted));
        stats.addProperty("median", new Median().evaluate(expressionValuesSorted));
        stats.addProperty("upper_quartile", new Percentile(0.75).evaluate(expressionValuesSorted));
        stats.addProperty("max", expressionValuesSorted[expressionValuesSorted.length-1]);
        result.add("stats", stats);

        JsonArray values = new JsonArray();
        data.entrySet().stream().map(e-> {
            JsonObject o = new JsonObject();
            o.addProperty("id", e.getKey().getId());
            JsonArray assays = new JsonArray();
            e.getKey().assaysAnalyzedForThisDataColumn().stream().forEach(assay -> assays.add(assay));
            o.add("assays", assays);
            o.addProperty("value", e.getValue().getLevel());
            return o;
        }).forEach(o -> {
            values.add(o);
        });
        result.add("values", values);

        return result;
    }
}
