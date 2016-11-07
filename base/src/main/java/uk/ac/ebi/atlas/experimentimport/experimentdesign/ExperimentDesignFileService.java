package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ExperimentDesignFileService {

    private final String targetFilePathTemplate;

    @Inject
    public ExperimentDesignFileService(@Value("#{configuration['experiment.experiment-design.path.template']}") String targetFilePathTemplate) {
        this.targetFilePathTemplate = targetFilePathTemplate;
    }

    ExperimentDesignFileWriter create(String experimentAccession, ExperimentType experimentType) throws IOException {
        return new ExperimentDesignFileWriter(
                new FileTsvWriterBuilder()
                        .forTsvFilePathTemplate(targetFilePathTemplate)
                        .withExperimentAccession(experimentAccession)
                        .withAppend(false)
                        .build(),
                experimentType);
    }

    public void write(String experimentAccession, ExperimentType experimentType, ExperimentDesign experimentDesign)
    throws IOException {
        create(experimentAccession,experimentType).write(experimentDesign);
    }
}
