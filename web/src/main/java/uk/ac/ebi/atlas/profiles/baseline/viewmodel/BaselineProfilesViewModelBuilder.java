package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfilesViewModelBuilder {

    private final BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder;
    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder;

    @Inject
    public BaselineProfilesViewModelBuilder(BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder, BaselineExpressionLevelRounder baselineExpressionLevelRounder) {
        this.baselineExpressionViewModelBuilder = baselineExpressionViewModelBuilder;
        this.baselineExpressionLevelRounder = baselineExpressionLevelRounder;
    }

    public BaselineProfilesViewModel build(BaselineProfilesList profiles, Set<Factor> orderedFactors) {
        BaselineProfileRowViewModel[] genes = buildGenes(profiles, orderedFactors, profiles.getMinExpressionLevel(), profiles.getMaxExpressionLevel());
        return new BaselineProfilesViewModel<>(baselineExpressionLevelRounder, profiles.getMinExpressionLevel(), profiles.getMaxExpressionLevel(), profiles.getTotalResultCount(), genes);
    }

    public BaselineProfileRowViewModel[] buildGenes(List<BaselineProfile> baselineProfiles, Set<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        BaselineProfileRowViewModel[] viewModels = new BaselineProfileRowViewModel[baselineProfiles.size()];

        int i = 0;
        for (BaselineProfile baselineProfile : baselineProfiles) {
            BaselineProfileRowViewModel profileViewModel = build(baselineProfile, orderedFactors, minExpressionLevel, maxExpressionLevel);
            viewModels[i++] = profileViewModel;
        }

        return viewModels;
    }

    public BaselineProfileRowViewModel build(Profile<Factor, BaselineExpression> profile, Set<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        String geneId = profile.getId();
        String geneName = profile.getName();
        BaselineExpressionViewModel[] expressions = baselineExpressionViewModelBuilder.buildExpressions(profile, orderedFactors, minExpressionLevel, maxExpressionLevel);
        return new BaselineProfileRowViewModel(geneId, geneName, expressions);
    }

}
