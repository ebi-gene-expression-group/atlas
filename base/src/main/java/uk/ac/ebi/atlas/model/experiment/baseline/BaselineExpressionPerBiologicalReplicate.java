package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.math.stat.descriptive.rank.Median;
import org.apache.commons.math.stat.descriptive.rank.Percentile;
import org.apache.commons.math.util.MathUtils;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.Expression;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class BaselineExpressionPerBiologicalReplicate implements Expression {

    public final Map<BiologicalReplicate, BaselineExpression> data;

    public BaselineExpressionPerBiologicalReplicate(Map<BiologicalReplicate, BaselineExpression> data) {
        this.data = data;
    }

    private double[] expressionValuesSorted() {
        return expressionValues().sorted().toArray();
    }

    private DoubleStream expressionValues() {
        return data.values().stream().mapToDouble(BaselineExpression::getLevel);
    }

    @Override
    public double getLevel() {
        return new Median().evaluate(expressionValuesSorted());
    }

    @Override
    public JsonObject toJson() {
        if (data.size() == 0) return new JsonObject();
        JsonObject result = new JsonObject();

        double[] expressionValuesSorted = expressionValuesSorted();
        double total = expressionValues().sum();

        JsonObject stats = new JsonObject();
        stats.addProperty("total", total); //not corresponding to any UI feature but will be helpful
        stats.addProperty("min", expressionValuesSorted[0]);
        stats.addProperty("lower_quartile", new Percentile(0.25).evaluate(expressionValuesSorted));
        stats.addProperty("median", new Median().evaluate(expressionValuesSorted));
        stats.addProperty("upper_quartile", new Percentile(0.75).evaluate(expressionValuesSorted));
        stats.addProperty("max", expressionValuesSorted[expressionValuesSorted.length - 1]);
        result.add("stats", stats);

        JsonArray values = new JsonArray();
        data.entrySet().stream().map(e -> {
            JsonObject o = new JsonObject();
            o.addProperty("id", e.getKey().getId());
            JsonArray assays = new JsonArray();
            e.getKey().assaysAnalyzedForThisDataColumn().stream().sorted().forEach(assays::add);
            o.add("assays", assays);
            JsonObject value = new JsonObject();
            double expressionLevel = e.getValue().getLevel();
            value.addProperty("expression_absolute_units", expressionLevel);
            value.addProperty("expression_as_fraction_of_total",(total == 0) ? 0 : MathUtils.round(expressionLevel / total,3));
            value.addProperty("expression_dominance", dominanceAmongRelatedValues(expressionValuesSorted, expressionLevel).name());

            o.add("value", value);
            return o;
        }).forEach(values::add);
        result.add("values", values);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaselineExpressionPerBiologicalReplicate that = (BaselineExpressionPerBiologicalReplicate) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return MessageFormat.format("BaselineExpressionPerBiologicalReplicate{data={0}}", data);
    }

    /*
    assumes allValues contains value
     */
    DOMINANCE_AMONG_RELATED_VALUES dominanceAmongRelatedValues(double[] allValuesSortedInIncreasingOrder, double value){
        if(allValuesSortedInIncreasingOrder.length == 0 || value == 0) return DOMINANCE_AMONG_RELATED_VALUES.absent;
        if(allValuesSortedInIncreasingOrder.length == 1) return DOMINANCE_AMONG_RELATED_VALUES.dominant;

        double topValue = allValuesSortedInIncreasingOrder[allValuesSortedInIncreasingOrder.length - 1];
        double valueFollowingTopValue = allValuesSortedInIncreasingOrder[allValuesSortedInIncreasingOrder.length - 2];
        /*
        Definition quoted by Nuno: a transcript is dominant if it is twice as expressed as the next value
         */
        if(value == topValue && value >= valueFollowingTopValue * 2 ) return DOMINANCE_AMONG_RELATED_VALUES.dominant;

        return DOMINANCE_AMONG_RELATED_VALUES.present;
    }

    /*
    Please do not rename my values without checking if there is a dependency on name() values in the UI
     */
    enum DOMINANCE_AMONG_RELATED_VALUES {
        dominant, present, absent
    }
}
