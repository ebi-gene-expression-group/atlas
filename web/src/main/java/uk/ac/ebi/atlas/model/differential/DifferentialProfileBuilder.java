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

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public abstract class DifferentialProfileBuilder<T extends DifferentialProfile, K extends DifferentialRequestContext> {

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
        for (DifferentialExpression expression : expressions) {
            differentialProfile.add(expression);
        }
        resetBuilder();
        return differentialProfilePrecondition.apply(differentialProfile) ? differentialProfile : null;
    }

    //this is only a temporary ugly workaround to patch previous even more buggish implementation...
    //Builder should not support multiple creations...
    //new instance of builder should be used everytime an object has to be built.
    private void resetBuilder() {
        this.expressions = Lists.newArrayList();
        this.geneId = null;
    }

}
