package uk.ac.ebi.atlas.species.services;

import com.google.auto.value.AutoValue;

import java.util.Comparator;

@AutoValue
public abstract class PopularSpeciesInfo {
    static PopularSpeciesInfo create(String species, String kingdom, long baselineExperiments, long differentialExperiments) {
        return new AutoValue_PopularSpeciesInfo(species, kingdom, baselineExperiments + differentialExperiments, baselineExperiments, differentialExperiments);
    }

    public abstract String species();
    public abstract String kingdom();
    public abstract long totalExperiments();
    public abstract long baselineExperiments();
    public abstract long differentialExperiments();

    static Comparator<PopularSpeciesInfo> ReverseComparator = (o1, o2) -> -Long.compare(o1.totalExperiments(), o2.totalExperiments());
}
