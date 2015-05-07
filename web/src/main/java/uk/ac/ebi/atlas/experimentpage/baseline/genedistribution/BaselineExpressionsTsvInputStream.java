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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerBaselineBuilder;

public class BaselineExpressionsTsvInputStream extends TsvInputStream<BaselineExpressions, BaselineExpression> {

    public BaselineExpressionsTsvInputStream(CSVReader csvReader, String experimentAccession, ExpressionsRowDeserializerBaselineBuilder baselineExpressionsQueueBuilder) {
        super(csvReader, experimentAccession, baselineExpressionsQueueBuilder);
    }

    @Override
    protected BaselineExpressions buildObjectFromTsvValues(String[] values) {

        BaselineExpressions baselineExpressions = new BaselineExpressions();
        //we need to reload because the first line can only be used to extract the gene ID
        getExpressionsRowTsvDeserializer().reload(removeGeneIDAndNameColumns(values));

        BaselineExpression expression;

        while ((expression = getExpressionsRowTsvDeserializer().next()) != null) {

            baselineExpressions.addExpression(expression);
        }

        return baselineExpressions;
    }

    @Override
    public BaselineExpressions createProfile() {
        return null;
    }

    @Override
    public void addExpressionToBuilder(BaselineExpression expression) {
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
    }
}
