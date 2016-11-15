package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class DifferentialProfileComparator<T extends DifferentialProfile> implements Comparator<T> {

    private boolean isSpecific;
    private Set<Contrast> selectedQueryContrasts;
    private Set<Contrast> allQueryContrasts;
    private Regulation regulation;

    public DifferentialProfileComparator(boolean isSpecific, Set<Contrast> selectedQueryContrasts,
                                         Set<Contrast> allQueryContrasts, Regulation regulation) {
        this.isSpecific = isSpecific;
        this.selectedQueryContrasts = selectedQueryContrasts;
        this.allQueryContrasts = allQueryContrasts;
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.regulation = regulation;
    }

    @Override
    public int compare(T firstProfile, T otherProfile) {

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedQueryContrasts)) {
            int order = Integer.compare(firstProfile.getSpecificity(regulation), otherProfile.getSpecificity(regulation));
            if (0 == order) {
                order = compareOnAverageExpressionLevel(firstProfile, otherProfile, allQueryContrasts);
            }
            if (0 == order) {
                order = compareOnAveragePValue(firstProfile, otherProfile, allQueryContrasts);
            }
            if (0 == order) {
                order = firstProfile.getName().compareTo(otherProfile.getName());
            }
            return order;
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedQueryContrasts)) {
            int order = Ordering.natural().compare(
                    getExpressionLevelFoldChange(firstProfile),
                    getExpressionLevelFoldChange(otherProfile));
            if (0 == order) {
                order = compareOnAverageExpressionLevel(firstProfile, otherProfile, selectedQueryContrasts);
            }
            if (0 == order) {
                order = compareOnAveragePValue(firstProfile, otherProfile, selectedQueryContrasts);
            }
            if (0 == order) {
                order = firstProfile.getName().compareTo(otherProfile.getName());
            }
            return order;

        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedQueryContrasts)) {
            int order = compareOnAverageExpressionLevel(firstProfile, otherProfile, allQueryContrasts);
            if (0 == order) {
                order = compareOnAveragePValue(firstProfile, otherProfile, allQueryContrasts);
            }
            if (0 == order) {
                order = firstProfile.getName().compareTo(otherProfile.getName());
            }
            return order;
        }

        // B2 - !specific && !CollectionUtils.isEmpty
        int order = compareOnAverageExpressionLevel(firstProfile, otherProfile, selectedQueryContrasts);
        if (0 == order) {
            order = compareOnAveragePValue(firstProfile, otherProfile, selectedQueryContrasts);
        }
        if (0 == order) {
            order = firstProfile.getName().compareTo(otherProfile.getName());
        }
        return order;

    }

    private int compareOnAveragePValue(DifferentialProfile<?> firstProfile, DifferentialProfile<?> otherProfile, Set<Contrast> contrasts) {
        double firstProfileAverageExpressionLevel = firstProfile.getAveragePValueOn(contrasts);
        double otherProfileAverageExpressionLevel = otherProfile.getAveragePValueOn(contrasts);
        return Double.compare(firstProfileAverageExpressionLevel, otherProfileAverageExpressionLevel);
    }

    private int compareOnAverageExpressionLevel(DifferentialProfile<?> firstProfile, DifferentialProfile<?> otherProfile,
                                                  Set<Contrast> contrasts) {
        double firstProfileAverageExpressionLevel = firstProfile.getAverageExpressionLevelOn(contrasts);
        double otherProfileAverageExpressionLevel = otherProfile.getAverageExpressionLevelOn(contrasts);
        return Double.compare(otherProfileAverageExpressionLevel, firstProfileAverageExpressionLevel);
    }

    public double getExpressionLevelFoldChange(DifferentialProfile<?> differentialProfile) {

        Set<Contrast> nonSelectedQueryContrasts = Sets.difference(allQueryContrasts, selectedQueryContrasts);

        double minExpressionLevelOnNonSelectedQueryContrasts = differentialProfile.getStrongestExpressionLevelOn(nonSelectedQueryContrasts);

        double averageExpressionLevelOnSelectedQueryContrasts = differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts);

        if (averageExpressionLevelOnSelectedQueryContrasts == 0) {
            return minExpressionLevelOnNonSelectedQueryContrasts / Double.MIN_VALUE;
        }

        return minExpressionLevelOnNonSelectedQueryContrasts / averageExpressionLevelOnSelectedQueryContrasts;
    }

}