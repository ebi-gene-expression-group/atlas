package uk.ac.ebi.atlas.model.baseline;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.lang.reflect.Array;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

public class QuartilesArrayBuilder {

    public static double[] create(double[] values) {
        checkArgument(Array.getLength(values) == 5 || Array.getLength(values) == 0, "expected 5 or no values for quartiles but got " + values);
        return values;
    }

    public static double[] create(String commaSeparatedValues) {
        Iterable<String> parsedValues = Splitter.on(",").split(commaSeparatedValues);

        checkArgument(Iterables.size(parsedValues) == 5, "expected 5 values for quartiles but got " + commaSeparatedValues);

        Iterator<String> iterator = parsedValues.iterator();

        double min = Double.parseDouble(iterator.next());
        double lower = Double.parseDouble(iterator.next());
        double median = Double.parseDouble(iterator.next());
        double upper = Double.parseDouble(iterator.next());
        double max = Double.parseDouble(iterator.next());

        return new double[] {min, lower, median, upper, max};
    }
}
