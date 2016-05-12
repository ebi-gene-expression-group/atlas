package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;

@Named
@Scope("prototype")
public class BaselineExperimentProfilesViewModelBuilder {

    private final BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder;
    private final FilterFactorsConverter filterFactorsConverter;

    @Inject
    public BaselineExperimentProfilesViewModelBuilder(BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder) {
        this.baselineExpressionViewModelBuilder = baselineExpressionViewModelBuilder;
        this.filterFactorsConverter = new FilterFactorsConverter();
    }

    public BaselineProfilesViewModel build(BaselineExperimentProfilesList profiles, SortedSet<Factor> orderedFactors) {
        BaselineExperimentProfileRowViewModel[] genes = buildExperiments(profiles, orderedFactors, profiles.getMinExpressionLevel(), profiles.getMaxExpressionLevel());
        return new BaselineProfilesViewModel<>(profiles.getTotalResultCount(), genes);
    }

    public BaselineExperimentProfileRowViewModel[] buildExperiments(List<BaselineExperimentProfile> baselineProfiles, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        BaselineExperimentProfileRowViewModel[] viewModels = new BaselineExperimentProfileRowViewModel[baselineProfiles.size()];

        int i = 0;
        for (BaselineExperimentProfile baselineProfile : baselineProfiles) {
            BaselineExperimentProfileRowViewModel profileViewModel = buildExperiment(baselineProfile, orderedFactors, minExpressionLevel, maxExpressionLevel);
            viewModels[i++] = profileViewModel;
        }

        return viewModels;
    }

    public BaselineExperimentProfileRowViewModel buildExperiment(BaselineExperimentProfile profile, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        String geneId = profile.getId();
        String geneName = profile.getShortName();
        String experimentType = profile.getExperimentType();
        String serializedFilterFactors = filterFactorsConverter.serialize(profile.getFilterFactors());
        BaselineExpressionViewModel[] expressions = baselineExpressionViewModelBuilder.buildExpressions(profile, orderedFactors, minExpressionLevel, maxExpressionLevel);
        return new BaselineExperimentProfileRowViewModel(geneId, geneName, expressions, serializedFilterFactors, experimentType);
    }

}
