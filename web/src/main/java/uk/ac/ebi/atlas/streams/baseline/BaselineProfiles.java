package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneSet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class BaselineProfiles {

    public static Iterable<BaselineProfile> filterByGeneIds(Iterable<BaselineProfile> profiles, Set<String> uppercaseGeneIDs) {
        return profiles;
    }

    public static Iterable<BaselineProfile> filterByQueryFactors(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors) {
        return profiles;
    }

    public static Iterable<BaselineProfile> filterByQueryFactorSpecificity(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors) {
        return profiles;
    }

}