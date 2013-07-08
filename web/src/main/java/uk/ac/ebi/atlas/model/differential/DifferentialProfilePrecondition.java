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

    private boolean specific;

    private Set<Contrast> selectedQueryContrasts;
    private Regulation regulation;
    private Set<Contrast> allQueryFactors;

    DifferentialProfilePrecondition setSelectedQueryContrasts(Set<Contrast> selectedQueryContrasts) {
        this.selectedQueryContrasts = selectedQueryContrasts;
        return this;
    }

    DifferentialProfilePrecondition setAllQueryFactors(Set<Contrast> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    DifferentialProfilePrecondition setRegulation(Regulation regulation) {
        this.regulation = regulation;
        return this;
    }

    DifferentialProfilePrecondition setSpecific(boolean specific) {
        this.specific = specific;
        return this;
    }

    public boolean apply(DifferentialProfile differentialProfile) {
        if (differentialProfile.isEmpty()) {
            return false;
        }

        if (!specific || selectedQueryContrasts.isEmpty()) {
            return true;
        }

        double averageExpressionLevelOnSelectedQueryContrasts = differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts, regulation);
        Set<Contrast> nonSelectedQueryContrasts = Sets.difference(allQueryFactors, selectedQueryContrasts);
        double minExpressionLevelOnNonSelectedQueryContrasts = differentialProfile.getMinExpressionLevelOn(nonSelectedQueryContrasts, regulation);

        return averageExpressionLevelOnSelectedQueryContrasts < minExpressionLevelOnNonSelectedQueryContrasts;

    }

}
