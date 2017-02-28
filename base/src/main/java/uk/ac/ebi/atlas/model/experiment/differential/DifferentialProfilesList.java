
package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfilesList<T extends DifferentialProfile> extends GeneProfilesList<T> implements DifferentialExpressionLimits {

    public DifferentialProfilesList() {
        // default, with no starting collection
    }

    public DifferentialProfilesList(Collection<T> collection) {
        super(collection);
    }

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

    @Override
    public Map<String, String> properties() {
        return ImmutableMap.<String,String>builder()
                .putAll(super.properties())
                .put("maxDownLevel", Double.toString(getMaxDownRegulatedExpressionLevel()))
                .put("maxUpLevel", Double.toString(getMaxUpRegulatedExpressionLevel()))
                .put("minDownLevel", Double.toString(getMinDownRegulatedExpressionLevel()))
                .put("minUpLevel", Double.toString(getMinUpRegulatedExpressionLevel()))
                .build();
    }
}
