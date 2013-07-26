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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Named
public class ExperimentDesignWriterBuilder {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String targetFilePathTemplate;

    private MicroarrayExperimentDesignMageTabParser microarrayMageTabParser;
    private RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser;
    private TwoColourExperimentDesignMageTabParser twoColourMageTabParser;

    private String experimentAccession;
    private ExperimentType experimentType;

    @Inject
    public ExperimentDesignWriterBuilder(@Named("microarrayExperimentDesignMageTabParser") MicroarrayExperimentDesignMageTabParser microarrayMageTabParser,
                                         RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser,
                                         @Named("twoColourExperimentDesignMageTabParser") TwoColourExperimentDesignMageTabParser twoColourMageTabParser) {
        this.microarrayMageTabParser = microarrayMageTabParser;
        this.rnaSeqMageTabParser = rnaSeqMageTabParser;
        this.twoColourMageTabParser = twoColourMageTabParser;
    }

    public ExperimentDesignWriterBuilder forExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public ExperimentDesignWriterBuilder withExperimentType(ExperimentType experimentType){
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDesignWriter build() throws IOException {

        String targetFilePath = MessageFormat.format(targetFilePathTemplate, experimentAccession);

        Path experimentDesignPath = Paths.get(targetFilePath);

        BufferedWriter writer = Files.newBufferedWriter(experimentDesignPath, StandardCharsets.UTF_8);

        CSVWriter csvWriter = new CSVWriter(writer, '\t');

        //ToDo (B) maybe it is silly that we need to inject different type of parsers.
        //ToDo (B) maybe we should have one only MageTabParser class and the MageTabParser should use different specialized ExperimentDesignBuilder to build the ExperimentDesign
        switch(experimentType){
            case MICRORNA:
            case MICROARRAY:
                return new ExperimentDesignWriter(csvWriter, microarrayMageTabParser, experimentType);
            case TWOCOLOUR:
                return new ExperimentDesignWriter(csvWriter, twoColourMageTabParser, experimentType);
            case BASELINE:
            case DIFFERENTIAL:
                return new ExperimentDesignWriter(csvWriter, rnaSeqMageTabParser, experimentType);
            default:
                throw new IllegalStateException("Unknown experimentType: " + experimentType);
        }
    }
}
