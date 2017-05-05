package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

import java.util.Collection;
import java.util.Comparator;

public class DifferentialProfileComparator<T extends DifferentialProfile> implements Comparator<T> {

    private final boolean isSpecific;
    private final Collection<Contrast> selectedQueryContrasts;
    private final Collection<Contrast> allQueryContrasts;
    private final Collection<Contrast> nonSelectedQueryContrastsCachedInstance;
    private final Regulation regulation;

    public DifferentialProfileComparator(boolean isSpecific, Collection<Contrast> selectedQueryContrasts,
                                         Collection<Contrast> allQueryContrasts, Regulation regulation) {
        this.isSpecific = isSpecific;
        this.selectedQueryContrasts = selectedQueryContrasts;
        this.allQueryContrasts = allQueryContrasts;
        this.nonSelectedQueryContrastsCachedInstance = Sets.difference(ImmutableSet.copyOf(allQueryContrasts),
                ImmutableSet.copyOf(selectedQueryContrasts));
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.regulation = regulation;
    }

    public static <Prof extends DifferentialProfile> DifferentialProfileComparator<Prof> create
            (DifferentialProfileStreamOptions options) {
        return new DifferentialProfileComparator<>(options.isSpecific(),
                options.getDataColumnsToReturn(),
                options.getAllDataColumns(),
                options.getRegulation());
    }

    @Override
    public int compare(T firstProfile, T otherProfile) {

        // A1:
        if (isSpecific && selectedQueryContrasts.equals(allQueryContrasts)) {
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
        if (isSpecific && !selectedQueryContrasts.equals(allQueryContrasts)) {
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
        if (!isSpecific && selectedQueryContrasts.equals(allQueryContrasts)) {
            int order = compareOnAverageExpressionLevel(firstProfile, otherProfile, allQueryContrasts);
            if (0 == order) {
                order = compareOnAveragePValue(firstProfile, otherProfile, allQueryContrasts);
            }
            if (0 == order) {
                order = firstProfile.getName().compareTo(otherProfile.getName());
            }
            return order;
        }

        // B2 - !specific && !selectedQueryContrasts.equals(allQueryContrasts)
        int order = compareOnAverageExpressionLevel(firstProfile, otherProfile, selectedQueryContrasts);
        if (0 == order) {
            order = compareOnAveragePValue(firstProfile, otherProfile, selectedQueryContrasts);
        }
        if (0 == order) {
            order = firstProfile.getName().compareTo(otherProfile.getName());
        }
        return order;

    }

    private int compareOnAveragePValue(DifferentialProfile<?> firstProfile, DifferentialProfile<?> otherProfile, Collection<Contrast> contrasts) {
        double firstProfileAverageExpressionLevel = firstProfile.getAveragePValueOn(contrasts);
        double otherProfileAverageExpressionLevel = otherProfile.getAveragePValueOn(contrasts);
        return Double.compare(firstProfileAverageExpressionLevel, otherProfileAverageExpressionLevel);
    }

    private int compareOnAverageExpressionLevel(DifferentialProfile<?> firstProfile, DifferentialProfile<?> otherProfile,
                                                  Collection<Contrast> contrasts) {
        double firstProfileAverageExpressionLevel = firstProfile.getAverageExpressionLevelOn(contrasts);
        double otherProfileAverageExpressionLevel = otherProfile.getAverageExpressionLevelOn(contrasts);
        return Double.compare(otherProfileAverageExpressionLevel, firstProfileAverageExpressionLevel);
    }

    public double getExpressionLevelFoldChange(DifferentialProfile<?> differentialProfile) {

        double minExpressionLevelOnNonSelectedQueryContrasts = differentialProfile.getMaxExpressionLevelOn(nonSelectedQueryContrastsCachedInstance);

        double averageExpressionLevelOnSelectedQueryContrasts = differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts);

        if (averageExpressionLevelOnSelectedQueryContrasts == 0) {
            return minExpressionLevelOnNonSelectedQueryContrasts / Double.MIN_VALUE;
        }

        return minExpressionLevelOnNonSelectedQueryContrasts / averageExpressionLevelOnSelectedQueryContrasts;
    }

}