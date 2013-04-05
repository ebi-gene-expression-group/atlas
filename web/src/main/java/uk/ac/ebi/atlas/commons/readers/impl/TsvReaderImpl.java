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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("prototype")
public class TsvReaderImpl implements TsvReader {

    private static final Logger LOGGER = Logger.getLogger(TsvReaderImpl.class);

    private String pathTemplate;

    TsvReaderImpl() {
    }

    public void setPathTemplate(String pathTemplate){
        this.pathTemplate = pathTemplate;
    }

    @Override
    public String[] readLine(String experimentAccession, long lineIndex) {

        Path fileSystemPath = FileSystems.getDefault().getPath(getPathString(experimentAccession));

        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(fileSystemPath));
             CSVReader csvReader = new CSVReader(reader, '\t')) {
            String[] line = null;
            for (int i = 0; i <= lineIndex; i++) {
                line = csvReader.readNext();
            }
            return line;

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot read Tsv file from path " + fileSystemPath.toString(), e);
        }
    }

    @Override
    public List<String[]> readAll(String experimentAccession) {
        return readAndFilter(experimentAccession, new IsNotComment());
    }

    protected List<String[]> readAndFilter(String experimentAccession, Predicate<String> acceptanceCriteria) {

        Path fileSystemPath = FileSystems.getDefault().getPath(getPathString(experimentAccession));

        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(fileSystemPath));
             CSVReader csvReader = new CSVReader(reader, '\t')) {
            List<String[]> rows = new ArrayList<>();
            for (String[] row : csvReader.readAll()) {
                if (acceptanceCriteria.apply(row[0])) {
                    rows.add(row);
                }
            }
            return rows;

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot read Tsv file from path " + fileSystemPath.toString(), e);
        }
    }

    protected static class IsComment implements Predicate<String> {

        @Override
        public boolean apply(String rowHeader) {
            return rowHeader.trim().startsWith("#");
        }
    }

    private static class IsNotComment extends IsComment {

        @Override
        public boolean apply(String rowHeader) {
            return !super.apply(rowHeader);
        }
    }

    private String getPathString(String experimentAccession) {
        return MessageFormat.format(pathTemplate, experimentAccession);
    }

}