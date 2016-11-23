
package uk.ac.ebi.atlas.model.experiment.baseline;


import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;

public class GenericBaselineProfilesList<T extends BaselineProfile> extends GeneProfilesList<T> {

    public GenericBaselineProfilesList() {
    }

    public GenericBaselineProfilesList(Collection<T> collection) {
        super(collection);
    }

    public double getMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (BaselineProfile geneProfile : this) {
            if (maxExpressionLevel < geneProfile.getMaxExpressionLevel()) {
                maxExpressionLevel = geneProfile.getMaxExpressionLevel();
            }
        }
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (BaselineProfile geneProfile : this) {
            if (geneProfile.getMinExpressionLevel() < minExpressionLevel) {
                minExpressionLevel = geneProfile.getMinExpressionLevel();
            }
        }
        return minExpressionLevel;
    }

}
