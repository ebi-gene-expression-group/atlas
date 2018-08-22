package uk.ac.ebi.atlas.species.services;

import com.google.auto.value.AutoValue;

import java.util.Comparator;

@AutoValue
public abstract class PopularSpeciesInfo {
    public abstract String species();
    public abstract String kingdom();
    public abstract long totalExperiments();
    public abstract long baselineExperiments();
    public abstract long differentialExperiments();

    static PopularSpeciesInfo create(String species,
                                     String kingdom,
                                     long baselineExperiments,
                                     long differentialExperiments) {
        return new AutoValue_PopularSpeciesInfo(
                species,
                kingdom,
                baselineExperiments + differentialExperiments,
                baselineExperiments,
                differentialExperiments);
    }

    static final Comparator<PopularSpeciesInfo> BY_SIZE_DESCENDING =
            (o1, o2) -> -Long.compare(o1.totalExperiments(), o2.totalExperiments());
}
