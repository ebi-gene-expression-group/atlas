package uk.ac.ebi.atlas.tsne;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Comparator;

@AutoValue
public abstract class TSnePoint {
    abstract double x();
    abstract double y();
    public abstract double expressionLevel();
    abstract String name();

    public static TSnePoint create(double x, double y, double expressionLevel, String name) {
        return new AutoValue_TSnePoint(x, y, expressionLevel, name);
    }

    public static TSnePoint create(double x, double y, String name) {
        return create(x, y, 0.0, name);
    }

    // Public static method returning a TypeAdapter<Foo> is what tells auto-value-gson to create a TypeAdapter for Foo
    public static TypeAdapter<TSnePoint> typeAdapter(Gson gson) {
        return new AutoValue_TSnePoint.GsonTypeAdapter(gson);
    }

    private static final Comparator<TSnePoint> NAME_COMPARATOR = new NameComparator();
    public static Comparator<TSnePoint> getNameComparator() {
        return NAME_COMPARATOR;
    }

    private static class NameComparator implements Comparator<TSnePoint> {
        @Override
        public int compare(TSnePoint o1, TSnePoint o2) {
            return o1.name().compareTo(o2.name());
        }
    }
}
