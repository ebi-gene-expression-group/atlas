package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;

@Named
@Scope("prototype")
public class BaselineGeneViewModelBuilder {

    private final ColourGradient colourGradient;

    @Inject
    public BaselineGeneViewModelBuilder(ColourGradient colourGradient) {
        this.colourGradient = colourGradient;
    }

    public BaselineGeneViewModel[] build(List<BaselineProfile> baselineProfiles, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        BaselineGeneViewModel[] viewModels = new BaselineGeneViewModel[baselineProfiles.size()];

        int i = 0;
        for (BaselineProfile baselineProfile : baselineProfiles) {
            BaselineGeneViewModel profileViewModel = build(baselineProfile, orderedFactors, minExpressionLevel, maxExpressionLevel);
            viewModels[i++] = profileViewModel;
        }

        return viewModels;
    }

    public BaselineGeneViewModel build(Profile<Factor, BaselineExpression> profile, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        String geneId = profile.getId();
        String geneName = profile.getName();
        BaselineExpressionViewModel[] expressions = buildExpressions(profile, orderedFactors, minExpressionLevel, maxExpressionLevel);
        return new BaselineGeneViewModel(geneId, geneName, expressions);
    }

    private BaselineExpressionViewModel[] buildExpressions(Profile<Factor, BaselineExpression> profile, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        BaselineExpressionViewModel[] expressionViewModels = new BaselineExpressionViewModel[orderedFactors.size()];

        int i = 0;
        for (Factor factor : orderedFactors) {
            String factorName = factor.getValue();
            BaselineExpression expression = profile.getExpression(factor);

            String value = (expression == null) ? "" : (expression.isKnown() ? Double.toString(expression.getLevel()) : "UNKNOWN");
            String color = (expression == null) ? "" : (expression.isKnown() ? colourGradient.getGradientColour(expression.getLevel(), minExpressionLevel, maxExpressionLevel) : "UNKNOWN");

            String svgPathId = factor.getValueOntologyTerm();

            BaselineExpressionViewModel expressionViewModel = new BaselineExpressionViewModel(factorName, color, value, svgPathId);
            expressionViewModels[i++] = expressionViewModel;
        }

        return expressionViewModels;
    }

}
