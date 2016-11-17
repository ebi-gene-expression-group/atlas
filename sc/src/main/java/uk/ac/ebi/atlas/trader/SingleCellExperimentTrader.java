package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class SingleCellExperimentTrader extends ExperimentTrader{

    @Inject
    public SingleCellExperimentTrader(ExperimentDAO experimentDAO) {
        super(experimentDAO);
    }

    public Experiment getPublicExperiment(String experimentAccession){
        return null;
    }

    public Experiment getExperiment(String experimentAccession, String accessKey){
        return getPublicExperiment(experimentAccession);
    }

    public void removeExperimentFromCache(String experimentAccession){

    }

    public void removeAllExperimentsFromCache(){

    }

    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType){
        if(experimentType.isSingleCell()){
            return getPublicExperiment(experimentAccession);
        } else {
            return null;
        }
    }

    public Set<String> getPublicExperimentAccessions(ExperimentType experimentType) {
        return experimentDAO.findPublicExperimentAccessions(experimentType);
    }
}
