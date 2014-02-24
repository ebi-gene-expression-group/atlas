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

package uk.ac.ebi.atlas.streams.differential.microarray;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;

import static com.google.common.base.Preconditions.checkState;

// This is a reusable builder that can be called multiple times in a read loop.
// To start creating another instance call beginNewInstance
public class MicroarrayProfileReusableBuilder {

    private MicroarrayProfile profile;

    private Predicate<DifferentialExpression> expressionFilter;

    public MicroarrayProfileReusableBuilder(Predicate<DifferentialExpression> expressionFilter) {
        this.expressionFilter = expressionFilter;
    }

    public MicroarrayProfileReusableBuilder beginNewInstance(String geneId, String geneName, String designElement) {
        profile = new MicroarrayProfile(geneId, geneName, designElement);
        return this;
    }

    public MicroarrayProfileReusableBuilder addExpression(MicroarrayExpression expression) {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        if (expressionFilter.apply(expression)) {
            profile.add(expression);
        }
        return this;
    }

    public MicroarrayProfile create() {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        return profile;
    }
}