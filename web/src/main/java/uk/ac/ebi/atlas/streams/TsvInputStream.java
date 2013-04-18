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

import java.io.IOException;

public abstract class TsvInputStream<T> implements ObjectInputStream<T> {

    private static final Logger logger = Logger.getLogger(TsvInputStream.class);

    protected static final int GENE_ID_COLUMN = 0;

    private CSVReader csvReader;

    private TsvRowBuffer tsvRowBuffer;


    protected TsvInputStream(CSVReader csvReader, String experimentAccession
            , TsvRowBufferBuilder tsvRowBufferBuilder) {

        this.csvReader = csvReader;

        String[] firstCsvLine = readCsvLine();
        String[] headersWithoutGeneIdColumn = (String[])ArrayUtils.remove(firstCsvLine, GENE_ID_COLUMN);
        tsvRowBuffer = tsvRowBufferBuilder.forExperiment(experimentAccession)
                .withHeaders(headersWithoutGeneIdColumn).create();
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

    String[] readCsvLine() {
        try {
            return csvReader.readNext();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    protected abstract T buildObjectFromTsvValues(String[] values);

    protected TsvRowBuffer getTsvRowBuffer() {
        return tsvRowBuffer;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
        logger.debug("<close> close invoked on TsvInputStream");
    }

}
