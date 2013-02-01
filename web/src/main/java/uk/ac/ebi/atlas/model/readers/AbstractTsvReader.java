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

package uk.ac.ebi.atlas.model.readers;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;

public abstract class AbstractTsvReader {

    private static final Logger LOGGER = Logger.getLogger(AbstractTsvReader.class);

    protected AbstractTsvReader() {
    }

    protected abstract String getPathTemplate();

    public Collection<String[]> readAll(String experimentAccession) {
        return readAndFilter(experimentAccession, new IsRowCommented());
    }

    protected Collection<String[]> readAndFilter(String experimentAccession, Predicate<String[]> filter) {

        Path fileSystemPath = FileSystems.getDefault().getPath(getPathString(experimentAccession));

        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(fileSystemPath));
             CSVReader csvReader = new CSVReader(reader, '\t')) {

            return Collections2.filter(csvReader.readAll(), filter);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot read Tsv file from path " + fileSystemPath.toString(), e);
        }
    }

    private static class IsRowCommented implements Predicate<String[]> {

        @Override
        public boolean apply(String[] columns) {
            return !columns[0].trim().startsWith("#");
        }
    }

    private String getPathString(String experimentAccession){
        return MessageFormat.format(getPathTemplate(), experimentAccession);
    }

}