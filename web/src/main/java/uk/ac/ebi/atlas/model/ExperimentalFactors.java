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
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class ExperimentalFactors {

    private SortedSetMultimap<String, Factor> factorsByName = TreeMultimap.create();

    private String defaultQueryFactorType;

    private Map<String, String> factorNamesByType = new HashMap<>();

    public Collection<FactorGroup> factorGroups = new HashSet<>();

    private Set<Factor> defaultFilterFactors = new HashSet<>();

    private SortedSetMultimap<Factor, Factor> validFactorCombinations = TreeMultimap.create();

    protected ExperimentalFactors(Collection<FactorGroup> factorGroups, String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        for (FactorGroup factorGroup : factorGroups) {
            addFactorGroup(factorGroup);
        }
        this.defaultQueryFactorType = defaultQueryFactorType;
        setDefaultFilterFactors(defaultFilterFactors);
    }

    public final String getFactorName(String type) {
        return factorNamesByType.get(type);
    }

    protected final ExperimentalFactors addFactorGroup(FactorGroup factorGroup) {
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

    public SortedSet<Factor> getFactorsByType(String type) {

        String factorName = factorNamesByType.get(type);

        checkArgument(factorName != null, "There is no factor with the given factor type");

        return ImmutableSortedSet.copyOf(factorsByName.get(factorName));

    }

    protected final void addToFactorCombinations(FactorGroup factorGroup, Factor factor) {
        for (Factor value : factorGroup) {
            if (!value.equals(factor)) {
                validFactorCombinations.put(factor, value);
            }
        }
    }

    protected final void setDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        for (Factor defaultFilterFactor : defaultFilterFactors) {
            //we need to set the name because defaultFilterFactors config file doesn't contain factor names
            defaultFilterFactor.setName(getFactorName(defaultFilterFactor.getType()));
        }
        this.defaultFilterFactors = Collections.unmodifiableSet(defaultFilterFactors);
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<Factor> getDefaultFilterFactors() {
        return defaultFilterFactors;
    }

    public SortedSetMultimap<Factor, Factor> getValidFactorCombinations() {
        return validFactorCombinations;
    }

    public SortedSet<Factor> getFilteredFactors(final Set<Factor> filterFactors, String queryFactorType) {

        for (Factor filterFactor : filterFactors) {
            checkArgument(!filterFactor.getType().equals(queryFactorType));
        }

        SortedSet<Factor> factorsByType = getFactorsByType(queryFactorType);

        //ToDo: this is a nasty trick to handle experiments that have multiple factor types but where we are not interested in filtering (e.g. Illumina)
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
}
