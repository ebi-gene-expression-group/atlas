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

package uk.ac.ebi.atlas.profiles;

import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;

import java.io.IOException;

public abstract class KryoInputStream<T, K extends Expression> implements ExpressionProfileInputStream<T, K> {

    private BaselineExpressionsKryoReader baselineExpressionsKryoReader;

    private ExpressionsRowDeserializer<BaselineExpression, K> expressionsRowRawDeserializer;

    protected KryoInputStream(BaselineExpressionsKryoReader baselineExpressionsKryoReader, String experimentAccession, ExpressionsRowDeserializerBuilder expressionsRowDeserializerBuilder) {
        this.baselineExpressionsKryoReader = baselineExpressionsKryoReader;

        String[] firstLine = baselineExpressionsKryoReader.rewindAndReadAssays();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstLine);
        expressionsRowRawDeserializer = expressionsRowDeserializerBuilder.forExperiment(experimentAccession).withHeaders(headersWithoutGeneIdColumn).build();
    }

    @Override
    public T readNext() {
        T geneProfile;

        do {
            if (!baselineExpressionsKryoReader.readLine()) {
                return null;
            }
            geneProfile =  buildObjectFromValues(baselineExpressionsKryoReader.getGeneId(), baselineExpressionsKryoReader.getGeneName(), baselineExpressionsKryoReader.getExpressions());

        } while (geneProfile == null);

        return geneProfile;
    }

    protected T buildObjectFromValues(String geneId, String geneName, BaselineExpression[] expressions) {
        addGeneInfoValueToBuilder(new String[]{geneId, geneName});

        //we need to reload because the first line can only be used to extract the gene ID
        expressionsRowRawDeserializer.reload(expressions);

        K expression;

        while ((expression = expressionsRowRawDeserializer.next()) != null) {
             addExpressionToBuilder(expression);
        }

        return createProfile();
    }

    // Used by BaselineExpressionsInputStream
    protected ExpressionsRowDeserializer<BaselineExpression, K> getExpressionsRowRawDeserializer() {
         return expressionsRowRawDeserializer;
    }

    @Override
    public void close() throws IOException {
        baselineExpressionsKryoReader.close();
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }
}
