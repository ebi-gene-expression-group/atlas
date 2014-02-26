package uk.ac.ebi.atlas.streams.differential;

import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.streams.IsExpressedForQueryCondition;
import uk.ac.ebi.atlas.streams.IsGeneIdMatch;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfileStreamPipelineBuilder<T extends DifferentialProfile> {

    public Iterable<T> build(Iterable<T> profiles, DifferentialProfileStreamOptions options) {
        boolean isSpecific = options.isSpecific();
        Set<Contrast> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = options.getSelectedGeneIDs();
        Set<Contrast> allQueryFactors = options.getAllQueryFactors();

        Iterable<T> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = DifferentialProfileStreamPipelineBuilder.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = isSpecific ?
                    DifferentialProfileStreamPipelineBuilder.filterByQueryFactorSpecificity(profilesPipeline, queryFactors, allQueryFactors) :
                    DifferentialProfileStreamPipelineBuilder.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    public static <T extends DifferentialProfile> Iterable<T> filterByGeneIds(Iterable<T> profiles, Set<String> uppercaseGeneIDs) {
        return Iterables.filter(profiles, new IsGeneIdMatch(uppercaseGeneIDs));
    }

    public static <K, T extends Profile<K, ?>>Iterable<T> filterByQueryFactors(Iterable<T> profiles, Set<K> queryFactors) {
        return Iterables.filter(profiles, new IsExpressedForQueryCondition<K, T>(queryFactors));
    }

    public static <T extends DifferentialProfile> Iterable<T> filterByQueryFactorSpecificity(Iterable<T> profiles, Set<Contrast> queryFactors, Set<Contrast> allQueryFactors) {
        IsDifferentialProfileSpecific isDifferentialProfileSpecific = new IsDifferentialProfileSpecific(queryFactors, allQueryFactors);
        return Iterables.filter(profiles, isDifferentialProfileSpecific);
    }

}
