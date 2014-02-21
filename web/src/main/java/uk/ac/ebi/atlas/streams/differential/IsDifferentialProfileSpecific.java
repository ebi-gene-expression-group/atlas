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

package uk.ac.ebi.atlas.streams.differential;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class IsDifferentialProfileSpecific implements Predicate<DifferentialProfile> {

    private Set<Contrast> selectedQueryContrasts;
    private Regulation regulation;
    private Set<Contrast> allQueryFactors;

    public IsDifferentialProfileSpecific(Set<Contrast> selectedQueryContrasts, Set<Contrast> allQueryFactors, Regulation regulation) {
        checkArgument(!selectedQueryContrasts.isEmpty(),"selectedQueryContrasts is empty");
        checkArgument(!selectedQueryContrasts.isEmpty(),"allQueryFactors is empty");
        checkNotNull(regulation, "regulation is null");

        this.selectedQueryContrasts = selectedQueryContrasts;
        this.regulation = regulation;
        this.allQueryFactors = allQueryFactors;
    }

    @Override
    public boolean apply(DifferentialProfile differentialProfile) {
        double averageExpressionLevelOnSelectedQueryContrasts = differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts, regulation);
        Set<Contrast> nonSelectedQueryContrasts = Sets.difference(allQueryFactors, selectedQueryContrasts);
        double minExpressionLevelOnNonSelectedQueryContrasts = differentialProfile.getMinExpressionLevelOn(nonSelectedQueryContrasts, regulation);

        return averageExpressionLevelOnSelectedQueryContrasts < minExpressionLevelOnNonSelectedQueryContrasts;
    }

}
