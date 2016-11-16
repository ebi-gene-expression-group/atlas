package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.IOException;

public class ExperimentDesignFileWriterService {

    private final DataFileHub dataFileHub;

    public ExperimentDesignFileWriterService(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    public void writeExperimentDesignFile(String accession, ExperimentType experimentType, ExperimentDesign
            experimentDesign) throws IOException {
        new ExperimentDesignFileWriter(
                dataFileHub.getExperimentFiles(accession).experimentDesignWrite.get(),
                experimentType).write(experimentDesign);
    }
}
