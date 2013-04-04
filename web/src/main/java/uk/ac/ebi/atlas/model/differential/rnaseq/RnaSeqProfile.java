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

package uk.ac.ebi.atlas.model.differential.rnaseq;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilePrecondition;
import uk.ac.ebi.atlas.model.differential.RnaSeqProfilePrecondition;

import javax.inject.Inject;
import javax.inject.Named;

public class RnaSeqProfile extends DifferentialProfile<DifferentialExpression> {


    public RnaSeqProfile(String geneId) {
        super(geneId);
    }

    @Named
    @Scope("prototype")
    public static class RnaSeqProfileBuilder extends DifferentialProfileBuilder<RnaSeqProfile, RnaSeqRequestContext> {

        @Inject
        public void setDifferentialProfilePrecondition(RnaSeqProfilePrecondition differentialProfilePrecondition) {
            super.setDifferentialProfilePrecondition(differentialProfilePrecondition);
        }

        @Inject
        protected RnaSeqProfileBuilder(RnaSeqRequestContext requestContext) {
            super(requestContext);
        }

        @Override
        protected void initExtraProfilePreconditions(DifferentialProfilePrecondition<RnaSeqProfile> differentialProfilePrecondition, RnaSeqRequestContext requestContext) {

        }

        @Override
        protected RnaSeqProfile createProfile(String geneId) {
            return new RnaSeqProfile(geneId);
        }
    }

}