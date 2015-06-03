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

package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;

import static com.google.common.base.Preconditions.checkState;

// This is a reusable builder that can be called multiple times in a read loop.
// To start creating another instance call beginNewInstance
public class BaselineProfileReusableBuilder {

    private final String queryFactorType;
    private BaselineProfile baselineProfile;

    private Predicate<BaselineExpression> baselineExpressionFilter;

    public BaselineProfileReusableBuilder(Predicate<BaselineExpression> baselineExpressionFilter, String queryFactorType) {
        this.baselineExpressionFilter = baselineExpressionFilter;
        this.queryFactorType = queryFactorType;
    }

    public BaselineProfileReusableBuilder beginNewInstance(String geneId, String geneName) {
        baselineProfile = new BaselineProfile(geneId, geneName);
        return this;
    }

    public BaselineProfileReusableBuilder addExpression(BaselineExpression expression) {
        checkState(baselineProfile != null, "Please invoke beginNewInstance before create");
        if (baselineExpressionFilter.apply(expression)) {
            baselineProfile.add(queryFactorType, expression);
        }
        return this;
    }

    public BaselineProfile create() {
        checkState(baselineProfile != null, "Please invoke beginNewInstance before create");
        return baselineProfile;
    }
}