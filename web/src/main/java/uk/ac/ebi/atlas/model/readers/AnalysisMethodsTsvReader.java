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

import com.google.common.base.Predicate;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Named("analysisMethodsTsvReader")
@Scope("singleton")
public class AnalysisMethodsTsvReader extends AbstractTsvReader {

    private static final String LIBRARIES_TITLE = "Processed libraries";

    @Inject
    public AnalysisMethodsTsvReader(ApplicationProperties a) {
        super(Logger.getLogger(AnalysisMethodsTsvReader.class), a);
    }

    @Override
    public Collection<String[]> readAll(String experimentAccession) {
        Path path = FileSystems.getDefault().getPath(applicationProperties.getAnalisysMethodTsvFilePath(experimentAccession));
        return readAllForPath(path);
    }

    public Collection<String[]> readAllWithoutLibraries(String experimentAccession) {
        Predicate<String[]> predicate = new Predicate<String[]>() {
            @Override
            public boolean apply(String[] columns) {
                String firstColumn = columns[0].trim();
                return !(firstColumn.startsWith("#") || firstColumn.equals(LIBRARIES_TITLE));
            }
        };
        Path path = FileSystems.getDefault().getPath(applicationProperties.getAnalisysMethodTsvFilePath(experimentAccession));
        return readAndFilter(path, predicate);
    }

    public Map<String, String> readAsMap(String experimentAccession) {
        Collection<String[]> rows = readAll(experimentAccession);
        Map<String, String> keyValuePairs = new HashMap<>(rows.size());
        for (String[] row : rows) {
            keyValuePairs.put(row[0], row[1]);
        }
        return keyValuePairs;
    }

}
