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

import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Experiment {

    private static final Logger logger = Logger.getLogger(Experiment.class);

    private String experimentAccession;
    private String description;
    private String factorType;
    private SortedSet<String> experimentalFactors = new TreeSet<>();

    private Map<String, ExperimentRun> runs = new HashMap<>();

    private String specie;

    private Set<String> experimentRunAccessions;

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";

    public Experiment(String experimentAccession, String description, Set<String> experimentRunAccessions, String factorType) {
        this.experimentAccession = experimentAccession;
        this.description = description;
        this.factorType = factorType;
        this.experimentRunAccessions = experimentRunAccessions;
    }

    public String getDefaultFactorType() {
        return factorType;
    }

    public Experiment addAll(Collection<ExperimentRun> experimentRuns) {
        for (ExperimentRun experimentRun : experimentRuns) {
            if (experimentRunAccessions.contains(experimentRun.getRunAccession())) {
                runs.put(experimentRun.getRunAccession(), experimentRun);
                experimentalFactors.add(experimentRun.getFactorValue(factorType).getValue());
            }
        }
        return this;
    }

    public FactorValue getFactorValue(String columnRun) {
        ExperimentRun experimentRun = getExperimentRun(columnRun);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, columnRun, experimentAccession));

        return experimentRun.getFactorValue(factorType);
    }

    public Set<FactorValue> getAllFactorValues(String columnRun) {
        ExperimentRun experimentRun = getExperimentRun(columnRun);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, columnRun, experimentAccession));

        return experimentRun.getFactorValues();
    }

    public Set<String> getExperimentRunAccessions() {
        return experimentRunAccessions;
    }

    public ExperimentRun getExperimentRun(String runAccession) {
        return runs.get(runAccession);
    }

    public int getNumberOfRuns() {
        return runs.size();
    }

    public String getSpecie() {
        return specie;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public Experiment setSpecie(String specie) {
        this.specie = specie;
        return this;
    }

    public SortedSet<String> getAllExperimentalFactors() {
        return experimentalFactors;
    }

    public String getDescription() {
        return description;
    }

}
