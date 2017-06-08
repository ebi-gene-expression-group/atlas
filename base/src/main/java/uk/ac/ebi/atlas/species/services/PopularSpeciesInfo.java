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

    static Comparator<PopularSpeciesInfo> ReverseComparator = new Comparator<PopularSpeciesInfo>() {
        @Override
        public int compare(PopularSpeciesInfo o1, PopularSpeciesInfo o2) {
            return -Long.compare(o1.totalExperiments(), o2.totalExperiments());
        }
    };
}
