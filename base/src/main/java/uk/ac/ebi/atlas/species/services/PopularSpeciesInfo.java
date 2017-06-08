package uk.ac.ebi.atlas.species.services;

import com.google.auto.value.AutoValue;

import java.util.Comparator;

@AutoValue
abstract class PopularSpeciesInfo {
    static PopularSpeciesInfo create(String species, String kingdom, int baselineExperiments, int differentialExperiments) {
        return new AutoValue_PopularSpeciesInfo(species, kingdom, baselineExperiments + differentialExperiments, baselineExperiments, differentialExperiments);
    }

    abstract String species();
    abstract String kingdom();
    abstract int totalExperiments();
    abstract int baselineExperiments();
    abstract int differentialExperiments();

    static Comparator<PopularSpeciesInfo> ReverseComparator = new Comparator<PopularSpeciesInfo>() {
        @Override
        public int compare(PopularSpeciesInfo o1, PopularSpeciesInfo o2) {
            return -Integer.compare(o1.totalExperiments(), o2.totalExperiments());
        }
    };
}
