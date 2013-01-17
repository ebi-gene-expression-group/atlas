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

package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.FilterParameters;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.HashSet;
import java.util.Set;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    private Set<String> queryFactorValues;

    private Set<FactorValue> filterFactorValues;

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, FilterParameters filterParameters) {
        super(geneProfileInputStream);

        this.filterFactorValues = filterParameters.getFilterFactorValues();
        this.geneIDs = toUpperCase(filterParameters.getGeneIDs());
        this.queryFactorValues = filterParameters.getQueryFactorValues();
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                //ToDo: this need to be simplified when we remove organismParts....
                boolean hasAllFactorValues = CollectionUtils.isEmpty(filterFactorValues);
                hasAllFactorValues = hasAllFactorValues || profile.getAllFactorValues().containsAll(filterFactorValues);
                boolean checkGene = checkGeneId(profile.getGeneId(), profile.getGeneName());
                return hasAllFactorValues && checkGene
                        && (CollectionUtils.isEmpty(queryFactorValues) || hasTheRightExpressionProfile(profile));
            }

            private boolean hasTheRightExpressionProfile(GeneProfile geneProfile) {
                return geneProfile.isExpressedOnAnyOf(queryFactorValues);
            }
        };

    }

    private boolean checkGeneId(String geneId, String geneName) {
        return CollectionUtils.isEmpty(geneIDs)
                || geneIDs.contains(geneId.toUpperCase())
                || (geneName != null && geneIDs.contains(geneName.toUpperCase()));
    }

    private Set<String> toUpperCase(Set<String> strings) {
        Set<String> capitalizedStrings = new HashSet<>();
        if (strings != null) {
            for (String s : strings) {
                capitalizedStrings.add(s.toUpperCase());
            }
        }
        return capitalizedStrings;
    }
}