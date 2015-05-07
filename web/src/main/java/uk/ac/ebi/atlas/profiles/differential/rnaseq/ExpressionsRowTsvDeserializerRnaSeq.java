/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

public class ExpressionsRowTsvDeserializerRnaSeq extends ExpressionsRowTsvDeserializer<DifferentialExpression> {

    private Iterator<Contrast> expectedContrasts;

    ExpressionsRowTsvDeserializerRnaSeq(List<Contrast> orderedContrasts) {
        this.expectedContrasts = Iterables.cycle(orderedContrasts).iterator();
    }

    public DifferentialExpression nextExpression(Queue<String> tsvRow) {
        String pValueString = tsvRow.poll();
        if (pValueString == null) {
            return null;
        }
        String foldChangeString = tsvRow.poll();
        checkState(foldChangeString != null, "missing fold change column in the analytics file");
        if ("NA".equalsIgnoreCase(pValueString) || "NA".equalsIgnoreCase(foldChangeString)) {
            expectedContrasts.next();
            return nextExpression(tsvRow);
        }
        double pValue = parseDouble(pValueString);
        double foldChange = parseDouble(foldChangeString);

        Contrast contrast = expectedContrasts.next();
        return new DifferentialExpression(pValue, foldChange, contrast);
    }

    double parseDouble(String value) {
        if (value.equalsIgnoreCase("inf")) {
            return Double.POSITIVE_INFINITY;
        }
        if (value.equalsIgnoreCase("-inf")) {
            return Double.NEGATIVE_INFINITY;
        }
        return Double.parseDouble(value);
    }


}
