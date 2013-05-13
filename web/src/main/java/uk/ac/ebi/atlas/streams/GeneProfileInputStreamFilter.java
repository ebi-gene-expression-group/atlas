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

package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

public class GeneProfileInputStreamFilter<K, T extends Profile> extends ObjectInputStreamFilter {

    private Set<String> uppercaseGeneIDs;

    private Set<K> queryConditions;

    public GeneProfileInputStreamFilter(ObjectInputStream<T> geneProfileInputStream
                                        , Set<String> uppercaseGeneIDs, Set<K> queryConditions) {
        super(geneProfileInputStream);

        this.uppercaseGeneIDs = uppercaseGeneIDs;
        this.queryConditions = queryConditions;
    }

    @Override
    protected Predicate<Profile> getAcceptanceCriteria() {

        return new Predicate<Profile>() {
            @Override
            public boolean apply(Profile profile) {

                boolean checkGene = checkGeneId(profile.getId());
                return checkGene && (CollectionUtils.isEmpty(queryConditions) || hasTheRightExpressionProfile(profile));
            }

            private boolean hasTheRightExpressionProfile(Profile profile) {
                return profile.isExpressedOnAnyOf(queryConditions);
            }
        };

    }

    private boolean checkGeneId(String geneId) {
        return CollectionUtils.isEmpty(uppercaseGeneIDs)
                || uppercaseGeneIDs.contains(geneId.toUpperCase());
    }

}