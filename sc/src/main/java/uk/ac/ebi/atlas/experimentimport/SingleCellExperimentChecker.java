package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Named;

@Named
public class SingleCellExperimentChecker implements ExperimentChecker{
    @Override
    public void checkAllFiles(String experimentAccession, ExperimentType experimentType) {
        if(!experimentType.isSingleCell()){
            throw new RuntimeException("Experiment type not supported: "+experimentType);
        }
    }
}
