package uk.ac.ebi.atlas.model.baseline;

import com.google.auto.value.AutoValue;

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

}
