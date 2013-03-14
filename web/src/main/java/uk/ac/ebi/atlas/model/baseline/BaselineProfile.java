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

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

public class BaselineProfile extends GeneProfile<Factor, BaselineExpression> {

    public BaselineProfile(String geneId) {
        super(geneId);
    }

    protected BaselineProfile add(BaselineExpression expression, String queryFactorType) {

        this.addExpression(expression.getFactor(queryFactorType), expression);
        return this;
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private BaselineProfile baselineProfile;

        private BaselineExpressionPrecondition baselineExpressionPrecondition;

        private BaselineProfilePrecondition baselineProfilePrecondition;

        private RequestContext requestContext;

        @Inject
        protected Builder(RequestContext requestContext, BaselineExpressionPrecondition baselineExpressionPrecondition,
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

        public Builder forGeneId(String geneId) {
            this.baselineProfile = new BaselineProfile(geneId);
            initPreconditions();

            return this;
        }

        public Builder addExpression(BaselineExpression expression) {
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
