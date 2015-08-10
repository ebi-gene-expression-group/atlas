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
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Set;
import java.util.SortedSet;

public class ExperimentInfo implements Comparable<ExperimentInfo> {

    private ExperimentType experimentType;
    private String experimentAccession;
    private String experimentDescription;
    private String lastUpdate;
    private int numberOfAssays;
    private int numberOfContrasts;
    private SortedSet<String> species = Sets.newTreeSet();
    private String kingdom;
    private String ensemblDB;
    private SortedSet<String> experimentalFactors = Sets.newTreeSet();
    private SortedSet<String> arrayDesigns = Sets.newTreeSet();
    private SortedSet<String> arrayDesignNames = Sets.newTreeSet();

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getExperimentDescription() {
        return experimentDescription;
    }

    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    public int getNumberOfAssays() {
        return numberOfAssays;
    }

    public void setNumberOfAssays(int numberOfAssays) {
        this.numberOfAssays = numberOfAssays;
    }

    public int getNumberOfContrasts() {
        return numberOfContrasts;
    }

    public void setNumberOfContrasts(int numberOfContrasts) {
        this.numberOfContrasts = numberOfContrasts;
    }

    public SortedSet<String> getSpecies() {
        return species;
    }

    public void setSpecies(Set<String> species) {
        this.species = Sets.newTreeSet(species);
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getEnsemblDB() {
        return ensemblDB;
    }

    public void setEnsemblDB(String ensemblDB) {
        this.ensemblDB = ensemblDB;
    }

    public SortedSet<String> getExperimentalFactors() {
        return experimentalFactors;
    }

    public void setExperimentalFactors(Set<String> experimentalFactors) {
        this.experimentalFactors = Sets.newTreeSet(experimentalFactors);
    }

    public SortedSet<String> getArrayDesigns() {
        return arrayDesigns;
    }

    public void setArrayDesigns(Set<String> arrayDesigns) {
        this.arrayDesigns = Sets.newTreeSet(arrayDesigns);
    }

    public SortedSet<String> getArrayDesignNames() {
        return arrayDesignNames;
    }

    public void setArrayDesignNames(SortedSet<String> arrayDesignNames) {
        this.arrayDesignNames = arrayDesignNames;
    }

    @Override
    public int compareTo(ExperimentInfo o) {
        return this.experimentAccession.compareTo(o.experimentAccession);
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}