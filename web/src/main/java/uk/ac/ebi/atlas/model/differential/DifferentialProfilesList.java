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


import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;

public class DifferentialProfilesList extends ArrayList<DifferentialProfile> {

    private static final long serialVersionUID = -1678371004778942235L;

    private Integer totalResultCount = 0;

    public DifferentialProfilesList(Collection<DifferentialProfile> collection) {
        super(collection);
    }

    public DifferentialProfilesList() {
    }

    public DifferentialProfilesList getTop(int size) {
        return subList(0, size);
    }

    @Override
    public DifferentialProfilesList subList(int fromIndex, int toIndex) {
        checkArgument(toIndex >= 0, "Upper index value must be larger than 0");
        if (toIndex > this.size()) {
            return this;
        }
        return new DifferentialProfilesList(super.subList(fromIndex, toIndex));
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }
/*
    public Double getExpressionLevel(String geneId, Factor factor) {
        for (GeneProfile geneProfile : this) {
            if (geneId.equalsIgnoreCase(geneProfile.getGeneId())) {
                return geneProfile.getExpressionLevel(factor);
            }
        }
        return null;
    }


    public double getMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (GeneProfile geneProfile : this) {
            if (maxExpressionLevel < geneProfile.getMaxExpressionLevel()) {
                maxExpressionLevel = geneProfile.getMaxExpressionLevel();
            }
        }
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (GeneProfile geneProfile : this) {
            if (geneProfile.getMinExpressionLevel() < minExpressionLevel) {
                minExpressionLevel = geneProfile.getMinExpressionLevel();
            }
        }
        return minExpressionLevel;
    }


    */
}
