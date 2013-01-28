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

package uk.ac.ebi.atlas.streams;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;

import java.io.IOException;

public abstract class AbstractGeneProfilesInputStream<T extends Iterable<Expression>> implements ObjectInputStream<T> {

    private static final Logger logger = Logger.getLogger(AbstractGeneProfilesInputStream.class);

    private CSVReader csvReader;

    private ExpressionsBuffer expressionsBuffer;


    protected AbstractGeneProfilesInputStream(CSVReader csvReader, String experimentAccession
            , ExpressionsBuffer.Builder expressionsBufferBuilder) {

        this.csvReader = csvReader;

        //ToDo: find a way to move this out of constructor
        String[] dataFileHeaders = readCsvLine();
        expressionsBuffer = expressionsBufferBuilder.forExperiment(experimentAccession)
                .withHeaders(dataFileHeaders).create();
    }


    @Override
    public T readNext() {
        T geneProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            geneProfile = buildGeneProfile(values);

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

    protected abstract T buildGeneProfile(String[] values);

    protected ExpressionsBuffer getExpressionsBuffer() {
        return expressionsBuffer;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
        logger.debug("<close> close invoked on GeneProfilesInputStream");
    }

}
