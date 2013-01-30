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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@Named("filterByMenuBuilder")
@Scope("prototype")
public class FilterByMenuBuilder {

    // does the serialisation to JSON
    private final Gson gson = new Gson();

    private final FactorValueUtils factorValueUtils;

    private SortedMap<String, SortedSet<FactorValue>> allFactorNames;

    private SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations;

    @Inject
    public FilterByMenuBuilder(FactorValueUtils factorValueUtils) {
        this.factorValueUtils = factorValueUtils;
    }

    public SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> build(Experiment experiment) {

        validFactorValueCombinations = experiment.getValidFactorValueCombinations();

        allFactorNames = factorValueUtils.indexFactorValuesByName(validFactorValueCombinations.keySet());

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
        for (FactorValue firstFactorValue : allFactorNames.get(firstFactorName)) {

            secondMenuLevel.put(firstFactorValue.getValue(), buildThirdMenuLevel(firstFactorValue));

        }

        return secondMenuLevel;
    }

    private SortedMap<String, SortedMap<String, String>> buildThirdMenuLevel(FactorValue firstFactorValue) {

        // factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, String>> thirdMenuLevel = new TreeMap<>();

        // index second level factor names
        SortedMap<String, SortedSet<FactorValue>> secondFactorNames =
                factorValueUtils.indexFactorValuesByName(validFactorValueCombinations.get(firstFactorValue));

        // third level: factor value choices per factor name, restricted by previous
        for (String secondFactorName : secondFactorNames.keySet()) {

            // get remainder of factor names
            SortedSet<String> remainingFactorNames = new TreeSet<>(allFactorNames.keySet());
            remainingFactorNames.remove(firstFactorValue.getName());
            remainingFactorNames.remove(secondFactorName);

            // TODO: what in case there are more than 3 possible factor types?
            // arbitrarily taking first of remaining factor names as query factor type
            String firstRemainingFactorName = remainingFactorNames.first();
            SortedSet<FactorValue> factorValuesForFirstRemainingFactorName = allFactorNames.get(firstRemainingFactorName);
            FactorValue firstArbitraryFactorValue = factorValuesForFirstRemainingFactorName.first();
            String queryFactorType = firstArbitraryFactorValue.getType();

            // third level: factor name
            SortedMap<String, String> thirdLevel = buildForthMenuLevel(queryFactorType, firstFactorValue, secondFactorNames.get(secondFactorName));
            if (!thirdMenuLevel.containsKey(secondFactorName)) {
                thirdMenuLevel.put(secondFactorName, thirdLevel);
            }
            SortedMap<String, String> stringStringSortedMap = thirdMenuLevel.get(secondFactorName);
            stringStringSortedMap.putAll(thirdLevel);
        }

        return thirdMenuLevel;
    }

    private SortedMap<String, String> buildForthMenuLevel(String queryFactorType, FactorValue firstFactorValue, SortedSet<FactorValue> secondFactorValues) {

        // factor value 2 > link
        SortedMap<String, String> forthMenuLevel = new TreeMap<>();

        // forth level: factor value choices for second factor name
        for (FactorValue secondFactorValue : secondFactorValues) {

            String link = gson.toJson(buildFilterFactorValueURL(queryFactorType, firstFactorValue, secondFactorValue));
            forthMenuLevel.put(secondFactorValue.getValue(), link);

        }

        return forthMenuLevel;
    }

    private FilterFactorValues buildFilterFactorValueURL(String queryFactorType, FactorValue firstFactorValue, FactorValue secondFactorValue) {
        return new FilterFactorValues(queryFactorType, firstFactorValue, secondFactorValue);
    }

    private final class FilterFactorValues {

        private final String queryFactorType;

        private final String filterFactorValuesURL;

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