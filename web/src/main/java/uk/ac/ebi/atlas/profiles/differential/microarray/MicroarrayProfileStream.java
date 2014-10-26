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

package uk.ac.ebi.atlas.profiles.differential.microarray;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.TsvInputStream;

import java.util.List;


public class MicroarrayProfileStream extends TsvInputStream<MicroarrayProfile, MicroarrayExpression> {

    private MicroarrayProfileReusableBuilder microarrayProfileBuilder;

    public MicroarrayProfileStream(CSVReader csvReader,
                                   String experimentAccession,
                                   ExpressionsRowDeserializerMicroarrayBuilder expressionsRowDeserializerMicroarrayBuilder,
                                   MicroarrayProfileReusableBuilder microarrayProfileBuilder) {

        super(csvReader, experimentAccession, expressionsRowDeserializerMicroarrayBuilder);
        this.microarrayProfileBuilder = microarrayProfileBuilder;
    }

    public List<Contrast> getOrderedContrastsPresentInStream() {
        ExpressionsRowDeserializerMicroarray tsvRowBuffer = (ExpressionsRowDeserializerMicroarray) this.getExpressionsRowDeserializer();
        return tsvRowBuffer.getOrderedContrasts();
    }

    protected MicroarrayProfile createProfile() {
        MicroarrayProfile profile = microarrayProfileBuilder.create();
        return profile.isEmpty() ? null : profile;
    }

    protected void addExpressionToBuilder(MicroarrayExpression expression) {
        microarrayProfileBuilder.addExpression(expression);
    }

    @Override
    protected void addGeneInfoValueToBuilder(String[] values) {
        microarrayProfileBuilder.beginNewInstance(values[0], values[1], values[2]);
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 3, columns.length);
    }
}
