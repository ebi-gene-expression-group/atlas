package uk.ac.ebi.atlas.search.baseline;

import java.util.Iterator;
import java.util.SortedSet;

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

}
