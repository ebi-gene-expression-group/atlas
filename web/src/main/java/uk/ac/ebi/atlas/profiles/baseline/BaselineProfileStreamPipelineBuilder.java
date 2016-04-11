package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneSet;
import uk.ac.ebi.atlas.profiles.ProfileStreamPipelineBuilder;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfileStreamPipelineBuilder<T extends BaselineProfileStreamOptions>
        extends ProfileStreamPipelineBuilder<BaselineProfile, T, Factor> {

    @Override
    protected Predicate<BaselineProfile> queryFactorSpecificityPredicate(Set<Factor> queryFactors, Set<Factor>
            allQueryFactors) {
        return new IsBaselineProfileSpecific(queryFactors, allQueryFactors);
    }

    @Override
    protected Iterable<BaselineProfile> averageIntoGeneSets(Iterable<BaselineProfile> profiles,
                                                            ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        ImmutableList.Builder<BaselineProfile> builder = ImmutableList.builder();

        ImmutableMap<String, GeneSet> geneSets = createGeneSetsById(geneSetIdsToGeneIds.keySet());

        ImmutableSetMultimap<String, String> geneIdsToGeneSetIds = geneSetIdsToGeneIds.inverse();

        for (BaselineProfile profile : profiles) {
            String geneId = profile.getId();
            for (String geneSetId : geneIdsToGeneSetIds.get(geneId)) {
                geneSets.get(geneSetId).addBaselineProfile(profile);
            }
        }

        for (GeneSet geneSet : geneSets.values()) {
            BaselineProfile averageProfile = geneSet.getAverageProfile();
            if (!averageProfile.isEmpty()) {
                builder.add(averageProfile);
            }
        }

        return builder.build();
    }

    private static ImmutableMap<String, GeneSet> createGeneSetsById(Set<String> geneSetIds) {
        ImmutableMap.Builder<String, GeneSet> geneSetsBuilder = ImmutableMap.builder();

        for (String geneSetId : geneSetIds) {
            GeneSet geneSet = new GeneSet(geneSetId);
            geneSetsBuilder.put(geneSetId, geneSet);
        }
        return geneSetsBuilder.build();
    }

}
