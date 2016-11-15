package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

public abstract class ProfileStreamFilters<P extends Profile<K,?>, K> {

    public abstract Iterable<P> averageIntoGeneSets(Iterable<P> profiles,
                                                  ImmutableSetMultimap<String, String> geneSetIdsToGeneIds);

    public abstract Iterable<P> filterBySpecificQueryFactors(Iterable<P> profiles, Set<K> queryFactors, Set<K>
            allQueryFactors);

    public Iterable<P> filterByGeneIds(Iterable<P> profiles, Set<String> geneIds) {
        return Iterables.filter(profiles, new IsGeneIdMatch(geneIds));
    }

    public Iterable<P> filterByQueryFactors(Iterable<P> profiles, Set<K> queryFactors) {
        return Iterables.filter(profiles, new IsExpressedForQueryCondition<K, P>(queryFactors));
    }


}
