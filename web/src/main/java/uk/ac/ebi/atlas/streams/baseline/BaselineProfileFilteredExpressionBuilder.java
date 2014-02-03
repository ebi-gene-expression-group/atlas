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

package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;

import static com.google.common.base.Preconditions.checkState;

public class BaselineProfileFilteredExpressionBuilder extends BaselineProfileConditionalBuilder {

    private final String queryFactorType;
    private BaselineProfile baselineProfile;

    private Predicate<BaselineExpression> baselineExpressionFilter;

    public BaselineProfileFilteredExpressionBuilder(Predicate<BaselineExpression> baselineExpressionFilter,
                                                    String queryFactorType) {
        this.baselineExpressionFilter = baselineExpressionFilter;
        this.queryFactorType = queryFactorType;
    }

    @Override
    public BaselineProfileFilteredExpressionBuilder forGeneIdAndName(String geneId, String geneName) {
        baselineProfile = new BaselineProfile(geneId, geneName);
        return this;
    }

    @Override
    public BaselineProfileFilteredExpressionBuilder addExpression(BaselineExpression expression) {
        checkState(baselineProfile != null, "Please invoke forGeneID before create");
        if (baselineExpressionFilter.apply(expression)) {
            baselineProfile.add(queryFactorType, expression);
        }
        return this;
    }

    @Override
    public BaselineProfile create() {
        checkState(baselineProfile != null, "Please invoke forGeneID before create");

        return baselineProfile.isEmpty() ? null : baselineProfile;
    }
}