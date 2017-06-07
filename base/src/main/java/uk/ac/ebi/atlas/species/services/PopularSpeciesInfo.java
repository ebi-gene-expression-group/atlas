package uk.ac.ebi.atlas.species.services;

import com.google.auto.value.AutoValue;

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
}
