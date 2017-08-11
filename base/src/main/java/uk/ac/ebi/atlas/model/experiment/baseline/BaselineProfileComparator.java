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

        this.nonSelectedQueryFactorsCachedInstance =
                Sets.difference(ImmutableSet.copyOf(allQueryFactors), ImmutableSet.copyOf(selectedQueryFactors));

        baselineProfileStats = new BaselineProfileCachedStats(cacheSize);
    }

    @Override
    public int compare(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile) {

        if(isSpecific) {
            return ComparisonChain.start().compare(
                    baselineProfileStats.getSpecificityOnNonSelectedQueryFactors(firstBaselineProfile),
                    baselineProfileStats.getSpecificityOnNonSelectedQueryFactors(otherBaselineProfile)
            ).compare(
                    baselineProfileStats.getSpecificity(firstBaselineProfile),
                    baselineProfileStats.getSpecificity(otherBaselineProfile)
            ).compare(
                    -baselineProfileStats.getExpressionLevelFoldChange(firstBaselineProfile),
                    -baselineProfileStats.getExpressionLevelFoldChange(otherBaselineProfile)
            ).result();
        } else {
            return Ordering.natural().reverse().
                    compare(baselineProfileStats.getAverageOverSelectedQueryFactors(firstBaselineProfile),
                            baselineProfileStats.getAverageOverSelectedQueryFactors(otherBaselineProfile));
        }

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
        private final Map<String, Double> expressionLevelFoldChange;
        private final Map<String, Long> specificityOnNonSelectedQueryFactors;
        private final  Map<String, Long> specificity;

        private BaselineProfileCachedStats(int cacheSize) {
            averageOverSelectedQueryFactors = createLRUMap(cacheSize);
            expressionLevelFoldChange = createLRUMap(cacheSize);
            specificityOnNonSelectedQueryFactors = createLRUMap(cacheSize);
            specificity = createLRUMap(cacheSize);
        }

        long getSpecificityOnNonSelectedQueryFactors(BaselineProfile baselineProfile){
            if(nonSelectedQueryFactorsCachedInstance.isEmpty()){
                return 0;
            }
            if(!specificityOnNonSelectedQueryFactors.containsKey(baselineProfile.getId())) {
                specificityOnNonSelectedQueryFactors.put(
                        baselineProfile.getId(), baselineProfile.getSpecificity(nonSelectedQueryFactorsCachedInstance));
            }
            return specificityOnNonSelectedQueryFactors.get(baselineProfile.getId());
        }

        long getSpecificity(BaselineProfile baselineProfile){
            if(!specificity.containsKey(baselineProfile.getId())) {
                specificity.put(
                        baselineProfile.getId(), baselineProfile.getSpecificity());
            }
            return specificity.get(baselineProfile.getId());
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