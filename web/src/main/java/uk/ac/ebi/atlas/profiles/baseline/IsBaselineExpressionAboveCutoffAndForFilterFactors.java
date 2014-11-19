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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@Scope("prototype")
public class IsBaselineExpressionAboveCutoffAndForFilterFactors implements Predicate<BaselineExpression>, Serializable {

    private double cutoff;

    private Set<Factor> filterFactors = new HashSet<>();

    public IsBaselineExpressionAboveCutoffAndForFilterFactors() {
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setFilterFactors(Set<Factor> filterFactors) {
        this.filterFactors = filterFactors;
        return this;
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    @Override
    public boolean apply(BaselineExpression expression) {
        return checkFilterFactors(expression) && (!expression.isKnown() || expression.isGreaterThan(cutoff));
    }

    protected boolean checkFilterFactors(BaselineExpression expression) {
        return (CollectionUtils.isEmpty(filterFactors)
        || expression.containsAll(filterFactors));
    }


}
