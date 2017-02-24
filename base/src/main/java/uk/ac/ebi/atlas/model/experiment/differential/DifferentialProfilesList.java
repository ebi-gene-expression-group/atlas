
package uk.ac.ebi.atlas.model.experiment.differential;

import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;

import java.util.Collection;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfilesList<T extends DifferentialProfile> extends GeneProfilesList<T> implements DifferentialExpressionLimits {

    public DifferentialProfilesList() {
        // default, with no starting collection
    }

    public DifferentialProfilesList(Collection<T> collection) {
        super(collection);
    }

    //TODO: memoize min and max levels, because they are called multiple times from the JSP and recalculated each time
    public double getMaxUpRegulatedExpressionLevel(){
        double maxUpRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMaxUpRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, expressionLevel);
            }
        }
        return maxUpRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel(){
        Double minUpRegulatedExpressionLevel = null;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMinUpRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                minUpRegulatedExpressionLevel = (minUpRegulatedExpressionLevel == null) ? expressionLevel : min(minUpRegulatedExpressionLevel, expressionLevel);
            }
        }
        return minUpRegulatedExpressionLevel == null ? Double.NaN : minUpRegulatedExpressionLevel;
    }


    public double getMaxDownRegulatedExpressionLevel(){
        double maxDownRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMaxDownRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, Math.abs(expressionLevel));
            }
        }
        return maxDownRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : negate(maxDownRegulatedExpressionLevel);
    }

    public double getMinDownRegulatedExpressionLevel(){
        Double minDownRegulatedExpressionLevel = null;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMinDownRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                minDownRegulatedExpressionLevel = (minDownRegulatedExpressionLevel == null) ? Math.abs(expressionLevel) : min(minDownRegulatedExpressionLevel, Math.abs(expressionLevel));
            }
        }
        return minDownRegulatedExpressionLevel == null ? Double.NaN : negate(minDownRegulatedExpressionLevel);
    }

    public static double negate(double value) {
        return value == 0 ? 0 : -value;
    }
}
