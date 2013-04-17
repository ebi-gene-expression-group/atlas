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

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfilePrecondition implements Predicate<DifferentialProfile> {

    private Set<Contrast> selectedQueryFactors;
    private Regulation regulation;
    private Set<Contrast> allQueryFactors;

    protected DifferentialProfilePrecondition setSelectedQueryFactors(Set<Contrast> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
        return this;
    }

    protected DifferentialProfilePrecondition setAllQueryFactors(Set<Contrast> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    protected DifferentialProfilePrecondition setRegulation(Regulation regulation) {
        this.regulation = regulation;
        return this;
    }

    public boolean apply(DifferentialProfile profile) {
        if (profile.isEmpty()) {
            return false;
        }

        double averageExpressionLevelOnSelectedFactors = averageExpressionLevelOnSelectedFactors(profile);
        double averageExpressionLevelOnNonSelectedFactors = averageExpressionLevelOnNonSelectedFactors(profile);

        return averageExpressionLevelOnNonSelectedFactors == 0 ||
                averageExpressionLevelOnSelectedFactors < averageExpressionLevelOnNonSelectedFactors;

    }

    private double averageExpressionLevelOnSelectedFactors(DifferentialProfile profile) {
        return profile.getAverageExpressionLevelOn(selectedQueryFactors, regulation);
    }

    private double averageExpressionLevelOnNonSelectedFactors(DifferentialProfile profile) {
        Set<Contrast> remainingFactors = Sets.difference(allQueryFactors, selectedQueryFactors);
        return profile.getAverageExpressionLevelOn(remainingFactors, regulation);
    }

}
