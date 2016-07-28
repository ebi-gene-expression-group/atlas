package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ExperimentDesignFileWriterFactory {

    private final String targetFilePathTemplate;

    @Inject
    public ExperimentDesignFileWriterFactory(@Value("#{configuration['experiment.experiment-design.path.template']}") String targetFilePathTemplate) {
        this.targetFilePathTemplate = targetFilePathTemplate;
    }

    public ExperimentDesignFileWriter create(String experimentAccession, ExperimentType experimentType) throws IOException {
        return new ExperimentDesignFileWriter(
                new FileTsvWriterBuilder()
                        .forTsvFilePathTemplate(targetFilePathTemplate)
                        .withExperimentAccession(experimentAccession)
                        .withAppend(false)
                        .build(),
                experimentType);
    }
}
