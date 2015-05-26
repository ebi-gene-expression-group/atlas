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

package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.Quartiles;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.ExpressionsRowRawDeserializer;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionsRowRawDeserializerBaseline extends ExpressionsRowRawDeserializer<BaselineExpression> {

    final int expectedNumberOfValues;
    Iterator<FactorGroup> factorGroups;

    public ExpressionsRowRawDeserializerBaseline(List<FactorGroup> orderedFactorGroups) {
        expectedNumberOfValues = orderedFactorGroups.size();
        factorGroups = Iterables.cycle(orderedFactorGroups).iterator();
    }

    @Override
    public ExpressionsRowRawDeserializer<BaselineExpression> reload(BaselineExpression... values) {
        if (values.length != expectedNumberOfValues) {
            throw new IllegalArgumentException(String.format("Expected %s values but got [%s]", expectedNumberOfValues, Joiner.on(",").join(values)));
        }
        return super.reload(values);
    }

    @Override
    public BaselineExpression nextExpression(Queue<BaselineExpression> rawValuesRow) {
//        Double[] expressionLevelValues = rawValuesRow.poll();
//        if (expressionLevelValues == null) {
//            return null;
//        } else if (expressionLevelValues.length == 0) {
//            return new BaselineExpression("NT", factorGroups.next());
//        }else if (expressionLevelValues.length == 1) {
//            return new BaselineExpression(expressionLevelValues[0], factorGroups.next());
//        } else {
//            return new BaselineExpression(Quartiles.create(expressionLevelValues), factorGroups.next());
//        }

        return rawValuesRow.poll();
    }

}
