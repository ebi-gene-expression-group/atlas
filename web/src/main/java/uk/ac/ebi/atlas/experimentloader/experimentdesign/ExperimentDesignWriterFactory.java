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

import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExperimentDesignWriterFactory {

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


    public ExperimentDesignWriter create(ExperimentType experimentType) {
        switch(experimentType){
            case MICRORNA:
            case MICROARRAY:
                return new MicroarrayExperimentDesignWriter(microarrayMageTabParser);
            case TWOCOLOUR:
                return new MicroarrayExperimentDesignWriter(twoColourMageTabParser);
            case BASELINE:
            case DIFFERENTIAL:
                return new RnaSeqExperimentDesignWriter(rnaSeqMageTabParser);
            default:
                throw new IllegalStateException("Unknown experimentType: " + experimentType);
        }
    }
}
