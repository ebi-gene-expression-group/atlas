package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Set;

public class ProfileStreamPipelineBuilder<P extends Profile<T, ?>, O extends ProfileStreamOptions<T>, T> {

    private ProfileStreamTransforms<P,T> profileStreamTransforms;

    public ProfileStreamPipelineBuilder(ProfileStreamTransforms<P,T> profileStreamTransforms){
        this.profileStreamTransforms = profileStreamTransforms;
    }

    public Iterable<P> build(Iterable<P> profiles, O options, GeneQueryResponse geneQueryResponse, boolean shouldAverageIntoGeneSets) {
        Set<T> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs =  geneQueryResponse.getAllGeneIds();
        Set<T> allQueryFactors = options.getAllQueryFactors();

        Iterable<P> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = profileStreamTransforms.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (shouldAverageIntoGeneSets) {
            profilesPipeline = profileStreamTransforms.averageIntoGeneSets(profilesPipeline, geneQueryResponse.getQueryTermsToIds());
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = options.isSpecific()
                    ? profileStreamTransforms.filterBySpecificQueryFactors(profilesPipeline, queryFactors,allQueryFactors)
                    : profileStreamTransforms.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }
}
