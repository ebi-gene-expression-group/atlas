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

package uk.ac.ebi.atlas.model.readers;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Named("analysisMethodsTsvReader")
@Scope("singleton")
public class AnalysisMethodsTsvReader {

    private static final Logger logger = Logger.getLogger(AnalysisMethodsTsvReader.class);

    private ApplicationProperties applicationProperties;

    @Inject
    public AnalysisMethodsTsvReader(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public Collection<String[]> readAll(String experimentAccession) throws IllegalStateException{
        try {
            Path filePath = FileSystems.getDefault().getPath(applicationProperties.getAnalisysMethodCsvFilePath(experimentAccession));

            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));

            CSVReader csvReader = new CSVReader(dataFileReader, '\t');

            return Collections2.filter(csvReader.readAll(), new IsCommented());
        } catch (IOException e) {

            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);

        }
    }

    public Map<String, String> readAsMap(String experimentAccession) {
        Collection<String[]> rows = readAll(experimentAccession);
        Map<String, String> keyValuePairs = new HashMap<>();
        for(String[] row: rows){
            keyValuePairs.put(row[0], row[1]);
        }
        return keyValuePairs;
    }

    protected class IsCommented implements Predicate<String[]> {

        @Override
        public boolean apply(String[] columns) {
            return ! columns[0].trim().startsWith("#");
        }
    }

}
