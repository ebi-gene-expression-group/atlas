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


import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.GeneProfile;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

public class DifferentialProfile extends GeneProfile<Contrast, DifferentialExpression> {


    public DifferentialProfile(String geneId) {
        super(geneId);
    }

    @Override
    public DifferentialExpression getExpression(Contrast contrast){
        return super.getExpression(contrast);
    }

    DifferentialProfile add(DifferentialExpression expression){
        this.addExpression(expression.getContrast(), expression);
        return this;
    }

    @Named
    @Scope("prototype")
    public static class DifferentialProfileBuilder {

        private DifferentialProfile differentialProfile;

        private DifferentialExpressionPrecondition differentialExpressionPrecondition;

        private DifferentialRequestContext requestContext;

        @Inject
        protected DifferentialProfileBuilder(DifferentialRequestContext requestContext
                                        , DifferentialExpressionPrecondition differentialExpressionPrecondition) {
            this.requestContext = requestContext;
            this.differentialExpressionPrecondition = differentialExpressionPrecondition;
        }

        //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
        // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
        void initPreconditions() {
            differentialExpressionPrecondition.setCutoff(requestContext.getCutoff()).setRegulation(requestContext.getRegulation());
        }

        public DifferentialProfileBuilder forGeneId(String geneId) {
            this.differentialProfile = new DifferentialProfile(geneId);
            initPreconditions();

            return this;
        }

        public DifferentialProfileBuilder addExpression(DifferentialExpression expression) {
            checkState(differentialProfile != null, "Please invoke forGeneID before create");
            if (differentialExpressionPrecondition.apply(expression)){
                differentialProfile.add(expression);
            }
            return this;
        }

        public DifferentialProfile create() {
            checkState(differentialProfile != null, "Please invoke forGeneID before create");

            if (differentialProfile.isEmpty()){
                return null;
            }

            return differentialProfile;
        }


    }


}
