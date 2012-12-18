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
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.HashSet;
import java.util.Set;

public class GeneProfileInputStreamFilter extends ObjectInputStreamFilter<GeneProfile> {

    private Set<String> geneIDs;

    private Set<String> factorValues;

    private Set<FactorValue> filterFactorValues = new HashSet<>();

    public GeneProfileInputStreamFilter(ObjectInputStream<GeneProfile> geneProfileInputStream, Set<String> filterFactorValues, Set<String> geneIDs, Set<String> factorValues) {
        super(geneProfileInputStream);

        // Turns a factor specification string into a FactorValue list.
        // Splits at : between type and value
        for (String filter : filterFactorValues) {
            String[] split = filter.split(":");
            if (split.length == 2) {
                FactorValue factorValue = new FactorValue(split[0], "", split[1]);
                this.filterFactorValues.add(factorValue);
            }
        }

        this.geneIDs = toUpperCase(geneIDs);
        this.factorValues = factorValues;
    }

    @Override
    protected Predicate<GeneProfile> getAcceptanceCriteria() {

        return new Predicate<GeneProfile>() {
            @Override
            public boolean apply(GeneProfile profile) {
                boolean checkGene = checkGeneId(profile.getGeneId(), profile.getGeneName());
                boolean isExpressed = profile.isExpressedAtMostOn(factorValues);
                boolean hasFactor = CollectionUtils.isEmpty(filterFactorValues);
                hasFactor = hasFactor || profile.getAllFactorValues().containsAll(filterFactorValues);
                return checkGene && isExpressed && hasFactor;
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