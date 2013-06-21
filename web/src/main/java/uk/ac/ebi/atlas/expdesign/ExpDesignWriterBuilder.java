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

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExpDesignWriterBuilder {

    private RnaSeqExpDesignWriter rnaSeqExpDesignWriter;

    private MicroArrayExpDesignWriter microArrayExpDesignWriter;

    private TwoColourExpDesignWriter twoColourExpDesignWriter;

    private ExperimentType experimentType;

    @Inject
    public ExpDesignWriterBuilder(RnaSeqExpDesignWriter rnaSeqExpDesignWriter,
                                  MicroArrayExpDesignWriter microArrayExpDesignWriter,
                                  TwoColourExpDesignWriter twoColourExpDesignWriter) {
        this.rnaSeqExpDesignWriter = rnaSeqExpDesignWriter;
        this.microArrayExpDesignWriter = microArrayExpDesignWriter;
        this.twoColourExpDesignWriter = twoColourExpDesignWriter;
    }

    public ExpDesignWriterBuilder forExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
        return this;
    }

    public ExpDesignWriter build() {
        checkState(experimentType != null, "Please invoke forExperimentType before build");

        if (experimentType == ExperimentType.BASELINE || experimentType == ExperimentType.DIFFERENTIAL) {
            return rnaSeqExpDesignWriter;
        } else if (experimentType == ExperimentType.TWOCOLOUR) {
            return twoColourExpDesignWriter;
        } else if (experimentType == ExperimentType.MICROARRAY) {
            return microArrayExpDesignWriter;
        }

        throw new IllegalStateException("ExperimentType does not have a matching ExpDesignWriter.");
    }

}