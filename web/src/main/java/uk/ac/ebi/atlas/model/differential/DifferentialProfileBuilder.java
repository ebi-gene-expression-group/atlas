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
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;

import java.util.List;

public abstract class DifferentialProfileBuilder<T extends DifferentialProfile<E>, K extends DifferentialRequestContext, E extends DifferentialExpression> {

    private K requestContext;

    private List<E> expressions = Lists.newArrayList();

    private DifferentialExpressionPrecondition differentialExpressionPrecondition;
    private DifferentialProfilePrecondition differentialProfilePrecondition;

    protected DifferentialProfileBuilder(K requestContext,
                                         DifferentialExpressionPrecondition differentialExpressionPrecondition,
                                         DifferentialProfilePrecondition differentialProfilePrecondition) {
        this.requestContext = requestContext;
        this.differentialExpressionPrecondition = differentialExpressionPrecondition;
        this.differentialProfilePrecondition = differentialProfilePrecondition;
    }

    //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
    // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
    void initPreconditions() {
        differentialExpressionPrecondition.setCutoff(requestContext.getCutoff()).setRegulation(requestContext.getRegulation());

        differentialProfilePrecondition.setAllQueryFactors(requestContext.getAllQueryFactors())
                .setSelectedQueryContrasts(requestContext.getSelectedQueryFactors())
                .setRegulation(requestContext.getRegulation());

    }

    public DifferentialProfileBuilder addExpression(E expression) {

        expressions.add(expression);
        return this;
    }

    public T create() {
        initPreconditions();

        T differentialProfile = createAndValidateProfile();

        resetBuilder();

        return differentialProfile;
    }

    T createAndValidateProfile() {
        T differentialProfile = createProfile();

        if (differentialProfile != null) {
            for (E expression : expressions) {
                if (differentialExpressionPrecondition.apply(expression)) {
                    differentialProfile.add(expression);
                }
            }

            if (differentialProfilePrecondition.apply(differentialProfile)) {
                return differentialProfile;
            }
        }
        return null;
    }

    protected abstract T createProfile();

    //this is only a temporary ugly workaround to patch previous even more buggish implementation...
    //Builder should not support multiple creations...
    //new instance of builder should be used everytime an object has to be built.
    private void resetBuilder() {
        expressions = Lists.newArrayList();
    }

}
