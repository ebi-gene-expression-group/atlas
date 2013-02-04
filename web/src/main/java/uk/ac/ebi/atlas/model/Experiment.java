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

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.SortedSetMultimap;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

//ToDo: this class needs a builder, for example in order to fully load defaultFilterFactors we first have to load all the metadata (we need factorsByType).
//ToDo: the way it is now we are loading incomplete DefaultFilterFactors (they miss their name), forcing client api to callback and fetch the name in an extra step.
public class Experiment {

    private static final Logger logger = Logger.getLogger(Experiment.class);

    private String description;
    private String specie;

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private ExperimentalFactors experimentalFactors;

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found";


    public Experiment(Collection<ExperimentRun> experimentRuns, String description, String defaultQueryFactorType, Set<Factor> defaultFilterFactors, String specie) {
        this.description = description;
        this.specie = specie;
        initExperimentRuns(experimentRuns);
        experimentalFactors = new ExperimentalFactors(extractFactorGroups(), defaultQueryFactorType, defaultFilterFactors);
    }

    protected Collection<FactorGroup> extractFactorGroups() {
        return Collections2.transform(experimentRuns.values(), new Function<ExperimentRun, FactorGroup>() {

            @Override
            public FactorGroup apply(ExperimentRun experimentRun) {
                return experimentRun.getFactorGroup();
            }
        });
    }

    public String getDefaultQueryFactorType() {
        return experimentalFactors.getDefaultQueryFactorType();
    }

    public Set<Factor> getDefaultFilterFactors() {
        return experimentalFactors.getDefaultFilterFactors();
    }

    private final void initExperimentRuns(Collection<ExperimentRun> experimentRuns) {
        for (ExperimentRun experimentRun : experimentRuns) {
            this.experimentRuns.put(experimentRun.getRunAccession(), experimentRun);
        }
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

    public SortedSet<Factor> getFilteredFactors(final Set<Factor> filterFactors, final String type) {
        return experimentalFactors.getFilteredFactors(filterFactors, type);
    }

    //ToDo: data structures like this should not be exposed
    public SortedSetMultimap<Factor, Factor> getValidFactorCombinations() {
        return experimentalFactors.getValidFactorCombinations();
    }

    public SortedSet<String> getAllFactorNames() {
        return experimentalFactors.getAllFactorNames();
    }

    public SortedSet<Factor> getFactorsByName(String name) {
        return experimentalFactors.getFactorsByName(name);
    }

}
