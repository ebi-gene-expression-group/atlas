
package uk.ac.ebi.atlas.search.diffanalytics;

import uk.ac.ebi.atlas.model.differential.DifferentialExpressionLimits;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.ArrayList;
import java.util.List;

public class DiffAnalyticsList extends ArrayList<DiffAnalytics> implements DifferentialExpressionLimits {

    private int totalNumberOfResults;

    private Double minUpRegulated;
    private Double maxUpRegulated;
    private Double minDownRegulated;
    private Double maxDownRegulated;

    public DiffAnalyticsList(){
    }

    public DiffAnalyticsList(List<DiffAnalytics> diffAnalyticses, int totalNumberOfResults) {
        super(diffAnalyticses);
        this.totalNumberOfResults = totalNumberOfResults;
    }

    public double getMaxUpRegulatedExpressionLevel() {
        if (maxUpRegulated == null) {
            maxUpRegulated = new FindTopLevelByRegulation(Regulation.UP).apply(this);
        }
        return maxUpRegulated;
    }

    public double getMinUpRegulatedExpressionLevel() {
        if(minUpRegulated == null) {
            minUpRegulated = new FindTopLevelByRegulation(Regulation.UP).apply(Lists.reverse(this));
        }
        return minUpRegulated;
    }

    public double getMaxDownRegulatedExpressionLevel() {
        if(maxDownRegulated ==null) {
            maxDownRegulated = new FindTopLevelByRegulation(Regulation.DOWN).apply(this);
        }
        return maxDownRegulated;
    }

    public double getMinDownRegulatedExpressionLevel() {
        if (minDownRegulated == null) {
            minDownRegulated = new FindTopLevelByRegulation(Regulation.DOWN).apply(Lists.reverse(this));
        }
        return minDownRegulated;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    static class FindTopLevelByRegulation implements Function<List<DiffAnalytics>, Double>{

        private Regulation regulation;

        public FindTopLevelByRegulation(Regulation regulation){

            this.regulation = regulation;
        }

        @Override
        public Double apply(List<DiffAnalytics> sortedExpressions) {
            for(DiffAnalytics diffAnalytics :sortedExpressions){
                DifferentialExpression differentialExpression = diffAnalytics.getExpression();
                if (differentialExpression.isRegulatedLike(regulation)){
                    return differentialExpression.getLevel();
                }
            }
            return 0.0;
        }
    }

}


