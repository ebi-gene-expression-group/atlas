package uk.ac.ebi.atlas.model.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

@AutoValue
public abstract class Quartiles {

    public abstract double min();

    public abstract double lower();

    public abstract double median();

    public abstract double upper();

    public abstract double max();

    public static Quartiles create(double min, double lower, double median, double upper, double max) {
        return new AutoValue_Quartiles(min, lower, median, upper, max);
    }

    public static Quartiles createFromCsvString(String csv) {
        Iterable<String> values = Splitter.on(",").split(csv);

        checkArgument(Iterables.size(values) == 5, "expected 5 values for quartiles but got " + csv);

        Iterator<String> iterator = values.iterator();

        double min = Double.parseDouble(iterator.next());
        double lower = Double.parseDouble(iterator.next());
        double median = Double.parseDouble(iterator.next());
        double upper = Double.parseDouble(iterator.next());
        double max = Double.parseDouble(iterator.next());

        return Quartiles.create(min, lower, median, upper, max);
    }

}
