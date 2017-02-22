package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.Quartiles;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
/*
I exist because the old heatmap wants colors from the server side.
The widget code no longer uses this.
 */
@Deprecated
@Named
public class BaselineExpressionViewModelBuilder {

    private final ColourGradient colourGradient;

    @Inject
    public BaselineExpressionViewModelBuilder(ColourGradient colourGradient) {
        this.colourGradient = colourGradient;
    }

    public JsonArray buildExpressions(Profile<Factor, BaselineExpression> profile,
                                      List<Factor> orderedFactors, double
                                                                  minExpressionLevel, double maxExpressionLevel) {
        JsonArray result = new JsonArray();

        for (Factor factor : orderedFactors) {
            result.add(createBaselineExpressionViewModel(profile, factor, minExpressionLevel, maxExpressionLevel).toJson());
        }

        return result;
    }

    private BaselineExpressionViewModel createBaselineExpressionViewModel(Profile<Factor, BaselineExpression> profile, Factor factor, double minExpressionLevel, double maxExpressionLevel) {
        String factorName = factor.getValue();
        BaselineExpression expression = profile.getExpression(factor);
        Optional<Quartiles> quartiles = (expression == null || expression.getQuartiles().length == 0) ? Optional.<Quartiles>absent() : Optional.of(Quartiles.create(expression.getQuartiles()));

        Double value = expression == null
                ? Double.NaN
                : expression.getLevel();
        String color = (expression == null)
                ? ""
                :colourGradient.getGradientColour(expression.getLevel(), minExpressionLevel, maxExpressionLevel);
        // We are assuming that the only relevant ontology term for tissues is the first one
        String svgPathId = factor.getValueOntologyTerms().isEmpty() ? null : factor.getValueOntologyTerms().iterator().next().accession();

        return new BaselineExpressionViewModel(factorName, color, value, svgPathId, quartiles);
    }

}
