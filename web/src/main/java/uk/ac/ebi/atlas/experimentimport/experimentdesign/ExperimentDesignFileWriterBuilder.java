package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Named
public class ExperimentDesignFileWriterBuilder {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String targetFilePathTemplate;

    private String experimentAccession;
    private ExperimentType experimentType;

    public ExperimentDesignFileWriterBuilder forExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public ExperimentDesignFileWriterBuilder withExperimentType(ExperimentType experimentType){
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDesignFileWriter build() throws IOException {
        String targetFilePath = MessageFormat.format(targetFilePathTemplate, experimentAccession);
        Path experimentDesignPath = Paths.get(targetFilePath);

        BufferedWriter writer = Files.newBufferedWriter(experimentDesignPath, StandardCharsets.UTF_8);

        CSVWriter csvWriter = new CSVWriter(writer, '\t');

        return new ExperimentDesignFileWriter(csvWriter, experimentType);
    }
}
