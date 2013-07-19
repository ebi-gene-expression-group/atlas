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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

@Named
public class ExperimentDesignWriterFactory {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String targetFilePathTemplate;

    private MicroarrayExperimentDesignMageTabParser microarrayMageTabParser;
    private RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser;
    private TwoColourExperimentDesignMageTabParser twoColourMageTabParser;

    @Inject
    public ExperimentDesignWriterFactory(@Named("microarrayExperimentDesignMageTabParser")MicroarrayExperimentDesignMageTabParser microarrayMageTabParser,
                                         RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser,
                                         @Named("twoColourExperimentDesignMageTabParser")TwoColourExperimentDesignMageTabParser twoColourMageTabParser) {
        this.microarrayMageTabParser = microarrayMageTabParser;
        this.rnaSeqMageTabParser = rnaSeqMageTabParser;
        this.twoColourMageTabParser = twoColourMageTabParser;
    }

    public ExperimentDesignWriter create(ExperimentType experimentType, String experimentAccession) throws IOException {

        String targetFilePath = MessageFormat.format(targetFilePathTemplate, experimentAccession);

        File experimentDesignFile = new File(targetFilePath);

        FileWriter writer = new FileWriter(experimentDesignFile);

        CSVWriter csvWriter = new CSVWriter(writer, '\t');

        switch(experimentType){
            case MICRORNA:
            case MICROARRAY:
                return new MicroarrayExperimentDesignWriter(microarrayMageTabParser, csvWriter);
            case TWOCOLOUR:
                return new MicroarrayExperimentDesignWriter(twoColourMageTabParser, csvWriter);
            case BASELINE:
            case DIFFERENTIAL:
                return new RnaSeqExperimentDesignWriter(rnaSeqMageTabParser, csvWriter);
            default:
                throw new IllegalStateException("Unknown experimentType: " + experimentType);
        }
    }
}
