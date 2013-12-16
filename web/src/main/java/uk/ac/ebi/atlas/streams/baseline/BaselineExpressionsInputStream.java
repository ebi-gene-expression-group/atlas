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
import uk.ac.ebi.atlas.model.baseline.BaselineExpressions;
import uk.ac.ebi.atlas.streams.TsvInputStream;

public class BaselineExpressionsInputStream extends TsvInputStream<BaselineExpressions, BaselineExpression> {

    public BaselineExpressionsInputStream(CSVReader csvReader, String experimentAccession, BaselineExpressionsQueueBuilder expressionsBufferBuilder) {
        super(csvReader, experimentAccession, expressionsBufferBuilder);
    }

    @Override
    protected BaselineExpressions buildObjectFromTsvValues(String[] values) {

        BaselineExpressions baselineExpressions = new BaselineExpressions();
        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowQueue().reload(removeGeneIDAndNameColumns(values));

        BaselineExpression expression;

        while ((expression = getTsvRowQueue().poll()) != null) {

            baselineExpressions.addExpression(expression);
        }

        return baselineExpressions;
    }

    @Override
    protected BaselineExpressions createProfile() {
        return null;
    }

    @Override
    protected void addExpressionToBuilder(BaselineExpression expression) {

    }

    @Override
    protected void addGeneInfoValueToBuilder(String[] values) {

    }
}
