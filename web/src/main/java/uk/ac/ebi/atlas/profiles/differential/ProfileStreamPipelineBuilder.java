package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.HashSet;
import java.util.Set;

public abstract class ProfileStreamPipelineBuilder<P extends Profile, O extends ProfileStreamOptions<T>, T> {

    public Iterable<P> build(Iterable<P> profiles, O options, Optional<GeneQueryResponse>
            geneQueryResponse) {
        Set<T> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = geneQueryResponse.isPresent()? geneQueryResponse.get().getAllGeneIds()
                : new HashSet<String>();
        Set<T> allQueryFactors = options.getAllQueryFactors();

        Iterable<P> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = ProfileStreamFilters.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (geneQueryResponse.isPresent()) {
            profilesPipeline = averageIntoGeneSets(profilesPipeline, geneQueryResponse.get().getQueryTermsToIds());
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = options.isSpecific() ?
                    Iterables.filter(profiles, queryFactorSpecificityPredicate(queryFactors, allQueryFactors)) :
                    ProfileStreamFilters.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    protected abstract Predicate<P> queryFactorSpecificityPredicate(Set<T> queryFactors, Set<T> allQueryFactors);

    protected abstract Iterable<P> averageIntoGeneSets(Iterable<P> profiles,
                                                       ImmutableSetMultimap<String, String>
                                                               geneSetIdsToGeneIds);
}
