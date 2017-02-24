package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Function;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.GeneSet;
import uk.ac.ebi.atlas.profiles.ProfileStreamTransforms;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import javax.annotation.Nullable;
import java.util.Set;

public class BaselineProfileStreamTransforms extends ProfileStreamTransforms<AssayGroup, BaselineProfile> {


    public BaselineProfileStreamTransforms(BaselineProfileStreamOptions options,
                                            GeneQueryResponse geneQueryResponse, boolean asGeneSets) {
        if (!geneQueryResponse.getAllGeneIds().isEmpty()) {
            register(keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds()));
        }
        if (asGeneSets) {
            register(averageIntoGeneSets(geneQueryResponse.getQueryTermsToIds()));
        }
        if (!options.getSelectedQueryFactors().isEmpty()) {
            if (options.isSpecific()) {
                register(keepOnlyProfilesOverExpressedOnColumns(options.getSelectedQueryFactors(), options.getAllQueryFactors()));
            } else {
                register(keepOnlyProfilesExpressedOnColumns(options.getSelectedQueryFactors()));
            }
        }
    }

    private Function<Iterable<BaselineProfile>, Iterable<BaselineProfile>> averageIntoGeneSets(final ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        return new Function<Iterable<BaselineProfile>, Iterable<BaselineProfile>>() {
            @Nullable
            @Override
            public Iterable<BaselineProfile> apply(@Nullable Iterable<BaselineProfile> baselineProfiles) {
                ImmutableList.Builder<BaselineProfile> builder = ImmutableList.builder();

                ImmutableMap<String, GeneSet> geneSets = createGeneSetsById(geneSetIdsToGeneIds.keySet());

                ImmutableSetMultimap<String, String> geneIdsToGeneSetIds = geneSetIdsToGeneIds.inverse();

                for (BaselineProfile profile : baselineProfiles) {
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
        };
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
