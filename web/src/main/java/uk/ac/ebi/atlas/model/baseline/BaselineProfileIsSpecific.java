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

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfileIsSpecific implements Predicate<BaselineProfile>, Serializable {

    private boolean specific;

    private Set<Factor> selectedQueryFactors;

    private Set<Factor> allQueryFactors;

    public BaselineProfileIsSpecific() {
    }

    @Override
    public boolean apply(BaselineProfile baselineProfile) {

        if (baselineProfile.isEmpty()){
            return false;
        }

        if (!specific || selectedQueryFactors.isEmpty()) {
            return true;
        }

        return isOverExpressedInSelectedQueryFactors(baselineProfile);

    }

    boolean isOverExpressedInSelectedQueryFactors(BaselineProfile baselineProfile) {

        double averageOnSelectedQueryFactors = baselineProfile.getAverageExpressionLevelOn(selectedQueryFactors);
        Set<Factor> nonSelectedQueryFactors = Sets.newHashSet(allQueryFactors);
        nonSelectedQueryFactors.removeAll(selectedQueryFactors);

        double maxOnNonSelectedQueryFactors = baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactors);

        return averageOnSelectedQueryFactors > maxOnNonSelectedQueryFactors;

    }

    BaselineProfileIsSpecific setSpecific(boolean specific) {
        this.specific = specific;
        return this;
    }

    BaselineProfileIsSpecific setSelectedQueryFactors(Set<Factor> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
        return this;
    }

    BaselineProfileIsSpecific setAllQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }
}
