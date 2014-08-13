package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

public final class ProfileStreamFilters {

    private ProfileStreamFilters() {
    }

    public static <P extends Profile> Iterable<P> filterByGeneIds(Iterable<P> profiles, Set<String> geneIds) {
        return Iterables.filter(profiles, new IsGeneIdMatch(geneIds));
    }

    public static <K, P extends Profile<K, ?>>Iterable<P> filterByQueryFactors(Iterable<P> profiles, Set<K> queryFactors) {
        return Iterables.filter(profiles, new IsExpressedForQueryCondition<K, P>(queryFactors));
    }


}
