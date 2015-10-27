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

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.atlas.model.Expression;

import java.io.IOException;

public abstract class TsvInputStream<T, K extends Expression> implements ExpressionProfileInputStream<T, K> {

    private static final Logger LOGGER = LogManager.getLogger(TsvInputStream.class);

    private CSVReader csvReader;

    private ExpressionsRowDeserializer<String, K> expressionsRowTsvDeserializer;

    protected TsvInputStream(CSVReader csvReader, String experimentAccession, ExpressionsRowDeserializerBuilder expressionsRowDeserializerBuilder) {
        this.csvReader = csvReader;

        String[] firstCsvLine = readCsvLine();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstCsvLine);
        expressionsRowTsvDeserializer = expressionsRowDeserializerBuilder.forExperiment(experimentAccession).withHeaders(headersWithoutGeneIdColumn).build();
    }

    @Override
    public T readNext() {
        T geneProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            geneProfile = buildObjectFromTsvValues(values);

        } while (geneProfile == null);

        return geneProfile;
    }

    private String[] readCsvLine() {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    protected T buildObjectFromTsvValues(String[] values) {
        addGeneInfoValueToBuilder(values);

        //we need to reload because the first line can only be used to extract the gene ID
        expressionsRowTsvDeserializer.reload(removeGeneIDAndNameColumns(values));

        K expression;

        while ((expression = expressionsRowTsvDeserializer.next()) != null) {
            addExpressionToBuilder(expression);
        }

        return createProfile();
    }

    protected ExpressionsRowDeserializer<String, K> getExpressionsRowTsvDeserializer() {
        return expressionsRowTsvDeserializer;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }

}
