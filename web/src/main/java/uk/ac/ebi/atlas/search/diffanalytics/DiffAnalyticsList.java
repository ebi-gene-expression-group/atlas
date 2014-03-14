/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialExpressionLimits;
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


