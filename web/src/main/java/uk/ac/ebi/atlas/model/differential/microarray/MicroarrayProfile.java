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

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilePrecondition;
import uk.ac.ebi.atlas.model.differential.MicroarrayProfilePrecondition;

import javax.inject.Inject;
import javax.inject.Named;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression> {

    public MicroarrayProfile(String geneId) {
        super(geneId);
    }

    @Named
    @Scope("prototype")
    public static class MicroarrayProfileBuilder extends DifferentialProfileBuilder<MicroarrayProfile, MicroarrayRequestContext> {

        @Inject
        protected MicroarrayProfileBuilder(MicroarrayRequestContext requestContext) {
            super(requestContext);
        }

        @Inject
        public void setDifferentialProfilePrecondition(MicroarrayProfilePrecondition differentialProfilePrecondition) {
            super.setDifferentialProfilePrecondition(differentialProfilePrecondition);
        }

        @Override
        protected void initExtraProfilePreconditions(DifferentialProfilePrecondition<MicroarrayProfile> precondition, MicroarrayRequestContext requestContext) {
            ((MicroarrayProfilePrecondition) precondition).setArrayDesignAcc(requestContext.getArrayDesignName());
        }

        @Override
        protected MicroarrayProfile createProfile(String geneId) {
            return new MicroarrayProfile(geneId);
        }
    }
}
