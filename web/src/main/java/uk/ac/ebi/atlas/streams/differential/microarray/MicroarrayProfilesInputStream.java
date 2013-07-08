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

package uk.ac.ebi.atlas.streams.differential.microarray;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfileBuilder;
import uk.ac.ebi.atlas.streams.TsvInputStream;


public class MicroarrayProfilesInputStream extends TsvInputStream<MicroarrayProfile, DifferentialExpression> {

    private String arrayDesignAccession;

    private MicroarrayProfileBuilder microarrayProfileBuilder;

    public MicroarrayProfilesInputStream(CSVReader csvReader,
                                         String experimentAccession,
                                         String arrayDesignAccession,
                                         MicroarrayExpressionsBufferBuilder expressionsBufferBuilder,
                                         MicroarrayProfileBuilder microarrayProfileBuilder) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.arrayDesignAccession = arrayDesignAccession;
        this.microarrayProfileBuilder = microarrayProfileBuilder;
    }

    protected MicroarrayProfile createProfile() {
        return microarrayProfileBuilder.create();
    }

    protected void addExpressionToBuilder(DifferentialExpression expression) {
        microarrayProfileBuilder.addExpression(expression);
    }

    protected void addGeneColumnValueToBuilder(String designElementName) {
        microarrayProfileBuilder.withDesignElementName(designElementName).withArrayDesignAccession(arrayDesignAccession);
    }
}
