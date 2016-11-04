package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Set;

public class ProfileStreamPipelineBuilder<P extends Profile, O extends ProfileStreamOptions<T>, T> {

    private ProfileStreamFilters<P,T> profileStreamFilters;

    public ProfileStreamPipelineBuilder(ProfileStreamFilters<P,T> profileStreamFilters){
        this.profileStreamFilters = profileStreamFilters;
    }

    public Iterable<P> build(Iterable<P> profiles, O options, GeneQueryResponse geneQueryResponse, boolean shouldAverageIntoGeneSets) {
        Set<T> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs =  geneQueryResponse.getAllGeneIds();
        Set<T> allQueryFactors = options.getAllQueryFactors();

        Iterable<P> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = profileStreamFilters.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (shouldAverageIntoGeneSets) {
            profilesPipeline = profileStreamFilters.averageIntoGeneSets(profilesPipeline, geneQueryResponse.getQueryTermsToIds());
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = options.isSpecific()
                    ? profileStreamFilters.filterBySpecificQueryFactors(profilesPipeline, queryFactors,allQueryFactors)
                    : profileStreamFilters.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }
}
