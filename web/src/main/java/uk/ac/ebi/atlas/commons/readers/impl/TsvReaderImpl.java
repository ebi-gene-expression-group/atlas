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

package uk.ac.ebi.atlas.commons.readers.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TsvReaderImpl implements TsvReader {

    private static final Logger LOGGER = LogManager.getLogger(TsvReaderImpl.class);

    private InputStreamReader tsvFileInputStreamReader;

    public TsvReaderImpl(InputStreamReader tsvFileInputStreamReader) {
        this.tsvFileInputStreamReader = tsvFileInputStreamReader;
    }

    @Override
    public String[] readLine(long lineIndex) {

        try (CSVReader csvReader = new CSVReader(tsvFileInputStreamReader, '\t')){
            String[] line = null;
            for (int i = 0; i <= lineIndex; i++) {
                line = csvReader.readNext();
            }
            return line;

        } catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot read TSV file", exception);
        }
    }

    @Override
    public List<String[]> readAll() {
        return readAndFilter(new IsNotCommentPredicate());
    }

    List<String[]> readAndFilter(Predicate<String> acceptanceCriteria) {

        try (CSVReader csvReader = new CSVReader(tsvFileInputStreamReader, '\t')){
            List<String[]> rows = new ArrayList<>();
            for (String[] row : csvReader.readAll()) {
                if (acceptanceCriteria.apply(row[0])) {
                    rows.add(row);
                }
            }
            return rows;

        } catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot read TSV file", exception);
        }
    }

    protected static class IsCommentPredicate implements Predicate<String> {

        @Override
        public boolean apply(String rowHeader) {
            return rowHeader.trim().startsWith("#");
        }
    }

    protected static class IsNotCommentPredicate extends IsCommentPredicate {

        @Override
        public boolean apply(String rowHeader) {
            return !super.apply(rowHeader);
        }
    }


}