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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class MageTabParserFactory {


    private MicroarrayExperimentDesignMageTabParser microarrayMageTabParser;
    private RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser;
    private TwoColourExperimentDesignMageTabParser twoColourMageTabParser;
    private ProteomicsBaselineExperimentMageTabParser proteomicsBaselineMageTabParser;

    @Inject
    public MageTabParserFactory(@Named("microarrayExperimentDesignMageTabParser") MicroarrayExperimentDesignMageTabParser microarrayMageTabParser,
                                RnaSeqExperimentDesignMageTabParser rnaSeqMageTabParser,
                                ProteomicsBaselineExperimentMageTabParser proteomicsBaselineMageTabParser,
                                @Named("twoColourExperimentDesignMageTabParser") TwoColourExperimentDesignMageTabParser twoColourMageTabParser) {
        this.microarrayMageTabParser = microarrayMageTabParser;
        this.rnaSeqMageTabParser = rnaSeqMageTabParser;
        this.twoColourMageTabParser = twoColourMageTabParser;
        this.proteomicsBaselineMageTabParser = proteomicsBaselineMageTabParser;
    }

    public MageTabParser create(ExperimentType experimentType)  {
        switch(experimentType){
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
                return microarrayMageTabParser;
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
                return twoColourMageTabParser;
            case RNASEQ_MRNA_BASELINE:
            case RNASEQ_MRNA_DIFFERENTIAL:
                return rnaSeqMageTabParser;
            case PROTEOMICS_BASELINE:
                return proteomicsBaselineMageTabParser;
            default:
                throw new IllegalStateException("Unknown experimentType: " + experimentType);
        }
    }
}
