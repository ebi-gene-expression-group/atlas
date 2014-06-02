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

package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression> {

    // is part of the identity of the profile, ie: you can have two profiles for same
    // gene but different design element, eg: http://ves-hx-76:8080/gxa/experiments/E-MTAB-1066?geneQuery=Mbs
    private final String designElementName;

    public MicroarrayProfile(String geneId, String geneName, String designElementName) {
        super(geneId, geneName);
        this.designElementName = designElementName;
    }

    //It's used in jsp EL
    public String getDesignElementName() {
        return designElementName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneName", getName())
                .add("designElementName", designElementName)
                .add("expressions", expressionsByCondition)
                .toString();
    }

    @Override
    public MicroarrayProfile add(MicroarrayExpression expression) {
        return (MicroarrayProfile)super.add(expression);
    }

    public static MicroarrayProfile create(String geneId, String geneName, String designElementName) {
        return new MicroarrayProfile(geneId, geneName, designElementName);
    }
}
