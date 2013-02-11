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

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentalFactors {

    private SortedSetMultimap<String, Factor> factorsByName = TreeMultimap.create();

    private Map<String, String> factorNamesByType = new HashMap<>();

    private Collection<FactorGroup> factorGroups = new HashSet<>();

    private SortedSetMultimap<Factor, Factor> coOccurringFactors = TreeMultimap.create();

    ExperimentalFactors() {
    }

    public String getFactorName(String type) {

        checkState(factorNamesByType.containsKey(type), "Cannot request a factor name for an unknown type.");

        return factorNamesByType.get(type);
    }

    ExperimentalFactors addFactorGroup(FactorGroup factorGroup) {
        factorGroups.add(factorGroup);

        for (Factor factor : factorGroup) {

            factorsByName.put(factor.getName(), factor);
            factorNamesByType.put(factor.getType(), factor.getName());

            addToFactorCombinations(factorGroup, factor);
        }
        return this;
    }

    public SortedSet<String> getAllFactorNames() {
        return ImmutableSortedSet.copyOf(factorsByName.keySet());
    }

    public SortedSet<Factor> getFactorsByName(String name) {
        return ImmutableSortedSet.copyOf(factorsByName.get(name));
    }

    public SortedSet<Factor> getCoOccurringFactors(Factor factor) {
        return coOccurringFactors.get(factor);
    }

    //ToDo: this would become experimentalFactors.byType(...)
    public SortedSet<Factor> getFactorsByType(String type) {

        String factorName = factorNamesByType.get(type);

        checkArgument(factorName != null, "There is no factor with the given factor type");

        return ImmutableSortedSet.copyOf(factorsByName.get(factorName));

    }

    void addToFactorCombinations(FactorGroup factorGroup, Factor factor) {
        for (Factor value : factorGroup) {
            if (!value.equals(factor)) {
                coOccurringFactors.put(factor, value);
            }
        }
    }

    //ToDo: this would be: experimentalFactor.sliceBy(... FilterFactor).byType(queryFactorType) ... or any better name?
    public SortedSet<Factor> getFilteredFactors(final Set<Factor> filterFactors, String queryFactorType) {

        for (Factor filterFactor : filterFactors) {
            checkArgument(!filterFactor.getType().equals(queryFactorType));
        }

        SortedSet<Factor> factorsByType = getFactorsByType(queryFactorType);

        //ToDo: this is a patch to handle experiments that have multiple factor types but where we are not interested in filtering (e.g. Illumina)
        //ToDo: we should rather avoid storing inside expressions factors that are useless (i.e. illumina experiment)
        if (CollectionUtils.isEmpty(filterFactors)) {
            return factorsByType;
        }

        return Sets.filter(factorsByType, new Predicate<Factor>() {
            @Override
            public boolean apply(Factor factorOfTheSelectedType) {
                FactorGroup factorGroup = new FactorSet(filterFactors).add(factorOfTheSelectedType);
                return factorGroups.contains(factorGroup);
            }
        });

    }

    public Set<Factor> getAllFactors() {
        return ImmutableSet.copyOf(coOccurringFactors.keySet());
    }

}
