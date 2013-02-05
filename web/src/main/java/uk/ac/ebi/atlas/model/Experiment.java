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

package uk.ac.ebi.atlas.model;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Experiment {

    private String description;
    private String specie;

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private ExperimentalFactors experimentalFactors;

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found";


    Experiment(ExperimentalFactors experimentalFactors, Collection<ExperimentRun> experimentRuns, String description, String specie) {
        this.description = description;
        this.specie = specie;
        this.experimentalFactors = experimentalFactors;
        for (ExperimentRun experimentRun : experimentRuns) {
            this.experimentRuns.put(experimentRun.getRunAccession(), experimentRun);
        }
    }

    public String getDefaultQueryFactorType() {
        return experimentalFactors.getDefaultQueryFactorType();
    }

    public Set<Factor> getDefaultFilterFactors() {
        return experimentalFactors.getDefaultFilterFactors();
    }

    public FactorGroup getFactorGroup(String experimentRunAccession) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession));

        return experimentRun.getFactorGroup();
    }

    public String getFactorName(String type) {
        return experimentalFactors.getFactorName(type);
    }

    public Set<String> getExperimentRunAccessions() {
        return experimentRuns.keySet();
    }

    private ExperimentRun getExperimentRun(String experimentRunAccession) {
        return experimentRuns.get(experimentRunAccession);
    }

    public String getSpecie() {
        return specie;
    }

    public String getDescription() {
        return description;
    }

    public SortedSet<Factor> getFactorsByType(@NotNull String type) {
        return experimentalFactors.getFactorsByType(type);
    }

    public SortedSet<Factor> getFilteredFactors(Set<Factor> filterFactors, String type) {
        return experimentalFactors.getFilteredFactors(filterFactors, type);
    }

    public SortedSet<String> getAllFactorNames() {
        return experimentalFactors.getAllFactorNames();
    }

    public SortedSet<String> getRemainingFactorNamesForNames(String... names) {
        return experimentalFactors.getRemainingFactorNamesForNames(names);
    }

    public SortedSet<Factor> getFactorsByName(@NotNull String name) {
        return experimentalFactors.getFactorsByName(name);
    }

    public SortedSet<Factor> getValidCombinationsForFactorAndName(Factor factor, String name) {
        return experimentalFactors.getFactorsWithGivenNameCooccurringWithGivenFactor(factor, name);
    }

}
