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

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.streams.TsvRowBuffer;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class BaselineExpressionsBuffer extends TsvRowBuffer<BaselineExpression> {

    private Iterator<FactorGroup> expectedFactorGroups;

    protected BaselineExpressionsBuffer(List<FactorGroup> orderedFactorGroups) {
        expectedFactorGroups = Iterables.cycle(orderedFactorGroups).iterator();
    }


    @Override
    public BaselineExpression pollExpression(Queue<String> tsvRow) {
        String expressionLevelString = tsvRow.poll();

        if (expressionLevelString == null) {
            return null;
        }
        double expressionLevel = Double.parseDouble(expressionLevelString);

        return new BaselineExpression(expressionLevel, expectedFactorGroups.next());
    }


}
