package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ComparisonChain;
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

    private static final int DEFAULT_CACHE_SIZE = 50;

    private final boolean isSpecific;
    private final Collection<AssayGroup> selectedQueryFactors;
    private final Collection<AssayGroup> allQueryFactors;
    private final Collection<AssayGroup> nonSelectedQueryFactorsCachedInstance;
    private final BaselineProfileCachedStats baselineProfileStats;

    public static Comparator<BaselineProfile> create(BaselineProfileStreamOptions<?> options) {
        return new BaselineProfileComparator(
                options.isSpecific(),
                options.getDataColumnsToReturn(),
                options.getAllDataColumns(),
                options.getHeatmapMatrixSize() + 1);    // The top ranked rows plus the profile we’re evaluating
    }

    protected BaselineProfileComparator(boolean isSpecific,
                                        Collection<AssayGroup> selectedQueryFactors,
                                        Collection<AssayGroup> allQueryFactors) {
        this(isSpecific, selectedQueryFactors, allQueryFactors, DEFAULT_CACHE_SIZE);
    }

    private BaselineProfileComparator(boolean isSpecific,
                                        Collection<AssayGroup> selectedQueryFactors,
                                        Collection<AssayGroup> allQueryFactors,
                                        int cacheSize) {
        this.isSpecific = isSpecific;
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;

        this.nonSelectedQueryFactorsCachedInstance =
                Sets.difference(ImmutableSet.copyOf(allQueryFactors), ImmutableSet.copyOf(selectedQueryFactors));

        baselineProfileStats = new BaselineProfileCachedStats(cacheSize);
    }

    @Override
    public int compare(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile) {

        boolean allFactorsSelected = nonSelectedQueryFactorsCachedInstance.isEmpty();

        if (isSpecific && allFactorsSelected) {
            int order = ComparisonChain
                    .start()
                    .compare(firstBaselineProfile.getSpecificity(), otherBaselineProfile.getSpecificity())
                    .result();

            return 0 != order ?
                    order :
                    compareOnAverageExpressionLevelOverAllQueryFactors(firstBaselineProfile, otherBaselineProfile);
        }

        if (isSpecific && !allFactorsSelected) {
            int order = ComparisonChain
                    .start()
                    .compare(
                            firstBaselineProfile.getSpecificity(nonSelectedQueryFactorsCachedInstance),
                            otherBaselineProfile.getSpecificity(nonSelectedQueryFactorsCachedInstance))
                    .result();

            // reverse because we want lower values to come first
            return 0 != order ?
                    order :
                    Ordering.natural().reverse().compare(
                            getExpressionLevelFoldChange(firstBaselineProfile),
                            getExpressionLevelFoldChange(otherBaselineProfile));
        }

        if (!isSpecific && allFactorsSelected) {
            return compareOnAverageExpressionLevelOverAllQueryFactors(firstBaselineProfile, otherBaselineProfile);
        }

        // !isSpecific && !allFactorsSelected
        return compareOnAverageExpressionLevelOverSelectedQueryFactors(firstBaselineProfile, otherBaselineProfile);

    }

    private int compareOnAverageExpressionLevelOverAllQueryFactors(BaselineProfile firstBaselineProfile,
                                                                     BaselineProfile otherBaselineProfile) {
        return Ordering.natural().reverse().
                compare(baselineProfileStats.getAverageOverAllQueryFactors(firstBaselineProfile),
                        baselineProfileStats.getAverageOverAllQueryFactors(otherBaselineProfile));
    }

    private int compareOnAverageExpressionLevelOverSelectedQueryFactors(BaselineProfile firstBaselineProfile,
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
        return baselineProfileStats.getExpressionLevelFoldChange(baselineProfile);
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
        private final Map<String, Double> expressionLevelFoldChange;

        private BaselineProfileCachedStats(int cacheSize) {
            averageOverSelectedQueryFactors = createLRUMap(cacheSize);
            averageOverAllQueryFactors = createLRUMap(cacheSize);
            expressionLevelFoldChange = createLRUMap(cacheSize);
        }

        double getExpressionLevelFoldChange(BaselineProfile baselineProfile) {
            if(!expressionLevelFoldChange.containsKey(baselineProfile.getId())) {
                expressionLevelFoldChange.put(
                        baselineProfile.getId(), computeExpressionLevelFoldChange(baselineProfile));
            }
            return expressionLevelFoldChange.get(baselineProfile.getId());
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

        private double computeExpressionLevelFoldChange(BaselineProfile baselineProfile) {

            double averageExpressionLevelOnSelectedQueryFactors =
                    baselineProfileStats.getAverageOverSelectedQueryFactors(baselineProfile);
            double maxExpressionLevelOnNonSelectedQueryFactors =
                    baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactorsCachedInstance);

            if (maxExpressionLevelOnNonSelectedQueryFactors == 0) {
                return averageExpressionLevelOnSelectedQueryFactors;
            }

            return averageExpressionLevelOnSelectedQueryFactors / maxExpressionLevelOnNonSelectedQueryFactors;
        }
    }


}