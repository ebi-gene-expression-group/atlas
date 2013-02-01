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

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

//ToDo: this class needs a builder, for example in order to fully load defaultFilterFactors we first have to load all the metadata (we need factorsByType).
//ToDo: the way it is now we are loading incomplete DefaultFilterFactorValues (they miss their name), forcing client api to callback and fetch the name in an extra step.
public class Experiment {

    private static final Logger logger = Logger.getLogger(Experiment.class);

    private String experimentAccession;
    private String description;
    private String specie;

    private String defaultQueryFactorType;
    private Set<Factor> defaultFilterFactors = new HashSet<>();

    private SortedSetMultimap<String, Factor> factorsByType = TreeMultimap.create();

    private SortedSetMultimap<Factor, Factor> validFactorCombinations = TreeMultimap.create();

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";

    public Experiment(String experimentAccession, String description, String defaultQueryFactorType, Set<Factor> defaultFilterFactors, String specie) {
        this.experimentAccession = experimentAccession;
        this.description = description;
        this.defaultQueryFactorType = defaultQueryFactorType;
        setDefaultFilterFactors(defaultFilterFactors);
        this.specie = specie;
    }

    public String getFactorName(String type, String value) {
        for (Factor factor : factorsByType.get(type)) {
            if (factor.getValue().equals(value)) {
                return factor.getName();
            }
        }
        throw new IllegalStateException("Type: " + type + " and value: " + value + " are not a valid combination in this experiment.");
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<Factor> getDefaultFilterFactors() {
        return defaultFilterFactors;
    }

    public Experiment add(ExperimentRun experimentRun) {
        this.experimentRuns.put(experimentRun.getRunAccession(), experimentRun);
        // index all possible factor values by their byType

        Set<Factor> factors = experimentRun.getFactors();
        for (Factor factor : factors) {
            addToFactorsByType(factor);

            addToFactorCombinations(factors, factor);
        }
        return this;
    }

    private void addToFactorCombinations(Set<Factor> factors, Factor factor) {
        for (Factor value : factors) {
            if (!value.equals(factor)) {
                validFactorCombinations.put(factor, value);
            }
        }
    }

    private void addToFactorsByType(Factor factor) {
        String type = factor.getType();
        factorsByType.put(type, factor);
    }

    public Factor getFactorValue(String experimentRunAccession, String byType) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession, experimentAccession));

        return experimentRun.getFactorValue(byType);
    }

    public Set<Factor> getAllFactors(String experimentRunAccession) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession, experimentAccession));

        return experimentRun.getFactors();
    }

    public Set<String> getExperimentRunAccessions(){
        return experimentRuns.keySet();
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

    public SortedSet<Factor> getFactorValues(@NotNull String byType) {
        return factorsByType.get(byType);
    }

    //ToDo: parameter byType is not needed, it's always the remaining type
    public SortedSet<Factor> getFilteredFactors(Set<Factor> filterByFactors, String byType) {

        SortedSet<Factor> results = new TreeSet<>();

        for (String experimentRunAccession : getExperimentRunAccessions()) {
            ExperimentRun experimentRun = experimentRuns.get(experimentRunAccession);
            if (experimentRun != null) {
                if (CollectionUtils.isEmpty(filterByFactors) ||
                        experimentRun.getFactors().containsAll(filterByFactors)) {
                    Factor factor = experimentRun.getFactorValue(byType);

                    checkNotNull(factor);
                    results.add(factor);
                }
            } else {
                logger.warn("Missing ExperimentRun for accession " + experimentRunAccession);
            }
        }

        return results;
    }

    public SortedSetMultimap<Factor, Factor> getValidFactorCombinations() {
        return validFactorCombinations;
    }

    public void setDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        for (Factor defaultFilterFactor : defaultFilterFactors) {
            //Next two lines should be uncommented when we have a proper builder that forces gene metadata to be loaded before curated metadata (before our tsv files)
            //String factorValueName = getFactorName(defaultFilterFactor.getType(), defaultFilterFactor.getName());
            //defaultFilterFactor.setName(factorValueName);
            this.defaultFilterFactors.add(defaultFilterFactor);
        }
    }
}
