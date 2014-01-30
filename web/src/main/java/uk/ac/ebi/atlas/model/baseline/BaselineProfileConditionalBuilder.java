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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineProfileConditionalBuilder {

    private BaselineProfile baselineProfile;

    private BaselineExpressionIsAboveCutoffAndForFilterFactors baselineExpressionIsAboveCutoffAndForFilterFactors;

    private BaselineProfileIsSpecific baselineProfileIsSpecific;

    private BaselineRequestContext requestContext;

    @Inject
    public BaselineProfileConditionalBuilder(BaselineRequestContext requestContext, BaselineExpressionIsAboveCutoffAndForFilterFactors baselineExpressionIsAboveCutoffAndForFilterFactors,
                                             BaselineProfileIsSpecific baselineProfileIsSpecific) {
        this.requestContext = requestContext;
        this.baselineExpressionIsAboveCutoffAndForFilterFactors = baselineExpressionIsAboveCutoffAndForFilterFactors;
        this.baselineProfileIsSpecific = baselineProfileIsSpecific;
    }

    //We can't do this @PostConstruct because RequestContext bean gets instantiated in the construction phase of the Controller
    // , that is before the Controller actually executes the request, before the Controller initialize RequestContext
    //ToDo - circular package dependency:
    //ToDo: builder should not be responsible of checking that added expressions satisfy request preferences...
    //ToDo: It introduces circular dependency between model and request preferences.
    //ToDo: We should move preconditions in the nearest point where the condition to be checked can be evaluated (i.e. ExpressionBuffer)
    void initPreconditions() {
        baselineExpressionIsAboveCutoffAndForFilterFactors.setCutoff(requestContext.getCutoff())
                .setFilterFactors(requestContext.getSelectedFilterFactors());
        baselineProfileIsSpecific.setAllQueryFactors(requestContext.getAllQueryFactors())
                .setSelectedQueryFactors(requestContext.getSelectedQueryFactors())
                .setSpecific(requestContext.isSpecific());
    }

    public BaselineProfileConditionalBuilder forGeneIdAndName(String geneId, String geneName) {
        baselineProfile = new BaselineProfile(geneId, geneName);
        initPreconditions();
        return this;
    }

    public BaselineProfileConditionalBuilder addExpression(BaselineExpression expression) {
        checkState(baselineProfile != null, "Please invoke forGeneID before create");
        if (baselineExpressionIsAboveCutoffAndForFilterFactors.apply(expression)) {
            baselineProfile.add(requestContext.getQueryFactorType(), expression);
        }
        return this;
    }

    public BaselineProfile create() {
        checkState(baselineProfile != null, "Please invoke forGeneID before create");

        if (baselineProfileIsSpecific.apply(baselineProfile)){
            return baselineProfile;
        }
        return null;
    }
}