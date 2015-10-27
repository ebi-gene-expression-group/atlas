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

package uk.ac.ebi.atlas.solr.admin.index;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.atlas.solr.BioentityProperty;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class BioentityPropertiesStream implements Closeable {

    private static final Logger LOGGER = LogManager.getLogger(BioentityPropertiesStream.class);

    private static final double BATCH_SIZE = 50000;

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

    public Collection<BioentityProperty> next() throws IOException {
        int lineCount = 0;

        Collection<BioentityProperty> propertiesBuffer = Lists.newArrayList();

        String[] csvValues;
        // observe order here, otherwise some data lines will be lost because of readNext()
        while (propertiesBuffer.size() <= BATCH_SIZE && (csvValues = csvReader.readNext()) != null) {
            lineCount++;

            List<String> propertyValues = Lists.newArrayList(csvValues);
            String bioentityIdentifier = propertyValues.remove(0);

            if (bioentityIdentifier.trim().isEmpty()) {
                LOGGER.warn("Line " + lineCount + " ignored because bioentityIdentifier is empty");
            }
            else {
                Collection<BioentityProperty> properties = bioentityPropertiesBuilder
                        .withBioentityIdentifier(bioentityIdentifier)
                        .withPropertyValues(propertyValues)
                        .build();
                propertiesBuffer.addAll(properties);
            }
        }

        if (propertiesBuffer.size() > 0) {
            return propertiesBuffer;
        }

        return null;

    }

    @Override
    public void close() throws IOException {
        LOGGER.debug("<close> close invoked on PropertyStream");
        csvReader.close();
    }
}
