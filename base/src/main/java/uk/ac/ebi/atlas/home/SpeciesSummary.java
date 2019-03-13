package uk.ac.ebi.atlas.home;

import com.google.auto.value.AutoValue;

import java.util.Comparator;

@AutoValue
public abstract class SpeciesSummary {
    public abstract String getSpecies();
    public abstract String getKingdom();
    public abstract long getTotalExperiments();
    public abstract long getBaselineExperiments();
    public abstract long getDifferentialExperiments();

    public static SpeciesSummary create(String species,
                                        String kingdom,
                                        long baselineExperiments,
                                        long differentialExperiments) {
        return new AutoValue_SpeciesSummary(
                species,
                kingdom,
                baselineExperiments + differentialExperiments,
                baselineExperiments,
                differentialExperiments);
    }

    // Suitable for single cell experiment types, which are neither baseline, nor differential
    public static SpeciesSummary create(String species,
                                        String kingdom,
                                        long totalExperiments) {
        return new AutoValue_SpeciesSummary(
                species,
                kingdom,
                totalExperiments,
                0,
                0);
    }

    public static final Comparator<SpeciesSummary> BY_SIZE_DESCENDING =
            (o1, o2) -> -Long.compare(o1.getTotalExperiments(), o2.getTotalExperiments());
}
