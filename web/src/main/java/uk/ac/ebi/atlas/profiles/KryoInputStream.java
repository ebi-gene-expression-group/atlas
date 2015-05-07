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
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.Expression;

import java.io.IOException;

public abstract class KryoInputStream<T, K extends Expression> implements ExpressionProfileInputStream<T, K> {

    private static final Logger LOGGER = Logger.getLogger(KryoInputStream.class);

    private KryoReader kryoReader;

    private ExpressionsRowDeserializer<Double[], K> expressionsRowRawDeserializer;

    protected KryoInputStream(KryoReader kryoReader, String experimentAccession, ExpressionsRowDeserializerBuilder expressionsRowDeserializerBuilder) {
        this.kryoReader = kryoReader;

        String[] firstLine = kryoReader.rewindAndreadFirstLine();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstLine);
        expressionsRowRawDeserializer = expressionsRowDeserializerBuilder.forExperiment(experimentAccession).withHeaders(headersWithoutGeneIdColumn).build();
    }

    @Override
    public T readNext() {
        T geneProfile;

        do {
            if (!kryoReader.readLine()) {
                return null;
            }
            geneProfile =  buildObjectFromValues(kryoReader.getGeneId(), kryoReader.getGeneName(), kryoReader.getExpressionLevels());

        } while (geneProfile == null);

        return geneProfile;
    }

    protected T buildObjectFromValues(String geneId, String geneName, Double[][] expressionLevels) {
        addGeneInfoValueToBuilder(new String[]{geneId, geneName});

        //we need to reload because the first line can only be used to extract the gene ID
        expressionsRowRawDeserializer.reload(expressionLevels);

        K expression;

        while ((expression = expressionsRowRawDeserializer.next()) != null) {
            addExpressionToBuilder(expression);
        }

        return createProfile();
    }

    // Used by BaselineExpressionsInputStream
    protected ExpressionsRowDeserializer<Double[], K> getExpressionsRowRawDeserializer() {
        return expressionsRowRawDeserializer;
    }

    @Override
    public void close() throws IOException {
        kryoReader.close();
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }
}
