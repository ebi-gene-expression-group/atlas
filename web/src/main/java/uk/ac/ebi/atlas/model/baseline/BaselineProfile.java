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

package uk.ac.ebi.atlas.model.baseline;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class BaselineProfile extends GeneProfile<Factor, BaselineExpression> {
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    public BaselineProfile(String geneId) {
        super(geneId);
    }

    BaselineProfile add(BaselineExpression expression, String queryFactorType) {

        this.addExpression(expression.getFactor(queryFactorType), expression);
        return this;
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public double getAverageExpressionLevelOn(Set<Factor> factors) {
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(factors)) {
            return expressionLevel;
        }

        for (Factor condition : factors) {
            expressionLevel += getExpressionLevel(condition);
        }
        return expressionLevel / factors.size();
    }

    @Override
    protected void updateProfileExpression(BaselineExpression geneExpression) {
        maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
        minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
    }


    @Named
    @Scope("prototype")
    public static class BaselineProfileBuilder {

        private BaselineProfile baselineProfile;

        private BaselineExpressionPrecondition baselineExpressionPrecondition;

        private BaselineProfilePrecondition baselineProfilePrecondition;

        private BaselineRequestContext requestContext;

        @Inject
        protected BaselineProfileBuilder(BaselineRequestContext requestContext, BaselineExpressionPrecondition baselineExpressionPrecondition,
                                         BaselineProfilePrecondition baselineProfilePrecondition) {
            this.requestContext = requestContext;
            this.baselineExpressionPrecondition = baselineExpressionPrecondition;
            this.baselineProfilePrecondition = baselineProfilePrecondition;
        }

        //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
        // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
        void initPreconditions() {
            baselineExpressionPrecondition.setCutoff(requestContext.getCutoff())
                    .setFilterFactors(requestContext.getSelectedFilterFactors());
            baselineProfilePrecondition.setAllQueryFactors(requestContext.getAllQueryFactors())
                    .setSelectedQueryFactors(requestContext.getSelectedQueryFactors())
                    .setSpecific(requestContext.isSpecific());
        }

        public BaselineProfileBuilder forGeneId(String geneId) {
            this.baselineProfile = new BaselineProfile(geneId);
            initPreconditions();

            return this;
        }

        public BaselineProfileBuilder addExpression(BaselineExpression expression) {
            checkState(baselineProfile != null, "Please invoke forGeneID before create");
            if (baselineExpressionPrecondition.apply(expression)) {
                baselineProfile.add(expression, requestContext.getQueryFactorType());
            }
            return this;
        }

        public BaselineProfile create() {
            checkState(baselineProfile != null, "Please invoke forGeneID before create");

            return baselineProfilePrecondition.apply(baselineProfile) ? baselineProfile : null;
        }


    }
}
