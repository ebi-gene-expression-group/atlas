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

import com.google.gson.Gson;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.streams.FilterParameters;

import java.util.*;

public class FilterByMenuBuilder {

    // does the serialisation to JSON
    private static final Gson gson = new Gson();

    private FilterByMenuBuilder() {
    }

    public static SortedMap<String, SortedSet<FactorValue>> indexFactorValuesByName(Set<FactorValue> factorValues) {
        // using factor names here for better readability and compatibility with experiment design page
        SortedMap<String, SortedSet<FactorValue>> allFactorNames = new TreeMap<>();
        for (FactorValue key : factorValues) {
            if (!allFactorNames.containsKey(key.getName())) {
                allFactorNames.put(key.getName(), new TreeSet<FactorValue>());
            }
            SortedSet<FactorValue> factorValuesForName = allFactorNames.get(key.getName());
            factorValuesForName.add(key);
        }
        return allFactorNames;
    }

    public static SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> buildFilterByMenu(
            SortedMap<String, SortedSet<FactorValue>> allFactorNames,
            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations) {

        // build filter by menu map here, structure:
        // factor name 1 > factor value 1 > factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> filterByMenu = new TreeMap<>();

        // first level: factor name
        for (String firstFactorName : allFactorNames.keySet()) {

            filterByMenu.put(firstFactorName, buildSecondMenuLevel(allFactorNames, validFactorValueCombinations, firstFactorName));

        }

        return filterByMenu;
    }

    private static SortedMap<String, SortedMap<String, SortedMap<String, String>>> buildSecondMenuLevel(SortedMap<String, SortedSet<FactorValue>> allFactorNames, SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations, String firstFactorName) {

        // factor value 1 > factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, SortedMap<String, String>>> secondMenuLevel = new TreeMap<>();

        // second level: factor value choices per factor name, all are valid
        for (FactorValue firstFactorValue : allFactorNames.get(firstFactorName)) {

            secondMenuLevel.put(firstFactorValue.getValue(),
                    buildThirdMenuLevel(allFactorNames, validFactorValueCombinations, firstFactorValue));

        }

        return secondMenuLevel;
    }

    private static SortedMap<String, SortedMap<String, String>> buildThirdMenuLevel(SortedMap<String, SortedSet<FactorValue>> allFactorNames, SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations, FactorValue firstFactorValue) {

        // factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, String>> thirdMenuLevel = new TreeMap<>();

        // index second level factor names
        SortedMap<String, SortedSet<FactorValue>> secondFactorNames =
                indexFactorValuesByName(validFactorValueCombinations.get(firstFactorValue));

        // third level: factor value choices per factor name, restricted by previous
        for (String secondFactorName : secondFactorNames.keySet()) {

            // get remainder of factor names
            SortedSet<String> remainingFactorNames = new TreeSet<>(allFactorNames.keySet());
            remainingFactorNames.remove(firstFactorValue.getName());
            remainingFactorNames.remove(secondFactorName);

            // third level: factor name
            SortedMap<String, String> thirdLevel = buildForthMenuLevel(allFactorNames, firstFactorValue, secondFactorNames.get(secondFactorName), remainingFactorNames);
            if (!thirdMenuLevel.containsKey(secondFactorName)) {
                thirdMenuLevel.put(secondFactorName, thirdLevel);
            }
            SortedMap<String, String> stringStringSortedMap = thirdMenuLevel.get(secondFactorName);
            stringStringSortedMap.putAll(thirdLevel);
        }

        return thirdMenuLevel;
    }

    private static SortedMap<String, String> buildForthMenuLevel(SortedMap<String, SortedSet<FactorValue>> allFactorNames, FactorValue firstFactorValue, SortedSet<FactorValue> secondFactorValues, SortedSet<String> remainingFactorNames) {
        // TODO: what in case there are more than 3 possible factor types?

        // factor value 2 > link
        SortedMap<String, String> forthMenuLevel = new TreeMap<>();

        // forth level: factor value choices for second factor name
        for (FactorValue secondFactorValue : secondFactorValues) {
            // arbitrarily taking first of remaining factor names as query factor type
            String firstRemainingFactorName = remainingFactorNames.first();
            SortedSet<FactorValue> factorValuesForFirstRemainingFactorName = allFactorNames.get(firstRemainingFactorName);
            FactorValue firstArbitraryFactorValue = factorValuesForFirstRemainingFactorName.first();
            String factorType = firstArbitraryFactorValue.getType();
            String link = gson.toJson(buildFilterFactorValueURL(factorType, firstFactorValue, secondFactorValue));
            forthMenuLevel.put(secondFactorValue.getValue(), link);
        }

        return forthMenuLevel;
    }

    public static SortedSet<FactorValue> extractSelectedFactorValues(SortedMap<String, SortedSet<FactorValue>> allFactorNames, FilterParameters parameters) {
        // construct label above filter by menu
        SortedSet<FactorValue> selectedFactorValues = new TreeSet<>();
        for (String name : allFactorNames.keySet()) {
            selectedFactorValues.addAll(getCompleteFactorValues(allFactorNames, parameters, name));
        }
        return selectedFactorValues;
    }

    private static SortedSet<FactorValue> getCompleteFactorValues(SortedMap<String, SortedSet<FactorValue>> allFactorNames, FilterParameters parameters, String name) {
        SortedSet<FactorValue> completeFactorValues = new TreeSet<>();
        for (FactorValue factorValue : allFactorNames.get(name)) {
            // this is necessary because what comes back from RequestPreferences are not "complete" FactorValues
            // they are missing the factor name
            Set<FactorValue> filterFactorValues = parameters.getFilterFactorValues();
            if (filterFactorValues.contains(factorValue)) {
                completeFactorValues.add(factorValue);
            }
        }
        return completeFactorValues;
    }

    private static FilterFactorValues buildFilterFactorValueURL(String queryFactorType, FactorValue firstFactorValue, FactorValue secondFactorValue) {
        return new FilterFactorValues(queryFactorType, firstFactorValue, secondFactorValue);
    }

    private static class FilterFactorValues {

        final String queryFactorType;

        final String filterFactorValuesURL;

        FilterFactorValues(String queryFT, FactorValue firstFV, FactorValue secondFV) {
            queryFactorType = queryFT;
            filterFactorValuesURL = FactorValue.composeFactorValueURLRepresentation(firstFV) + ',' + FactorValue.composeFactorValueURLRepresentation(secondFV);
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getFilterFactorValuesURL() {
            return filterFactorValuesURL;
        }
    }
}