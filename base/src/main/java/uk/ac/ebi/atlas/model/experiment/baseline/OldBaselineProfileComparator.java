package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;

import java.util.Comparator;
import java.util.Set;

public class OldBaselineProfileComparator implements Comparator<OldBaselineProfile> {

    // This value is just smaller than the smallest non-zero value we allow,
    // c.f. FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ZEROPOINTONE in
    // BaselineExpressionLevelRounder class
    private static final double CUTOFF_DIVISOR_DEFAULT_VALUE = 0.00000009;

    private boolean isSpecific;
    private Set<Factor> selectedQueryFactors;
    private Set<Factor> allQueryFactors;
    private double cutoffDivisor;
    private Double minimumExpressionLevelToQualifyAsGoodForOurRule = null;
    private Double minimumFractionOfExpressionToQualifyAsGoodForOurRule = null;


    public static Comparator<OldBaselineProfile> create(BaselineProfileStreamOptions options) {
        return new OldBaselineProfileComparator(options.isSpecific(),
                options.getDataColumnsToReturn(),
                options.getAllDataColumns(),
                options.getCutoff(), options.getThresholdForPremium(), options.getFractionForPremium());
    }

    OldBaselineProfileComparator(boolean isSpecific, Set<Factor> selectedQueryFactors,
                                 Set<Factor> allQueryFactors, double cutoff) {
        this(isSpecific,selectedQueryFactors,allQueryFactors,cutoff,null,null);
    }
    protected OldBaselineProfileComparator(boolean isSpecific, Set<Factor> selectedQueryFactors,
                                           Set<Factor> allQueryFactors, double cutoff, Double
                                      minimumExpressionLevelToQualifyAsGoodForOurRule, Double minimumFractionOfExpressionToQualifyAsGoodForOurRule ) {
        this.isSpecific = isSpecific;
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;

        cutoffDivisor = cutoff != 0 ? cutoff : CUTOFF_DIVISOR_DEFAULT_VALUE;
        this.minimumExpressionLevelToQualifyAsGoodForOurRule=minimumExpressionLevelToQualifyAsGoodForOurRule;
        this.minimumFractionOfExpressionToQualifyAsGoodForOurRule = minimumFractionOfExpressionToQualifyAsGoodForOurRule;
    }

    @Override
    public int compare(OldBaselineProfile firstBaselineProfile, OldBaselineProfile otherBaselineProfile) {

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            int order = ComparisonChain
                    .start()
                    .compareTrueFirst(ourRule(firstBaselineProfile), ourRule(otherBaselineProfile))
                    .compare(firstBaselineProfile.getSpecificity(),otherBaselineProfile.getSpecificity() )
                    .result();
            return 0 != order ? order : compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, allQueryFactors);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedQueryFactors)) {
            // reverse because we want lower values to come first
            return Ordering.natural().reverse().compare(
                    getExpressionLevelFoldChange(firstBaselineProfile),
                    getExpressionLevelFoldChange(otherBaselineProfile));
        }


        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            return compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, allQueryFactors);
        }

        //B2 - !isSpecific && !CollectionUtils.isEmpty(selectedQueryFactors)
        return compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, selectedQueryFactors);

    }

    boolean ourRule(OldBaselineProfile baselineProfile){
        if(minimumExpressionLevelToQualifyAsGoodForOurRule == null ||
                minimumFractionOfExpressionToQualifyAsGoodForOurRule == null){
            return false;
        } else {
            return baselineProfile.getFactorsWithExpressionLevelAtLeast(minimumExpressionLevelToQualifyAsGoodForOurRule).size()
                    >= minimumFractionOfExpressionToQualifyAsGoodForOurRule * allQueryFactors.size();
        }
    }

    protected int compareOnAverageExpressionLevel(OldBaselineProfile firstBaselineProfile, OldBaselineProfile
            otherBaselineProfile,
                                                  Set<Factor> factors) {

        return Ordering.natural().reverse().
                compare(firstBaselineProfile.getAverageExpressionLevelOn(factors),
                        otherBaselineProfile.getAverageExpressionLevelOn(factors));
    }

    public double getExpressionLevelFoldChange(OldBaselineProfile baselineProfile) {

        double averageExpressionLevelOnSelectedQueryFactors = baselineProfile.getAverageExpressionLevelOn(selectedQueryFactors);

        Set<Factor> nonSelectedQueryFactors = Sets.difference(allQueryFactors, selectedQueryFactors);

        double maxExpressionLevelOnNonSelectedQueryFactors = baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactors);

        if (maxExpressionLevelOnNonSelectedQueryFactors == 0) {
            if (nonSelectedQueryFactors.isEmpty()) {
                return averageExpressionLevelOnSelectedQueryFactors;
            }
            return averageExpressionLevelOnSelectedQueryFactors / cutoffDivisor;
        }

        return averageExpressionLevelOnSelectedQueryFactors / maxExpressionLevelOnNonSelectedQueryFactors;
    }

}