package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneSet;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfileStreamPipelineBuilder <T extends BaselineProfileStreamOptions>
        implements ProfileStreamPipelineBuilder<BaselineProfile,T> {

    @Override
    public Iterable<BaselineProfile> build(Iterable<BaselineProfile> profiles, T options) {
        Set<Factor> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = options.getSelectedGeneIDs();
        Set<Factor> allQueryFactors = options.getAllQueryFactors();
        boolean asGeneSets = options.asGeneSets();

        Iterable<BaselineProfile> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = ProfileStreamFilters.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (asGeneSets) {
            ImmutableSetMultimap<String,String> geneSetIdsToGeneIds = options.getGeneSetIdsToGeneIds();
            profilesPipeline = averageIntoGeneSets(profilesPipeline, geneSetIdsToGeneIds);
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = options.isSpecific() ?
                    filterByQueryFactorSpecificity(profilesPipeline, queryFactors, allQueryFactors) :
                    ProfileStreamFilters.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    public static Iterable<BaselineProfile> filterByQueryFactorSpecificity(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors, Set<Factor> allQueryFactors) {
        IsBaselineProfileSpecific isBaselineProfileSpecific = new IsBaselineProfileSpecific(queryFactors, allQueryFactors);
        return Iterables.filter(profiles, isBaselineProfileSpecific);
    }

    private static Iterable<BaselineProfile> averageIntoGeneSets(Iterable<BaselineProfile> profiles,
                                                        ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        ImmutableList.Builder<BaselineProfile> builder = ImmutableList.builder();

        ImmutableMap<String, GeneSet> geneSets = createGeneSetsById(geneSetIdsToGeneIds.keySet());

        ImmutableSetMultimap<String, String> geneIdsToGeneSetIds = geneSetIdsToGeneIds.inverse();

        for (BaselineProfile profile : profiles) {
            String geneId = profile.getId();
            for (String geneSetId: geneIdsToGeneSetIds.get(geneId)){
                geneSets.get(geneSetId).addBaselineProfile(profile);
            }
        }

        for(GeneSet geneSet: geneSets.values()){
            BaselineProfile averageProfile = geneSet.getAverageProfile();
            if (!averageProfile.isEmpty()){
                builder.add(averageProfile);
            }
        }

        return builder.build();
    }

    private static ImmutableMap<String, GeneSet> createGeneSetsById(Set<String> geneSetIds) {
        ImmutableMap.Builder<String, GeneSet> geneSetsBuilder = ImmutableMap.builder();

        for (String geneSetId: geneSetIds){
            GeneSet geneSet = new GeneSet(geneSetId);
            geneSetsBuilder.put(geneSetId, geneSet);
        }
        return geneSetsBuilder.build();
    }

}
