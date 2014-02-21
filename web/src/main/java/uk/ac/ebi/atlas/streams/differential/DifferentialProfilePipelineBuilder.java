package uk.ac.ebi.atlas.streams.differential;

import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfilePipelineBuilder<T extends DifferentialProfile> {

    public Iterable<T> build(Iterable<T> profiles, DifferentialProfilesCommandOptions options) {
        boolean isSpecific = options.isSpecific();
        Set<Contrast> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = options.getSelectedGeneIDs();
        Set<Contrast> allQueryFactors = options.getAllQueryFactors();
        Regulation regulation = options.getRegulation();

        Iterable<T> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = DifferentialProfilePipelineBuilder.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = isSpecific ?
                    DifferentialProfilePipelineBuilder.filterByQueryFactorSpecificity(profilesPipeline, queryFactors, allQueryFactors, regulation) :
                    DifferentialProfilePipelineBuilder.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    public static <T extends DifferentialProfile> Iterable<T> filterByGeneIds(Iterable<T> profiles, Set<String> uppercaseGeneIDs) {
        return Iterables.filter(profiles, new GeneProfileInputStreamFilter.GeneIdMatchesPredicate(uppercaseGeneIDs));
    }

    public static <T extends DifferentialProfile>Iterable<T> filterByQueryFactors(Iterable<T> profiles, Set<Contrast> queryFactors) {
        return Iterables.filter(profiles, new GeneProfileInputStreamFilter.ExpressedForQueryConditionPredicate<>(queryFactors));
    }

    public static <T extends DifferentialProfile> Iterable<T> filterByQueryFactorSpecificity(Iterable<T> profiles, Set<Contrast> queryFactors, Set<Contrast> allQueryFactors, Regulation regulation) {
        IsDifferentialProfileSpecific isDifferentialProfileSpecific = new IsDifferentialProfileSpecific(queryFactors, allQueryFactors, regulation);
        return Iterables.filter(profiles, isDifferentialProfileSpecific);
    }

}
