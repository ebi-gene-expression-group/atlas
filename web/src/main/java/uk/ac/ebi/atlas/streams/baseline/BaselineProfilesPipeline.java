package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfilesPipeline {

    private final GeneSetBaselineProfilesFactory geneSetBaselineProfilesFactory;
    private boolean isSpecific;
    private Set<Factor> queryFactors = Collections.emptySet();
    private Set<String> uppercaseGeneIDs = Collections.emptySet();
    private Iterable<BaselineProfile> profiles;
    private ImmutableSetMultimap<String, String> geneSetIdsToGeneIds = ImmutableSetMultimap.of();
    private Set<Factor> allQueryFactors;

    @Inject
    public BaselineProfilesPipeline(GeneSetBaselineProfilesFactory geneSetBaselineProfilesFactory) {
        this.geneSetBaselineProfilesFactory = geneSetBaselineProfilesFactory;
    }

    public BaselineProfilesPipeline profiles(Iterable<BaselineProfile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public BaselineProfilesPipeline isSpecific(boolean isSpecific) {
        this.isSpecific = isSpecific;
        return this;
    }

    public BaselineProfilesPipeline isSpecific() {
        this.isSpecific = true;
        return this;
    }

    public BaselineProfilesPipeline queryFactors(Set<Factor> queryFactors) {
        this.queryFactors = queryFactors;
        return this;
    }

    public BaselineProfilesPipeline allQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    public BaselineProfilesPipeline selectGeneIDs(Set<String> uppercaseGeneIDs) {
        this.uppercaseGeneIDs = uppercaseGeneIDs;
        return this;
    }

    public BaselineProfilesPipeline averageIntoGeneSets(ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        this.geneSetIdsToGeneIds = geneSetIdsToGeneIds;
        return this;
    }

    public Iterable<BaselineProfile> lazyMap() {

            if (!uppercaseGeneIDs.isEmpty()) {
                profiles = BaselineProfilesPipeline.filterByGeneIds(profiles, uppercaseGeneIDs);
            }

            if (!geneSetIdsToGeneIds.isEmpty()) {
                profiles = geneSetBaselineProfilesFactory.averageIntoGeneSets(profiles, geneSetIdsToGeneIds);
            }

            if (!queryFactors.isEmpty()) {
                profiles = isSpecific ?
                        BaselineProfilesPipeline.filterByQueryFactorSpecificity(profiles, queryFactors, allQueryFactors) :
                        BaselineProfilesPipeline.filterByQueryFactors(profiles, queryFactors);
            }

            return profiles;
    }


    public static Iterable<BaselineProfile> filterByGeneIds(Iterable<BaselineProfile> profiles, Set<String> uppercaseGeneIDs) {
        return Iterables.filter(profiles, new GeneProfileInputStreamFilter.GeneIdMatchesPredicate(uppercaseGeneIDs));
    }

    public static Iterable<BaselineProfile> filterByQueryFactors(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors) {
        return Iterables.filter(profiles, new GeneProfileInputStreamFilter.ExpressedForQueryConditionPredicate<>(queryFactors));
    }

    public static Iterable<BaselineProfile> filterByQueryFactorSpecificity(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors, Set<Factor> allQueryFactors) {
        BaselineProfileIsSpecific baselineProfileIsSpecific = new BaselineProfileIsSpecific();
        baselineProfileIsSpecific.setSpecific(true);
        baselineProfileIsSpecific.setSelectedQueryFactors(queryFactors);
        baselineProfileIsSpecific.setAllQueryFactors(allQueryFactors);
        return Iterables.filter(profiles, baselineProfileIsSpecific);
    }


    public static class SpecificallyExpressedForQueryConditionPredicate<K> implements Predicate<Profile> {
        private Set<K> queryConditions;

        public SpecificallyExpressedForQueryConditionPredicate(Set<K> queryConditions){
            this.queryConditions = queryConditions;
        }

        @Override
        public boolean apply(Profile profile) {
            return CollectionUtils.isEmpty(queryConditions) || profile.isExpressedOnAnyOf(queryConditions);
        }

    }

}
