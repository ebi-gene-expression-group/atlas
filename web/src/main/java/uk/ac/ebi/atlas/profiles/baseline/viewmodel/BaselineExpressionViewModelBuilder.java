package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.base.Optional;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.Quartiles;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
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

    public BaselineExpressionViewModel[] buildExpressions(Profile<Factor, BaselineExpression> profile,
                                                          SortedSet<Factor> orderedFactors, double
                                                                  minExpressionLevel, double maxExpressionLevel) {
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
        Optional<Quartiles> quartiles = (expression == null || expression.getQuartiles().length == 0) ? Optional.<Quartiles>absent() : Optional.of(Quartiles.create(expression.getQuartiles()));

        String value = (expression == null) ? "" : (expression.getLevelAsString().equals("NT")) ? "NT" : (!expression.isKnown() ? "UNKNOWN" : baselineExpressionLevelRounder.format(expression.getLevel()));
        String color = (expression == null) ? "" : (expression.isKnown() && !expression.getLevelAsString().equals("NT") ?
                colourGradient.getGradientColour(expression.getLevel(), minExpressionLevel, maxExpressionLevel) : (expression.getLevelAsString().equals("NT") ? "" : "UNKNOWN"));

        // We are assuming that the only relevant ontology term for tissues is the first one
        String svgPathId = factor.getValueOntologyTerms().isEmpty() ? null : factor.getValueOntologyTerms().iterator().next().accession();

        return new BaselineExpressionViewModel(factorName, color, value, svgPathId, quartiles);
    }

}
