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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class ExperimentalFactors implements Serializable {

    private SortedSetMultimap<String, Factor> factorsByName = TreeMultimap.create();

    private Map<String, String> factorNamesByType = new HashMap<>();

    private Collection<FactorGroup> factorGroups = new HashSet<>();

    private SortedSetMultimap<Factor, Factor> coOccurringFactors = TreeMultimap.create();

    private Set<String> menuFilterFactorTypes;

    ExperimentalFactors(SortedSetMultimap<String, Factor> factorsByName, Map<String, String> factorNamesByType, Collection<FactorGroup> factorGroups, SortedSetMultimap<Factor, Factor> coOccurringFactors, Set<String> menuFilterFactorTypes) {
        this.factorsByName = factorsByName;
        this.factorNamesByType = factorNamesByType;
        this.factorGroups = factorGroups;
        this.coOccurringFactors = coOccurringFactors;
        this.menuFilterFactorTypes = menuFilterFactorTypes;
    }

    public String getFactorName(String type) {

        checkState(factorNamesByType.containsKey(type), "Cannot request a factor name for an unknown type.");

        return factorNamesByType.get(type);
    }

    //ToDo: this is only used to build factor filter menu, maybe should be encapsulated in a menu builder and the menu builder could be used by a menu cache loader
    public SortedSet<Factor> getCoOccurringFactors(Factor factor) {
        return coOccurringFactors.get(factor);
    }

    public SortedSet<Factor> getFactorsByType(String type) {

        String factorName = factorNamesByType.get(type);

        checkArgument(factorName != null, "There is no factor with the given factor type");

        return ImmutableSortedSet.copyOf(factorsByName.get(factorName));

    }

    public SortedSet<Factor> getFilteredFactors(final Set<Factor> filterFactors) {

        if (CollectionUtils.isEmpty(filterFactors)) {
            return getAllFactors();
        }

        TreeSet<Factor> factorsByType = Sets.newTreeSet();

        for (FactorGroup factorGroup : factorGroups) {

            List<Factor> remainingFactors = factorGroup.remove(filterFactors);
            if (remainingFactors.size() == 1) {
                factorsByType.add(remainingFactors.get(0));
            }
        }

        return factorsByType;

    }

    public SortedSet<String> getMenuFilterFactorNames() {

        SortedSet<String> factorNames = Sets.newTreeSet();

        for (String type : menuFilterFactorTypes) {

            String factorName = getFactorName(type);
            factorNames.add(factorName);
        }

        return factorNames;
    }

    public SortedSet<Factor> getAllFactors() {
        return ImmutableSortedSet.copyOf(factorsByName.values());
    }

}
