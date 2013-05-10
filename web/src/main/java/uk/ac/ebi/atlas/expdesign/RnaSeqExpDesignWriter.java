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

import com.google.common.collect.Lists;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RnaSeqExpDesignWriter {

    MageTabLimpopoExpDesignParser mageTabLimpopoExpDesignParser;


    public RnaSeqExpDesignWriter(MageTabLimpopoExpDesignParser mageTabLimpopoExpDesignParser) {
        this.mageTabLimpopoExpDesignParser = mageTabLimpopoExpDesignParser;
    }

    public void forExperimentAccession(String experimentAccession) throws IOException, ParseException {
        mageTabLimpopoExpDesignParser.forExperimentAccession(experimentAccession).build();

        List<String> characteristics = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractCharacteristics());
        Collections.sort(characteristics);

        List<String> factors = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractFactors());
        Collections.sort(factors);

        String headerString = composeHeader(characteristics, factors);

    }


    String composeHeader(List<String> characteristics, List<String> factors) {
        StringBuilder sb = new StringBuilder("Run\t");
        for (String characteristic : characteristics) {
            sb.append("Sample Characteristics[");
            sb.append(characteristic);
            sb.append("]\t");
        }
        for (String factor : factors) {
            sb.append("Factor Values[");
            sb.append(factor);
            sb.append("]\t");
        }
        return sb.toString();
    }

}