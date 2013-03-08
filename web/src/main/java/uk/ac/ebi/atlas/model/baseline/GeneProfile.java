/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.model.AbstractGeneProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile extends AbstractGeneProfile<BaselineExpression> {

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private Map<Factor, BaselineExpression> expressions = new HashMap<>();


    public GeneProfile(String geneId) {
        super(geneId);
    }

    protected GeneProfile add(BaselineExpression expression, String queryFactorType) {

        updateProfileExpression(expression.getLevel());

        expressions.put(expression.getFactor(queryFactorType), expression);
        return this;
    }

    public int getSpecificity() {
        return expressions.values().size();
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    private void updateProfileExpression(double level) {
        if (maxExpressionLevel < level) {
            maxExpressionLevel = level;
        }
        if (level < minExpressionLevel) {
            minExpressionLevel = level;
        }
    }

    public boolean isExpressedOnAnyOf(Set<Factor> factors) {
        checkArgument(CollectionUtils.isNotEmpty(factors));
        return Sets.intersection(this.expressions.keySet(), factors).size() > 0;
    }

    public double getAverageExpressionLevelOn(Set<Factor> factors) {
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(factors)) {
            return expressionLevel;
        }

        for (Factor factor : factors) {
            expressionLevel += getExpressionLevel(factor);
        }
        return expressionLevel / factors.size();
    }

    public double getExpressionLevel(Factor factor) {
        BaselineExpression expression = expressions.get(factor);
        return expression == null ? 0 : expression.getLevel();
    }

    @Override
    protected Collection<BaselineExpression> getExpressions() {
        return expressions.values();
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneProfile geneProfile;

        private GeneExpressionPrecondition geneExpressionPrecondition;

        private GeneProfilePrecondition geneProfilePrecondition;

        private RequestContext requestContext;

        @Inject
        protected Builder(RequestContext requestContext, GeneExpressionPrecondition geneExpressionPrecondition,
                          GeneProfilePrecondition geneProfilePrecondition) {
            this.requestContext = requestContext;
            this.geneExpressionPrecondition = geneExpressionPrecondition;
            this.geneProfilePrecondition = geneProfilePrecondition;
        }

        //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
        // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
        void initPreconditions() {
            geneExpressionPrecondition.setCutoff(requestContext.getCutoff())
                    .setFilterFactors(requestContext.getSelectedFilterFactors());
            geneProfilePrecondition.setAllQueryFactors(requestContext.getAllQueryFactors())
                    .setSelectedQueryFactors(requestContext.getSelectedQueryFactors())
                    .setSpecific(requestContext.isSpecific());
        }

        public Builder forGeneId(String geneId) {
            this.geneProfile = new GeneProfile(geneId);
            initPreconditions();

            return this;
        }

        public Builder addExpression(BaselineExpression expression) {
            checkState(geneProfile != null, "Please invoke forGeneID before create");
            if (geneExpressionPrecondition.apply(expression)) {
                geneProfile.add(expression, requestContext.getQueryFactorType());
            }
            return this;
        }

        public GeneProfile create() {
            checkState(geneProfile != null, "Please invoke forGeneID before create");

            return geneProfilePrecondition.apply(geneProfile) ? geneProfile : null;
        }


    }
}
