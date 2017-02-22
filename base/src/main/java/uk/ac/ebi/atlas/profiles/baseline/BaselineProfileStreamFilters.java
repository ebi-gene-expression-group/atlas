package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.GeneSet;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;

import java.util.Set;

public class BaselineProfileStreamFilters extends ProfileStreamFilters<OldBaselineProfile, Factor> {

    @Override
    public Iterable<OldBaselineProfile> filterBySpecificQueryFactors(Iterable<OldBaselineProfile> profiles, Set<Factor> queryFactors, Set<Factor> allQueryFactors) {
        return Iterables.filter(profiles, new OldIsBaselineProfileSpecific(queryFactors, allQueryFactors));
    }

    @Override
    public Iterable<OldBaselineProfile> averageIntoGeneSets(Iterable<OldBaselineProfile> profiles,
                                                            ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        ImmutableList.Builder<OldBaselineProfile> builder = ImmutableList.builder();

        ImmutableMap<String, GeneSet> geneSets = createGeneSetsById(geneSetIdsToGeneIds.keySet());

        ImmutableSetMultimap<String, String> geneIdsToGeneSetIds = geneSetIdsToGeneIds.inverse();

        for (OldBaselineProfile profile : profiles) {
            String geneId = profile.getId();
            for (String geneSetId : geneIdsToGeneSetIds.get(geneId)) {
                geneSets.get(geneSetId).addBaselineProfile(profile);
            }
        }

        for (GeneSet geneSet : geneSets.values()) {
            OldBaselineProfile averageProfile = geneSet.getAverageProfile();
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
