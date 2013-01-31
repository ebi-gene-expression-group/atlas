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

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.*;

@Named("filterByMenuBuilder")
@Scope("prototype")
public class FilterByMenuBuilder {

    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private SortedSetMultimap<String, Factor> allFactorNames;

    private SortedSetMultimap<Factor, Factor> validFactorValueCombinations;

    public FilterByMenuBuilder() {
    }

    public SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> build(Experiment experiment) {

        validFactorValueCombinations = experiment.getValidFactorValueCombinations();

        allFactorNames = factorValuesByName(validFactorValueCombinations.keySet());

        // build filter by menu map here, structure:
        // factor name 1 > factor value 1 > factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> filterByMenu = new TreeMap<>();

        // first level: factor name
        for (String firstFactorName : allFactorNames.keySet()) {

            filterByMenu.put(firstFactorName, buildSecondMenuLevel(firstFactorName));

        }

        return filterByMenu;
    }

    private SortedMap<String, SortedMap<String, SortedMap<String, String>>> buildSecondMenuLevel(String firstFactorName) {

        // factor value 1 > factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, SortedMap<String, String>>> secondMenuLevel = new TreeMap<>();

        // second level: factor value choices per factor name, all are valid
        for (Factor firstFactor : allFactorNames.get(firstFactorName)) {

            secondMenuLevel.put(firstFactor.getValue(), buildThirdMenuLevel(firstFactor));

        }

        return secondMenuLevel;
    }

    private SortedMap<String, SortedMap<String, String>> buildThirdMenuLevel(Factor firstFactor) {

        // factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, String>> thirdMenuLevel = new TreeMap<>();

        // index second level factor names
        SortedSetMultimap<String, Factor> secondFactorNames =
                factorValuesByName(validFactorValueCombinations.get(firstFactor));

        // third level: factor value choices per factor name, restricted by previous
        for (String secondFactorName : secondFactorNames.keySet()) {

            // get remainder of factor names
            SortedSet<String> remainingFactorNames = new TreeSet<>(allFactorNames.keySet());
            remainingFactorNames.remove(firstFactor.getName());
            remainingFactorNames.remove(secondFactorName);

            // TODO: what in case there are more than 3 possible factor types?
            // arbitrarily taking first of remaining factor names as query factor type
            String firstRemainingFactorName = remainingFactorNames.first();
            SortedSet<Factor> factorValuesForFirstRemainingFactorName = allFactorNames.get(firstRemainingFactorName);
            Factor firstArbitraryFactor = factorValuesForFirstRemainingFactorName.first();
            String queryFactorType = firstArbitraryFactor.getType();

            // third level: factor name
            SortedMap<String, String> thirdLevel = this.buildForthMenuLevel(queryFactorType, firstFactor, secondFactorNames.get(secondFactorName));
            if (!thirdMenuLevel.containsKey(secondFactorName)) {
                thirdMenuLevel.put(secondFactorName, thirdLevel);
            }
            SortedMap<String, String> stringStringSortedMap = thirdMenuLevel.get(secondFactorName);
            stringStringSortedMap.putAll(thirdLevel);
        }

        return thirdMenuLevel;
    }

    private SortedMap<String, String> buildForthMenuLevel(String queryFactorType, Factor firstFactor, SortedSet<Factor> secondFactors) {

        // factor value 2 > link
        SortedMap<String, String> forthMenuLevel = new TreeMap<>();

        // forth level: factor value choices for second factor name
        for (Factor secondFactor : secondFactors) {
            String link = getJsonUrl(queryFactorType, firstFactor, secondFactor);
            forthMenuLevel.put(secondFactor.getValue(), link);
        }
        return forthMenuLevel;
    }

    protected String getJsonUrl(String queryFactorType, Factor firstFactor, Factor secondFactor){
        return new Gson().toJson(buildFactorsCombination(queryFactorType, firstFactor, secondFactor));
    }

    private FactorsCombination buildFactorsCombination(String queryFactorType, Factor firstFactor, Factor secondFactor) {
        return new FactorsCombination(queryFactorType, firstFactor, secondFactor);
    }

    private final class FactorsCombination {

        private final String queryFactorType;

        private final String filterFactorsURL;

        FactorsCombination(String queryFT, Factor firstFactor, Factor secondFactor) {
            queryFactorType = queryFT;
            filterFactorsURL = serialize(firstFactor) + ',' + serialize(secondFactor);
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getFilterFactorsURL() {
            return filterFactorsURL;
        }

    }

    protected SortedSetMultimap<String, Factor> factorValuesByName(Set<Factor> factors) {
        // using factor names here for better readability and compatibility with experiment design page
        SortedSetMultimap<String, Factor> factorValuesByName = TreeMultimap.create();
        for (Factor factor : factors) {
            factorValuesByName.put(factor.getName(), factor);
        }
        return factorValuesByName;
    }

    protected String serialize(Factor factor) {
        return factor.getType() + FACTOR_VALUE_SEPARATOR + factor.getValue();
    }
}