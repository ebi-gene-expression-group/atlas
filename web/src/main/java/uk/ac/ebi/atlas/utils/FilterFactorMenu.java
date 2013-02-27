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

package uk.ac.ebi.atlas.utils;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.web.FactorsConverter;

import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class FilterFactorMenu {

    private final SortedSet<Factor> factors = Sets.newTreeSet();

    private ExperimentalFactors experimentalFactors;
    private FactorsConverter factorConverter;

    public FilterFactorMenu(ExperimentalFactors experimentalFactors, Set<Factor> setOfFactors) {
        this.experimentalFactors = experimentalFactors;
        factors.addAll(setOfFactors);
    }

    protected SortedSet<Factor> getFactors() {
        return factors;
    }

    public FilterFactorMenu filterOutByFactor(Factor factor) {

        checkNotNull(factor, "Cannot filter by null factor!");

        Set<String> remainingNames = getAllFactorNames();
        String factorName = experimentalFactors.getFactorName(factor.getType());

        remainingNames.remove(factorName);

        Set<Factor> validFactors = Sets.newHashSet();
        for (String name : remainingNames) {
            SortedSet<Factor> factorsForFactorName = getFactorsForFactorName(name);
            validFactors.addAll(factorsForFactorName);
        }

        SortedSet<Factor> coOccurring = experimentalFactors.getCoOccurringFactors(factor);
        validFactors.retainAll(coOccurring);

        FilterFactorMenu filterFactorMenu = new FilterFactorMenu(experimentalFactors, validFactors);
        filterFactorMenu.setFactorConverter(factorConverter);
        return filterFactorMenu;
    }

    public SortedSet<Factor> getFactorsForFactorName(final String name) {

        return Sets.filter(factors, new Predicate<Factor>() {
            @Override
            public boolean apply(Factor selectedFactor) {
                String factorName = experimentalFactors.getFactorName(selectedFactor.getType());
                return factorName.equals(name);
            }
        });
    }

    public SortedSet<String> getAllFactorNames() {
        SortedSet<String> remainingFactorNames = Sets.newTreeSet();
        for (Factor factor : factors) {
            String factorName = experimentalFactors.getFactorName(factor.getType());
            remainingFactorNames.add(factorName);
        }
        return remainingFactorNames;
    }

    public String resolveTypeForName(String factorName) {

        for (Factor factor : getFactorsForFactorName(factorName)) {
            return factor.getType();
        }
        return null;
    }

    public String getLink(String queryFactorType, Factor... factors) {
        String link = getJsonUrl(queryFactorType, factors);
        return link;
    }

    protected String getJsonUrl(String queryFactorType, Factor... factors) {
        return new Gson().toJson(buildFactorsCombination(queryFactorType, factors));
    }

    private FactorsCombination buildFactorsCombination(String queryFactorType, Factor... factors) {
        return new FactorsCombination(queryFactorType, factors);
    }

    public void setFactorConverter(FactorsConverter factorConverter) {
        this.factorConverter = factorConverter;
    }


    private final class FactorsCombination {

        private String queryFactorType;

        private String serializedFactors;

        FactorsCombination(String queryFactorType, Factor... factors) {
            this.queryFactorType = queryFactorType;
            serializedFactors = factorConverter.serializeFactors(Lists.newArrayList(factors));
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getSerializedFactors() {
            return serializedFactors;
        }

    }
}
