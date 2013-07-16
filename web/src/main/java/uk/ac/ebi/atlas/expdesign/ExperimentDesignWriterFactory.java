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

package uk.ac.ebi.atlas.expdesign;

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

    public ExperimentDesignWriter getMicroarrayWriter() {
        return new MicroarrayExperimentDesignWriter(microarrayMageTabParser);
    }

    public ExperimentDesignWriter getRnaseqWriter() {
        return new RnaSeqExperimentDesignWriter(rnaSeqMageTabParser);
    }

    public ExperimentDesignWriter getTwoColourWriter() {
        return new MicroarrayExperimentDesignWriter(twoColourMageTabParser);
    }


}
