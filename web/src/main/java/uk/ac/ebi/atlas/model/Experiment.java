package uk.ac.ebi.atlas.model;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Experiment {

    private String experimentAccession;

    private Map<String, ExperimentRun> runs = new HashMap<>();

    private String specie;

    public Experiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
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
}
