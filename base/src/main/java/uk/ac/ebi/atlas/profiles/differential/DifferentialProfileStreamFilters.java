package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;

import java.util.Set;

public class DifferentialProfileStreamFilters<P extends DifferentialProfile<? extends DifferentialExpression>> extends ProfileStreamFilters<P, Contrast> {

    @Override
    public Iterable<P> averageIntoGeneSets(Iterable<P> profiles,
                                           ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        //we do not average differential results into gene sets
        return profiles;
    }

    @Override
    public Iterable<P> filterBySpecificQueryFactors(Iterable<P> profiles,
                                                    Set<Contrast> queryFactors, Set<Contrast> allQueryFactors) {

        return Iterables.filter(profiles, new IsDifferentialProfileSpecific<P>(queryFactors, allQueryFactors));
    }
}
