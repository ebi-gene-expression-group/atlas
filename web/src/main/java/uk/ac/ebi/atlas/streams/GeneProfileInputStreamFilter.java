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
import com.google.common.base.Predicates;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneProfileInputStreamFilter<K, T extends Profile> extends ObjectInputStreamFilter {

    private Predicate<Profile> geneProfileAcceptanceCriteria;

    public GeneProfileInputStreamFilter(ObjectInputStream<T> geneProfileInputStream
                                        , Collection<String> uppercaseGeneIDs, Set<K> queryConditions) {
        super(geneProfileInputStream);

        geneProfileAcceptanceCriteria = Predicates.and(new GeneIdsPredicate(uppercaseGeneIDs),
                                                       new GeneProfilePredicate(queryConditions));
    }

    public GeneProfileInputStreamFilter(ObjectInputStream<T> geneProfileInputStream, Set<K> queryConditions) {
        super(geneProfileInputStream);

        geneProfileAcceptanceCriteria = new GeneProfilePredicate(queryConditions);
    }

    @Override
    protected Predicate<Profile> getAcceptanceCriteria() {
        return geneProfileAcceptanceCriteria;
    }

    class GeneIdsPredicate implements Predicate<Profile> {
        private Collection<String> uppercaseGeneIDs;

        GeneIdsPredicate(Collection<String> uppercaseGeneIDs){
            checkArgument(CollectionUtils.isNotEmpty(uppercaseGeneIDs));
            this.uppercaseGeneIDs = uppercaseGeneIDs;
        }

        @Override
        public boolean apply(Profile geneProfile) {
            return uppercaseGeneIDs.contains(geneProfile.getId().toUpperCase());
        }

    };

    class GeneProfilePredicate<K> implements Predicate<Profile> {
        private Set<K> queryConditions;

        GeneProfilePredicate(Set<K> queryConditions){
            this.queryConditions = queryConditions;
        }

        @Override
        public boolean apply(Profile profile) {
            return CollectionUtils.isEmpty(queryConditions) || profile.isExpressedOnAnyOf(queryConditions);
        }

    };


}