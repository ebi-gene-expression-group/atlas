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
import uk.ac.ebi.atlas.web.ApplicationProperties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public abstract class AbstractTsvReader {

    private final Logger log4jLogger;

    // changed to private by Sonar recommendation
    private final ApplicationProperties applicationProperties;

    protected AbstractTsvReader(Logger logger, ApplicationProperties a) {
        log4jLogger = logger;
        applicationProperties = a;
    }

    public abstract Collection<String[]> readAll(String experimentAccession);

    ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    Collection<String[]> read(Path path) {
        return readAndFilter(path, new IsCommented());
    }

    Collection<String[]> readAndFilter(Path path,
                                       Predicate<String[]> filter) {

        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(path));
             CSVReader csvReader = new CSVReader(reader, '\t')) {

            return Collections2.filter(csvReader.readAll(), filter);

        } catch (IOException e) {
            log4jLogger.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot read Tsv file from path " + path, e);
        }
    }

    private static class IsCommented implements Predicate<String[]> {

        @Override
        public boolean apply(String[] columns) {
            return !columns[0].trim().startsWith("#");
        }
    }

}