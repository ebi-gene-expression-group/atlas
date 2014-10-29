package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedSet;

@Named
@Scope("singleton")
public class BaselineExpressionViewModelBuilder {

    private final ColourGradient colourGradient;
    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder;

    @Inject
    public BaselineExpressionViewModelBuilder(ColourGradient colourGradient, BaselineExpressionLevelRounder baselineExpressionLevelRounder) {
        this.colourGradient = colourGradient;
        this.baselineExpressionLevelRounder = baselineExpressionLevelRounder;
    }

    public BaselineExpressionViewModel[] buildExpressions(Profile<Factor, BaselineExpression> profile, SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        BaselineExpressionViewModel[] expressionViewModels = new BaselineExpressionViewModel[orderedFactors.size()];

        int i = 0;
        for (Factor factor : orderedFactors) {
            BaselineExpressionViewModel expressionViewModel = createBaselineExpressionViewModel(profile, factor, minExpressionLevel, maxExpressionLevel);
            expressionViewModels[i++] = expressionViewModel;
        }

        return expressionViewModels;
    }

    private BaselineExpressionViewModel createBaselineExpressionViewModel(Profile<Factor, BaselineExpression> profile, Factor factor, double minExpressionLevel, double maxExpressionLevel) {
        String factorName = factor.getValue();
        BaselineExpression expression = profile.getExpression(factor);

        String value = (expression == null) ? "" : (!expression.isKnown() ? "UNKNOWN" :  (expression.getLevelAsString().equals("NT")) ? "NT" : baselineExpressionLevelRounder.format(expression.getLevel()));
        String color = (expression == null) ? "" : (expression.isKnown() && !expression.getLevelAsString().equals("NT") ?
                colourGradient.getGradientColour(expression.getLevel(), minExpressionLevel, maxExpressionLevel) : (expression.getLevelAsString().equals("NT")  ? "" : "UNKNOWN"));

        String svgPathId = factor.getValueOntologyTermId();

        return new BaselineExpressionViewModel(factorName, color, value, svgPathId);
    }

}
