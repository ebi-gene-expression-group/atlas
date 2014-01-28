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


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;

import java.io.IOException;

public abstract class TsvInputStream<T, K extends Expression> implements ObjectInputStream<T> {

    private static final Logger LOGGER = Logger.getLogger(TsvInputStream.class);

    private CSVReader csvReader;

    private TsvRowQueue<K> tsvRowQueue;

    protected TsvInputStream(CSVReader csvReader, String experimentAccession
            , TsvRowQueueBuilder tsvRowQueueBuilder) {

        this.csvReader = csvReader;

        String[] firstCsvLine = readCsvLine();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstCsvLine);
        tsvRowQueue = tsvRowQueueBuilder.forExperiment(experimentAccession)
                .withHeaders(headersWithoutGeneIdColumn).build();
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
        getTsvRowQueue().reload(removeGeneIDAndNameColumns(values));

        K expression;

        while ((expression = getTsvRowQueue().poll()) != null) {

            addExpressionToBuilder(expression);

        }

        return createProfile();


    }

    protected abstract T createProfile();

    protected abstract void addExpressionToBuilder(K expression);

//    //ToDo (B): something wrong with the name of this method, it is used to add design element, not gene name, see how the implementation is used...
//    protected abstract void addGeneColumnValueToBuilder(String geneName);

    protected abstract void addGeneInfoValueToBuilder(String[] values);

    protected TsvRowQueue<K> getTsvRowQueue() {
        return tsvRowQueue;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
        LOGGER.debug("<close> close invoked on TsvInputStream");
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }
}
