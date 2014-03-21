package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfileStreamPipelineBuilder<P extends DifferentialProfile> implements ProfileStreamPipelineBuilder<P, DifferentialProfileStreamOptions> {

    @Override
    public Iterable<P> build(Iterable<P> profiles, DifferentialProfileStreamOptions options) {
        boolean isSpecific = options.isSpecific();
        Set<Contrast> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = options.getSelectedGeneIDs();
        Set<Contrast> allQueryFactors = options.getAllQueryFactors();

        Iterable<P> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = ProfileStreamFilters.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = isSpecific ?
                    filterByQueryFactorSpecificity(profilesPipeline, queryFactors, allQueryFactors) :
                    ProfileStreamFilters.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    public static <P extends DifferentialProfile> Iterable<P> filterByQueryFactorSpecificity(Iterable<P> profiles, Set<Contrast> queryFactors, Set<Contrast> allQueryFactors) {
        IsDifferentialProfileSpecific isDifferentialProfileSpecific = new IsDifferentialProfileSpecific(queryFactors, allQueryFactors);
        return Iterables.filter(profiles, isDifferentialProfileSpecific);
    }

}
