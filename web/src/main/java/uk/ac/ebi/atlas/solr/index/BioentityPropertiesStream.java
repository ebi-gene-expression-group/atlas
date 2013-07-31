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

package uk.ac.ebi.atlas.solr.index;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class BioentityPropertiesStream implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(BioentityPropertiesStream.class);

    private BioentityPropertiesBuilder bioentityPropertiesBuilder;

    private CSVReader csvReader;

    BioentityPropertiesStream(CSVReader csvReader, BioentityPropertiesBuilder bioentityPropertiesBuilder, String species) throws IOException {
        this.csvReader = csvReader;
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder;
        List<String> csvHeaders = Lists.newArrayList(csvReader.readNext());
        String bioentityType = csvHeaders.remove(0);
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder
                                            .forBioentityType(bioentityType)
                                            .forSpecies(species)
                                            .forPropertyNames(csvHeaders);
    }

    public List<BioentityProperty> next() throws IOException {
        String[] csvValues = csvReader.readNext();
        if (csvValues != null) {
            return buildBioentityProperties(csvValues);
        }
        return null;
    }

    List<BioentityProperty> buildBioentityProperties(String[] csvValues) {
        List<String> propertyValues = Lists.newArrayList(csvValues);
        String bioentityIdentifier = propertyValues.remove(0);
        return bioentityPropertiesBuilder.withBioentityIdentifier(bioentityIdentifier)
                                        .withPropertyValues(propertyValues)
                                        .build();
    }

    @Override
    public void close() throws IOException {
        LOGGER.debug("<close> close invoked on PropertyStream");
        csvReader.close();
    }
}
