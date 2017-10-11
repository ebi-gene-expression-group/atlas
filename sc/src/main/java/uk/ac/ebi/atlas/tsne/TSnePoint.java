package uk.ac.ebi.atlas.tsne;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import javax.annotation.Nullable;

@AutoValue
public abstract class   TSnePoint  implements Comparable<TSnePoint> {
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

    @Override
    public int compareTo(@Nullable TSnePoint o) {
        return o == null ? -1 : this.name().compareTo(o.name());
    }

    // Public static method returning a TypeAdapter<Foo> is what tells auto-value-gson to create a TypeAdapter for Foo
    public static TypeAdapter<TSnePoint> typeAdapter(Gson gson) {
        return new AutoValue_TSnePoint.GsonTypeAdapter(gson);
    }
}
