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

package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;

import java.util.Set;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> uppercaseGeneIDs;

    private Set<Factor> queryFactors;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> uppercaseGeneIDs, Set<Factor> queryFactors) {
        super(geneProfileInputStream);

        this.uppercaseGeneIDs = uppercaseGeneIDs;
        this.queryFactors = queryFactors;
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {

                boolean checkGene = checkGeneId(profile.getGeneId());
                return checkGene && (CollectionUtils.isEmpty(queryFactors) || hasTheRightExpressionProfile(profile));
            }

            private boolean hasTheRightExpressionProfile(GeneProfile geneProfile) {
                return geneProfile.isExpressedOnAnyOf(queryFactors);
            }
        };

    }

    private boolean checkGeneId(String geneId) {
        return CollectionUtils.isEmpty(uppercaseGeneIDs)
                || uppercaseGeneIDs.contains(geneId.toUpperCase());
    }

}