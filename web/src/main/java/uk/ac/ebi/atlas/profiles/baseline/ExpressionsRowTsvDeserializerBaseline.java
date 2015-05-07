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
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionsRowTsvDeserializerBaseline extends ExpressionsRowTsvDeserializer<BaselineExpression> {

    private static final String ZERO_CODE = "-";
    private static final Quartiles ZERO_QUARTILES = Quartiles.create(0.0, 0.0, 0.0, 0.0, 0.0);

    final int expectedNumberOfValues;
    Iterator<FactorGroup> factorGroups;

    public ExpressionsRowTsvDeserializerBaseline(List<FactorGroup> orderedFactorGroups) {
        expectedNumberOfValues = orderedFactorGroups.size();
        factorGroups = Iterables.cycle(orderedFactorGroups).iterator();
    }

    @Override
    public ExpressionsRowTsvDeserializer reload(String... values) {
        checkArgument(values.length == expectedNumberOfValues, String.format("Expected %s values but got [%s]", expectedNumberOfValues, Joiner.on(",").join(values)));
        return super.reload(values);
    }

    @Override
    public BaselineExpression nextExpression(Queue<String> tsvRow) {
        String expressionLevelString = tsvRow.poll();

        if (expressionLevelString == null) {
            return null;
        }

        if (expressionLevelString.equals(ZERO_CODE)) {
            return new BaselineExpression(ZERO_QUARTILES, factorGroups.next());
        }

        if (expressionLevelString.contains(",")) {
            Quartiles quartiles = Quartiles.create(expressionLevelString);

            return new BaselineExpression(quartiles, factorGroups.next());
        }

        return new BaselineExpression(expressionLevelString, factorGroups.next());
    }


}
