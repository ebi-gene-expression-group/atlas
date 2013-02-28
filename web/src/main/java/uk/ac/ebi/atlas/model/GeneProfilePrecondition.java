/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Set;

//ToDo: This class in not just "expressionPrecondition", but also container for other request parameters. Maybe we need to create "GeneProfilePrecondition" class.
@Named
@Scope("request")
public class GeneProfilePrecondition implements Predicate<GeneProfile>, Serializable {

    private boolean specific;

    private Set<Factor> selectedQueryFactors;

    private Set<Factor> allQueryFactors;

    public GeneProfilePrecondition() {
    }

    @Override
    public boolean apply(GeneProfile geneProfile) {

        if (!specific || selectedQueryFactors.isEmpty()) {
            return true;
        }

        double averageOnSelected = geneProfile.getAverageExpressionLevelOn(selectedQueryFactors);
        Set<Factor> remainingFactors = Sets.newHashSet(allQueryFactors);
        remainingFactors.removeAll(selectedQueryFactors);

        double averageOnRest = geneProfile.getAverageExpressionLevelOn(remainingFactors);

        return (averageOnSelected / averageOnRest) >= 1;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public void setSelectedQueryFactors(Set<Factor> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    public void setAllQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }
}
