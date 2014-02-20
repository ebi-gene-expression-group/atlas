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

package uk.ac.ebi.atlas.streams.baseline;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.streams.TsvInputStream;

public class BaselineProfilesInputStream extends TsvInputStream<BaselineProfile, BaselineExpression> {

    private BaselineProfileBuilder baselineProfileBuilder;


    public BaselineProfilesInputStream(CSVReader csvReader, String experimentAccession
            , BaselineExpressionsQueueBuilder baselineExpressionsQueueBuilder
            , BaselineProfileBuilder baselineProfileBuilder) {

        super(csvReader, experimentAccession, baselineExpressionsQueueBuilder);
        this.baselineProfileBuilder = baselineProfileBuilder;
    }

    @Override
    protected BaselineProfile createProfile() {
        return baselineProfileBuilder.create();
    }

    @Override
    protected void addExpressionToBuilder(BaselineExpression expression) {
        baselineProfileBuilder.addExpression(expression);
    }

    @Override
    protected void addGeneInfoValueToBuilder(String[] values) {
        baselineProfileBuilder.forGeneIdAndName(values[0], values[1]);
    }

}
