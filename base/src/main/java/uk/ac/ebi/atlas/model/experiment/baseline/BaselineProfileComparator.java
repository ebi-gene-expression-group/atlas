package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaselineProfileComparator implements Comparator<BaselineProfile> {

    // This value is just smaller than the smallest non-zero value we allow,
    // c.f. FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ZEROPOINTONE in
    // BaselineExpressionLevelRounder class
    private static final double CUTOFF_DIVISOR_DEFAULT_VALUE = 0.00000009;
    private static final int DEFAULT_CACHE_SIZE = 50;

    private final boolean isSpecific;
    private final Collection<AssayGroup> selectedQueryFactors;
    private final Collection<AssayGroup> allQueryFactors;
    private final Collection<AssayGroup> nonSelectedQueryFactorsCachedInstance;
    private final double cutoffDivisor;
    private final BaselineProfileCachedStats baselineProfileStats;

    public static Comparator<BaselineProfile> create(BaselineProfileStreamOptions options) {
        return new BaselineProfileComparator(
                options.isSpecific(),
                options.getDataColumnsToReturn(),
                options.getAllDataColumns(),
                options.getCutoff(),
                options.getHeatmapMatrixSize() + 1);    // The top ranked rows plus the profile we’re evaluating
    }

    protected BaselineProfileComparator(boolean isSpecific,
                                        Collection<AssayGroup> selectedQueryFactors,
                                        Collection<AssayGroup> allQueryFactors,
                                        double cutoff) {
        this(isSpecific, selectedQueryFactors, allQueryFactors, cutoff, DEFAULT_CACHE_SIZE);
    }

    protected BaselineProfileComparator(boolean isSpecific,
                                        Collection<AssayGroup> selectedQueryFactors,
                                        Collection<AssayGroup> allQueryFactors,
                                        double cutoff,
                                        int cacheSize) {
        this.isSpecific = isSpecific;
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;

        this.nonSelectedQueryFactorsCachedInstance =
                Sets.difference(ImmutableSet.copyOf(allQueryFactors), ImmutableSet.copyOf(selectedQueryFactors));

        cutoffDivisor = cutoff != 0 ? cutoff : CUTOFF_DIVISOR_DEFAULT_VALUE;

        baselineProfileStats = new BaselineProfileCachedStats(cacheSize);
    }

    @Override
    public int compare(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile) {

        boolean allContrastsSelected = nonSelectedQueryFactorsCachedInstance.isEmpty();

        if (isSpecific && allContrastsSelected) {
            int order = ComparisonChain
                    .start()
                    .compare(firstBaselineProfile.getSpecificity(),otherBaselineProfile.getSpecificity() )
                    .result();

            return 0 != order ?
                    order :
                    compareOnAverageExpressionLevelOverAllQueryFactors(firstBaselineProfile, otherBaselineProfile);
        }

        if (isSpecific && !allContrastsSelected) {
            // reverse because we want lower values to come first
            return Ordering.natural().reverse().compare(
                    getExpressionLevelFoldChange(firstBaselineProfile),
                    getExpressionLevelFoldChange(otherBaselineProfile));
        }

        if (!isSpecific && allContrastsSelected) {
            return compareOnAverageExpressionLevelOverAllQueryFactors(firstBaselineProfile, otherBaselineProfile);
        }

        // !isSpecific && !allContrastsSelected
        return compareOnAverageExpressionLevelOverSelectedQueryFactors(firstBaselineProfile, otherBaselineProfile);

    }

    protected int compareOnAverageExpressionLevelOverAllQueryFactors(BaselineProfile firstBaselineProfile,
                                                                     BaselineProfile otherBaselineProfile) {
        return Ordering.natural().reverse().
                compare(baselineProfileStats.getAverageOverAllQueryFactors(firstBaselineProfile),
                        baselineProfileStats.getAverageOverAllQueryFactors(otherBaselineProfile));
    }

    protected int compareOnAverageExpressionLevelOverSelectedQueryFactors(BaselineProfile firstBaselineProfile,
                                                                          BaselineProfile otherBaselineProfile) {
        return Ordering.natural().reverse().
                compare(baselineProfileStats.getAverageOverSelectedQueryFactors(firstBaselineProfile),
                        baselineProfileStats.getAverageOverSelectedQueryFactors(otherBaselineProfile));
    }

    protected int compareOnAverageExpressionLevel(BaselineProfile firstBaselineProfile,
                                                  BaselineProfile otherBaselineProfile,
                                                  Collection<AssayGroup> assayGroups) {

        return Ordering.natural().reverse().
                compare(firstBaselineProfile.getAverageExpressionLevelOn(assayGroups),
                        otherBaselineProfile.getAverageExpressionLevelOn(assayGroups));
    }

    public double getExpressionLevelFoldChange(BaselineProfile baselineProfile) {

        double averageExpressionLevelOnSelectedQueryFactors =
                baselineProfileStats.getAverageOverSelectedQueryFactors(baselineProfile);
        double maxExpressionLevelOnNonSelectedQueryFactors =
                baselineProfileStats.getMaxOverNonSelectedQueryFactors(baselineProfile);

        if (maxExpressionLevelOnNonSelectedQueryFactors == 0) {
            if (nonSelectedQueryFactorsCachedInstance.isEmpty()) {
                return averageExpressionLevelOnSelectedQueryFactors;
            }
            return averageExpressionLevelOnSelectedQueryFactors / cutoffDivisor;
        }

        return averageExpressionLevelOnSelectedQueryFactors / maxExpressionLevelOnNonSelectedQueryFactors;
    }

    static private <K, V> Map<K, V> createLRUMap(final int maxEntries) {
        return new LinkedHashMap<K, V>(maxEntries, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }

    private class BaselineProfileCachedStats {
        private final Map<String, Double> averageOverSelectedQueryFactors;
        private final Map<String, Double> averageOverAllQueryFactors;
        private final Map<String, Double> maxOverNonSelectedQueryFactors;

        private BaselineProfileCachedStats(int cacheSize) {
            averageOverSelectedQueryFactors = createLRUMap(cacheSize);
            averageOverAllQueryFactors = createLRUMap(cacheSize);
            maxOverNonSelectedQueryFactors = createLRUMap(cacheSize);
        }

        double getAverageOverSelectedQueryFactors(BaselineProfile baselineProfile) {
            if(!averageOverSelectedQueryFactors.containsKey(baselineProfile.getId())) {
                averageOverSelectedQueryFactors.put(
                        baselineProfile.getId(), baselineProfile.getAverageExpressionLevelOn(selectedQueryFactors));
            }
            return averageOverSelectedQueryFactors.get(baselineProfile.getId());
        }

        double getAverageOverAllQueryFactors(BaselineProfile baselineProfile) {
            if(!averageOverAllQueryFactors.containsKey(baselineProfile.getId())) {
                averageOverAllQueryFactors.put(
                        baselineProfile.getId(), baselineProfile.getAverageExpressionLevelOn(allQueryFactors));
            }
            return averageOverAllQueryFactors.get(baselineProfile.getId());
        }

        double getMaxOverNonSelectedQueryFactors(BaselineProfile baselineProfile) {
            if(!maxOverNonSelectedQueryFactors.containsKey(baselineProfile.getId())) {
                maxOverNonSelectedQueryFactors.put(
                        baselineProfile.getId(),
                        baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactorsCachedInstance));
            }
            return maxOverNonSelectedQueryFactors.get(baselineProfile.getId());
        }
    }

}