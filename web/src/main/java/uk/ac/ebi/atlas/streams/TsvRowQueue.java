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

package uk.ac.ebi.atlas.streams;

import uk.ac.ebi.atlas.model.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

/*
 * Converts a single row of expressions, eg:
 *
 * Gene ID, Gene Name, g1, g2, g3, g4, g5
 * mus1, musName, 1, 2, 3, 4, 5
 *
 * into Expressions objects, eg:
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 *
 * Implemented as a queue, from which the next expression in the row can be pulled off
 * (ie: polled)
 */
public abstract class TsvRowQueue<T extends Expression> {


    private Queue<String> tsvRow = new LinkedList<>();


    public TsvRowQueue reload(String... values) {
        checkState(this.tsvRow.isEmpty(), "Reload must be invoked only when readNext returns null");

        Collections.addAll(this.tsvRow, values);

        return this;
    }

    public T poll(){
        return pollExpression(tsvRow);
    }

    protected abstract T pollExpression(Queue<String> expressionLevelsBuffer);

}
