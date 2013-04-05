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

package uk.ac.ebi.atlas.model.differential;


import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfile<T extends DifferentialExpression> extends GeneProfile<Contrast, T> {


    private double maxUpRegulatedExpressionLevel = 0D;
    private double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
    private double maxDownRegulatedExpressionLevel = 0D;
    private double minDownRegulatedExpressionLevel = Double.MAX_VALUE;

    private EnumMap<Regulation, Integer> specificityForRegulation = new EnumMap(Regulation.class);

    public DifferentialProfile(String geneId) {
        super(geneId);
    }

    DifferentialProfile add(T expression) {
        this.addExpression(expression.getContrast(), expression);
        return this;
    }

    public double getMaxUpRegulatedExpressionLevel() {
        return maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel() {
        return minUpRegulatedExpressionLevel;
    }

    public double getMaxDownRegulatedExpressionLevel() {
        return maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel() {
        return minDownRegulatedExpressionLevel;
    }

    public int getSpecificity(Regulation regulation) {
        Integer specificity = specificityForRegulation.get(regulation);
        return specificity == null ? 0 : specificity;
    }

    public double getAverageExpressionLevelOn(Set<Contrast> conditions, Regulation regulation) {
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(conditions)) {
            return expressionLevel;
        }

        for (Contrast condition : conditions) {
            DifferentialExpression expression = getExpression(condition);
            if (expression != null && expression.isRegulatedLike(regulation)) {
                expressionLevel += expression.getLevel();
            }
        }
        return expressionLevel / conditions.size();
    }


    @Override
    protected void updateProfileExpression(DifferentialExpression differentialExpression) {
        if (differentialExpression.isOverExpressed()) {
            updateUpRegulatedProfileExpression(differentialExpression.getLevel());
            updateSpecificityForRegulation(Regulation.UP);
        }

        if (differentialExpression.isUnderExpressed()) {
            updateDownRegulatedProfileExpression(differentialExpression.getLevel());
            updateSpecificityForRegulation(Regulation.DOWN);
        }
        updateSpecificityForRegulation(Regulation.UP_DOWN);
    }

    private void updateSpecificityForRegulation(Regulation regulation) {
        Integer specificity = specificityForRegulation.get(regulation);
        if (specificity == null) {
            specificity = 0;
        }
        specificityForRegulation.put(regulation, specificity + 1);
    }

    void updateUpRegulatedProfileExpression(double expressionLevel) {
        maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, expressionLevel);
        minUpRegulatedExpressionLevel = min(minUpRegulatedExpressionLevel, expressionLevel);
    }

    void updateDownRegulatedProfileExpression(double expressionLevel) {
        maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, expressionLevel);
        minDownRegulatedExpressionLevel = min(minDownRegulatedExpressionLevel, expressionLevel);
    }

    public double getMinExpressionLevel() {
        return min(minUpRegulatedExpressionLevel, minDownRegulatedExpressionLevel);
    }


    public abstract static class DifferentialProfileBuilder<T extends DifferentialProfile, K extends DifferentialRequestContext> {

        private K requestContext;

        private String geneId;

        private List<DifferentialExpression> expressions = Lists.newArrayList();

        private DifferentialExpressionPrecondition differentialExpressionPrecondition;
        private DifferentialProfilePrecondition differentialProfilePrecondition;

        @Inject
        private void setDifferentialExpressionPrecondition(DifferentialExpressionPrecondition differentialExpressionPrecondition) {
            this.differentialExpressionPrecondition = differentialExpressionPrecondition;
        }

        @Inject
        private void setDifferentialProfilePrecondition(DifferentialProfilePrecondition differentialProfilePrecondition) {
            this.differentialProfilePrecondition = differentialProfilePrecondition;
        }

        protected DifferentialProfileBuilder(K requestContext) {
            this.requestContext = requestContext;
        }

        //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
        // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
        void initPreconditions() {
            differentialExpressionPrecondition.setCutoff(requestContext.getCutoff()).setRegulation(requestContext.getRegulation());

            differentialProfilePrecondition.setAllQueryFactors(requestContext.getAllQueryFactors())
                    .setSelectedQueryFactors(requestContext.getSelectedQueryFactors())
                    .setRegulation(requestContext.getRegulation());

    }


        public DifferentialProfileBuilder forGeneId(String geneId) {
            this.geneId = geneId;
            initPreconditions();
            return this;
        }

        protected abstract T createProfile(String geneId);

        public DifferentialProfileBuilder addExpression(DifferentialExpression expression) {
            checkState(geneId != null, "Please invoke forGeneID before create");
            if (differentialExpressionPrecondition.apply(expression)) {
                expressions.add(expression);
            }
            return this;
        }

        public T create() {
            checkState(geneId != null, "Please invoke forGeneId before create");
            T differentialProfile = createProfile(geneId);
            for(DifferentialExpression expression:expressions){
                differentialProfile.add(expression);
            }
            resetBuilder();
            return differentialProfilePrecondition.apply(differentialProfile)? differentialProfile : null;
        }

        //this is only a temporary ugly workaround to patch previous even more buggish implementation...
        //Builder should not support multiple creations...
        //new instance of builder should be used everytime an object has to be built.
        private void resetBuilder(){
           this.expressions = Lists.newArrayList();
           this.geneId = null;
        }

    }


}
