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

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Experiment {

    private static final Logger logger = Logger.getLogger(Experiment.class);

    private String experimentAccession;
    private String description;
    private String specie;

    private String defaultQueryFactorType;
    private Set<FactorValue> defaultFilterFactorTypes;

    private Map<String, SortedSet<FactorValue>> factorValuesByType = new HashMap<>();

    private SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations = new TreeMap<>();

    private Set<String> experimentRunAccessions;
    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";

    public Experiment(String experimentAccession, String description, Set<String> experimentRunAccessions, String defaultQueryFactorType, Set<FactorValue> defaultFilterFactorTypes, String specie) {
        this.experimentAccession = experimentAccession;
        this.description = description;
        this.experimentRunAccessions = experimentRunAccessions;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactorTypes = defaultFilterFactorTypes;
        this.specie = specie;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<FactorValue> getDefaultFilterFactorValues() {
        return defaultFilterFactorTypes;
    }

    public Experiment addAll(Collection<ExperimentRun> experimentRuns) {
        for (ExperimentRun experimentRun : experimentRuns) {
            if (experimentRunAccessions.contains(experimentRun.getRunAccession())) {
                this.experimentRuns.put(experimentRun.getRunAccession(), experimentRun);
                // index all possible factor values by their byType

                Set<FactorValue> factorValues1 = experimentRun.getFactorValues();
                for (FactorValue factorValue : factorValues1) {
                    addToFactorValuesByType(factorValue);

                    addToFactorValueCombinations(factorValues1, factorValue);
                }

            }
        }
        return this;
    }

    private void addToFactorValueCombinations(Set<FactorValue> factorValues, FactorValue factorValue) {// track all valid
        // combinations for filterFactorValues
        if (!validFactorValueCombinations.containsKey(factorValue)) {
            validFactorValueCombinations.put(factorValue, new TreeSet<FactorValue>());
        }
        for (FactorValue value : factorValues) {
            if (!value.equals(factorValue)) {
                validFactorValueCombinations.get(factorValue).add(value);
            }
        }
    }

    private void addToFactorValuesByType(FactorValue factorValue) {
        String type = factorValue.getType();

        if (!factorValuesByType.containsKey(type)) {
            factorValuesByType.put(type, new TreeSet<FactorValue>());
        }
        factorValuesByType.get(type).add(factorValue);
    }

    public FactorValue getFactorValue(String experimentRunAccession, String byType) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession, experimentAccession));

        return experimentRun.getFactorValue(byType);
    }

    public Set<FactorValue> getAllFactorValues(String experimentRunAccession) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession, experimentAccession));

        return experimentRun.getFactorValues();
    }

    public Set<String> getExperimentRunAccessions() {
        return experimentRunAccessions;
    }

    public ExperimentRun getExperimentRun(String experimentRunAccession) {
        return experimentRuns.get(experimentRunAccession);
    }

    public int getNumberOfRuns() {
        return experimentRuns.size();
    }

    public String getSpecie() {
        return specie;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getDescription() {
        return description;
    }

    public SortedSet<FactorValue> getFactorValues(@NotNull String byType) {
        return factorValuesByType.get(byType);
    }

    //ToDo: parameter byType is not needed, it's always the remaining type
    public SortedSet<FactorValue> getFilteredFactorValues(Set<FactorValue> filterByFactorValues, String byType) {

        SortedSet<FactorValue> results = new TreeSet<>();

        for (String experimentRunAccession : experimentRunAccessions) {
            ExperimentRun experimentRun = experimentRuns.get(experimentRunAccession);
            if (experimentRun != null) {
                if (CollectionUtils.isEmpty(filterByFactorValues) ||
                        experimentRun.getFactorValues().containsAll(filterByFactorValues)) {
                    FactorValue factorValue = experimentRun.getFactorValue(byType);

                    checkNotNull(factorValue);
                    results.add(factorValue);
                }
            } else {
                logger.warn("Missing ExperimentRun for accession " + experimentRunAccession);
            }
        }

        return results;
    }

    public SortedMap<FactorValue, SortedSet<FactorValue>> getValidFactorValueCombinations() {
        return validFactorValueCombinations;
    }
}
