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


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Experiment {

    private String experimentAccession;
    private String description;

    private Map<String, ExperimentRun> runs = new HashMap<>();

    private String specie;

    public Experiment(String experimentAccession, String description) {
        this.experimentAccession = experimentAccession;
        this.description = description;
    }

    public Experiment addAll(Collection<ExperimentRun> experimentRuns){
        for (ExperimentRun experimentRun: experimentRuns){
            runs.put(experimentRun.getRunAccession(), experimentRun);
        }
        return this;
    }

    public ExperimentRun getExperimentRun(String runAccession){

        return runs.get(runAccession);

    }

    public int getNumberOfRuns(){
        return runs.size();
    }

    public String getSpecie(){
        return specie;
    }

    public String getExperimentAccession(){
        return experimentAccession;
    }

    public Experiment setSpecie(String specie) {
        this.specie = specie;
        return this;
    }

    public String getDescription() {
        return description;
    }

}
