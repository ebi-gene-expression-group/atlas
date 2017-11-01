package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.math.stat.descriptive.rank.Median;
import org.apache.commons.math.stat.descriptive.rank.Percentile;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.Expression;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

public class BaselineExpressionPerBiologicalReplicate implements Expression {

    public final Map<BiologicalReplicate, BaselineExpression> data;

    public BaselineExpressionPerBiologicalReplicate(Map<BiologicalReplicate, BaselineExpression> data) {
        this.data = data;
    }

    private double[] expressionValuesSorted() {
        return data.values().stream().mapToDouble(BaselineExpression::getLevel).sorted().toArray();
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
        stats.addProperty("max", expressionValuesSorted[expressionValuesSorted.length - 1]);
        result.add("stats", stats);

        JsonArray values = new JsonArray();
        data.entrySet().stream().map(e -> {
            JsonObject o = new JsonObject();
            o.addProperty("id", e.getKey().getId());
            JsonArray assays = new JsonArray();
            e.getKey().assaysAnalyzedForThisDataColumn().stream().sorted().forEach(assays::add);
            o.add("assays", assays);
            o.addProperty("value", e.getValue().getLevel());
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
}
