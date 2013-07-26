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

package uk.ac.ebi.atlas.geneannotation;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Maps;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

@Named("fileBasedPropertyMappingFinder")
public class FileBasedPropertyMappingFinder implements PropertyMappingFinder {

    private TsvReaderUtils tsvReaderUtils;

    @Inject
    public FileBasedPropertyMappingFinder(TsvReaderUtils tsvReaderUtils) {
        this.tsvReaderUtils = tsvReaderUtils;
    }

    @Override
    public Map<String, String> executeQuery(String serverUrl, String... urlVariables) {
        Map<String, String> result = Maps.newHashMap();

        String annotationFileLocation = MessageFormat.format(serverUrl, (Object[])urlVariables);
        CSVReader csvReader = tsvReaderUtils.build(annotationFileLocation);
        try {
            String[] inputLine;
            //Skip first line with header
            csvReader.readNext();
            while ((inputLine = csvReader.readNext()) != null) {
                result.put(inputLine[0], inputLine[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
