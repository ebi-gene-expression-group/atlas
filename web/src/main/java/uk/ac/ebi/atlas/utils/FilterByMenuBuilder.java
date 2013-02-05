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

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.Set;
import java.util.SortedSet;

@Named("filterByMenuBuilder")
@Scope("prototype")
public class FilterByMenuBuilder {

    public static final String FACTOR_SEPARATOR = ":";

    public FilterByMenuBuilder() {
    }


    public class MenuLevel implements Comparable<MenuLevel> {

        String value;

        SortedSet<MenuLevel> children = Sets.newTreeSet();

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void addChild(MenuLevel child) {
            children.add(child);
        }

        public void addChildren(SortedSet<MenuLevel> children) {
            this.children.addAll(children);
        }

        public Set<MenuLevel> getChildren() {
            return children;
        }

        public void iterate(SortedSet<MenuLevel> visited) {
            for (MenuLevel child : children) {
                if (visited.contains(child)) continue;
                visited.add(child);
                child.iterate(visited);
            }
        }

        @Override
        public int compareTo(MenuLevel o) {
            return value.compareTo(o.value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public SortedSet<MenuLevel> build(Experiment experiment) {

        SortedSet<MenuLevel> firstMenuLevel = Sets.newTreeSet();

        // first level: factor name
        for (String firstFactorName : experiment.getAllFactorNames()) {

            MenuLevel level = new MenuLevel();
            level.setValue(firstFactorName);
            level.addChildren(buildSecondMenuLevel(experiment, firstFactorName));
            firstMenuLevel.add(level);
        }

        return firstMenuLevel;
    }

    private SortedSet<MenuLevel> buildSecondMenuLevel(Experiment experiment, String firstFactorName) {

        SortedSet<MenuLevel> secondMenuLevel = Sets.newTreeSet();

        // second level: factor value choices per factor name, all are valid
        for (Factor firstFactor : experiment.getFactorsByName(firstFactorName)) {

            MenuLevel level = new MenuLevel();
            level.setValue(firstFactor.getValue());
            level.addChildren(buildThirdMenuLevel(experiment, firstFactor));
            secondMenuLevel.add(level);
        }

        return secondMenuLevel;
    }

    private SortedSet<MenuLevel> buildThirdMenuLevel(Experiment experiment, Factor firstFactor) {

        SortedSet<MenuLevel> thirdMenuLevel = Sets.newTreeSet();

        // what names are currently remaining
        String firstFactorName = firstFactor.getName();
        SortedSet<String> currentFactorNames = experiment.getRemainingFactorNamesForNames(firstFactorName);

        // third level: factor name choices, restricted by previous
        for (String secondFactorName : currentFactorNames) {

            SortedSet<Factor> remainingFactors = experiment.getValidCombinationsForFactorAndName(firstFactor, secondFactorName);

            MenuLevel level = new MenuLevel();
            level.setValue(secondFactorName);
            level.addChildren(buildForthMenuLevel(experiment, firstFactor, remainingFactors));
            thirdMenuLevel.add(level);
        }

        return thirdMenuLevel;
    }

    private SortedSet<MenuLevel> buildForthMenuLevel(Experiment experiment, Factor firstFactor, SortedSet<Factor> secondFactors) {

        SortedSet<MenuLevel> forthMenuLevel = Sets.newTreeSet();

        // forth level: factor value choices for second factor name
        for (Factor secondFactor : secondFactors) {

            MenuLevel level = new MenuLevel();
            level.setValue(secondFactor.getValue());
            level.addChild(buildLastMenuLevel(experiment, firstFactor, secondFactor));
            forthMenuLevel.add(level);
        }

        return forthMenuLevel;
    }

    private MenuLevel buildLastMenuLevel(Experiment experiment, Factor firstFactor, Factor secondFactor) {

        // get remainder of factors and names
        SortedSet<String> remainingFactorNames = experiment.getRemainingFactorNamesForNames(firstFactor.getName(), secondFactor.getName());

        // TODO: what in case there are more than 3 possible factor types?
        // arbitrarily taking first of remaining factor names as query factor type
        String firstRemainingFactorName = remainingFactorNames.first();
        SortedSet<Factor> factorsForFirstRemainingFactorName = experiment.getFactorsByName(firstRemainingFactorName);
        Factor firstArbitraryFactor = factorsForFirstRemainingFactorName.first();
        String queryFactorType = firstArbitraryFactor.getType();

        String link = getJsonUrl(queryFactorType, firstFactor, secondFactor);
        MenuLevel level = new MenuLevel();
        level.setValue(link);

        return level;
    }

    protected String getJsonUrl(String queryFactorType, Factor firstFactor, Factor secondFactor) {
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

    protected String serialize(Factor factor) {
        return factor.getType() + FACTOR_SEPARATOR + factor.getValue();
    }
}