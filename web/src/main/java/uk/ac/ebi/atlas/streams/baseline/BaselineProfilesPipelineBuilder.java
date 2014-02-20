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
public class BaselineProfilesPipelineBuilder {

    private final GeneSetBaselineProfilesFactory geneSetBaselineProfilesFactory;
    private boolean isSpecific;
    private Set<Factor> queryFactors = Collections.emptySet();
    private Set<String> uppercaseGeneIDs = Collections.emptySet();
    private Iterable<BaselineProfile> profiles;
    private ImmutableSetMultimap<String, String> geneSetIdsToGeneIds = ImmutableSetMultimap.of();
    private Set<Factor> allQueryFactors;

    @Inject
    public BaselineProfilesPipelineBuilder(GeneSetBaselineProfilesFactory geneSetBaselineProfilesFactory) {
        this.geneSetBaselineProfilesFactory = geneSetBaselineProfilesFactory;
    }

    public BaselineProfilesPipelineBuilder profiles(Iterable<BaselineProfile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public BaselineProfilesPipelineBuilder isSpecific(boolean isSpecific) {
        this.isSpecific = isSpecific;
        return this;
    }

    public BaselineProfilesPipelineBuilder isSpecific() {
        this.isSpecific = true;
        return this;
    }

    public BaselineProfilesPipelineBuilder queryFactors(Set<Factor> queryFactors) {
        this.queryFactors = queryFactors;
        return this;
    }

    public BaselineProfilesPipelineBuilder allQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    public BaselineProfilesPipelineBuilder selectGeneIDs(Set<String> uppercaseGeneIDs) {
        this.uppercaseGeneIDs = uppercaseGeneIDs;
        return this;
    }

    public BaselineProfilesPipelineBuilder averageIntoGeneSets(ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        this.geneSetIdsToGeneIds = geneSetIdsToGeneIds;
        return this;
    }

    public Iterable<BaselineProfile> build() {

            if (!uppercaseGeneIDs.isEmpty()) {
                profiles = BaselineProfilesPipelineBuilder.filterByGeneIds(profiles, uppercaseGeneIDs);
            }

            if (!geneSetIdsToGeneIds.isEmpty()) {
                profiles = geneSetBaselineProfilesFactory.averageIntoGeneSets(profiles, geneSetIdsToGeneIds);
            }

            if (!queryFactors.isEmpty()) {
                profiles = isSpecific ?
                        BaselineProfilesPipelineBuilder.filterByQueryFactorSpecificity(profiles, queryFactors, allQueryFactors) :
                        BaselineProfilesPipelineBuilder.filterByQueryFactors(profiles, queryFactors);
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

}
