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

import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Experiment {

    private static final Logger logger = Logger.getLogger(Experiment.class);

    private String experimentAccession;
    private String description;
    private String specie;

    private String defaultFactorType;
    private SortedSet<String> defaultFactorValues = new TreeSet<>();

    private Set<String> experimentRunAccessions;
    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";

    public Experiment(String experimentAccession, String description, Set<String> experimentRunAccessions, String defaultFactorType) {
        this.experimentAccession = experimentAccession;
        this.description = description;
        this.experimentRunAccessions = experimentRunAccessions;
        this.defaultFactorType = defaultFactorType;
    }

    public String getDefaultFactorType() {
        return defaultFactorType;
    }

    public Experiment addAll(Collection<ExperimentRun> experimentRuns) {
        for (ExperimentRun experimentRun : experimentRuns) {
            if (experimentRunAccessions.contains(experimentRun.getRunAccession())) {
                this.experimentRuns.put(experimentRun.getRunAccession(), experimentRun);
                defaultFactorValues.add(experimentRun.getFactorValue(defaultFactorType).getValue());
            }
        }
        return this;
    }

    //ToDo: redundant, will be removed when we remove organism parts

    public FactorValue getFactorValue(String experimentRunAccession) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession, experimentAccession));

        return experimentRun.getFactorValue(defaultFactorType);
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

    public Experiment setSpecie(String specie) {
        this.specie = specie;
        return this;
    }

    public SortedSet<String> getDefaultFactorValues() {
        return defaultFactorValues;
    }

    public SortedSet<String> getFilteredDefaultFactorValues(Set<FactorValue> filterByFactorValues) {
        SortedSet<String> results = new TreeSet<>();

        for (String experimentRunAccession : experimentRunAccessions) {
            ExperimentRun experimentRun = experimentRuns.get(experimentRunAccession);
            if (experimentRun != null) {
                if (CollectionUtils.isEmpty(filterByFactorValues) ||
                        experimentRun.getFactorValues().containsAll(filterByFactorValues)) {
                    FactorValue factorValue = experimentRun.getFactorValue(defaultFactorType);
                    checkNotNull(factorValue);
                    results.add(factorValue.getValue());
                }
            } else {
                logger.warn("Missing ExperimentRun for accession " + experimentRunAccession);
            }
        }

        return results;
    }


}
