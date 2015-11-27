package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableSet;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class BaselineExperimentAssayGroups {

    public static boolean hasAllSameSpecies(SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups) {
        if (baselineExperimentAssayGroups.isEmpty()) {
            return true;
        }

        Iterator<BaselineExperimentAssayGroup> iterator = baselineExperimentAssayGroups.iterator();

        String firstSpecies = iterator.next().getSpecies();

        while (iterator.hasNext()) {
            if (!iterator.next().getSpecies().equals(firstSpecies)) {
                return false;
            }
        }

        return true;
    }


    public static boolean hasAnyTissueExperiment(SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups) {
        for (BaselineExperimentAssayGroup baselineExperimentAssayGroup : baselineExperimentAssayGroups) {
            if (baselineExperimentAssayGroup.isTissueExperiment()) {
                return true;
            }
        }
        return false;
    }


    public static SortedSet<BaselineExperimentAssayGroup> selectNonTissueExperiments(Set<BaselineExperimentAssayGroup> baselineExperimentAssayGroups) {
        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroupsSet = new TreeSet<>();

        for (BaselineExperimentAssayGroup beag : baselineExperimentAssayGroups) {
            if (!beag.isTissueExperiment()) {
                baselineExperimentAssayGroupsSet.add(beag);
            }
        }

        return baselineExperimentAssayGroupsSet;
    }


    public static ImmutableSet<BaselineExperimentAssayGroup> removeFirstAssayGroups(SortedSet<BaselineExperimentAssayGroup> assayGroups, int n) {
        ImmutableSet.Builder<BaselineExperimentAssayGroup> builder = ImmutableSet.builder();
        int count = 0;

        while (!assayGroups.isEmpty() && count < n) {
            builder.add(assayGroups.first());
            assayGroups.remove(assayGroups.first());
            count++;
        }

        return builder.build();
    }
}
