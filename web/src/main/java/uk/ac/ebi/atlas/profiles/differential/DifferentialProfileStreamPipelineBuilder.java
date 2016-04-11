package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamPipelineBuilder;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfileStreamPipelineBuilder<P extends DifferentialProfile> extends
        ProfileStreamPipelineBuilder<P, DifferentialProfileStreamOptions, Contrast> {

    @Override
    protected Predicate<P> queryFactorSpecificityPredicate(Set<Contrast> queryFactors, Set<Contrast> allQueryFactors) {
        return new IsDifferentialProfileSpecific(queryFactors, allQueryFactors);
    }

    @Override
    protected Iterable<P> averageIntoGeneSets(Iterable<P> profiles, ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        //We do not average differential profiles
        return profiles;
    }
}
