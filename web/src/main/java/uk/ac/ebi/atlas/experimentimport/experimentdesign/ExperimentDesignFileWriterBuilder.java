package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class ExperimentDesignFileWriterBuilder {

    private final FileTsvWriterBuilder fileTsvWriterBuilder;
    private ExperimentType experimentType;

    @Inject
    public ExperimentDesignFileWriterBuilder(@Value("#{configuration['experiment.experiment-design.path.template']}") String targetFilePathTemplate) {
        this.fileTsvWriterBuilder = new FileTsvWriterBuilder();
        this.fileTsvWriterBuilder.forTsvFilePathTemplate(targetFilePathTemplate);
    }

    public ExperimentDesignFileWriterBuilder withExperimentAccession(String experimentAccession) {
        this.fileTsvWriterBuilder.withExperimentAccession(experimentAccession);
        return this;
    }

    public ExperimentDesignFileWriterBuilder withExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDesignFileWriter build() throws IOException {
        return new ExperimentDesignFileWriter(fileTsvWriterBuilder.build(), experimentType);
    }
}
