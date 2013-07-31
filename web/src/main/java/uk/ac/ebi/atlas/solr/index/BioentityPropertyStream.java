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

public class BioentityPropertyStream implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(BioentityPropertyStream.class);

    private static final String DESIGN_ELEMENT_PROPERTY_NAME = "design_element";
    private CSVReader csvReader;
    private String species;


    private List<String> headers;

    BioentityPropertyStream(CSVReader csvReader, String species) throws IOException {
        this.csvReader = csvReader;
        this.species = species;
        headers = Lists.newArrayList(csvReader.readNext());
    }

    public BioentityPropertyDocument next() throws IOException {
        String[] csvValues = csvReader.readNext();
        if (csvValues != null) {
            return buildBioentityPropertyDocument(csvValues);
        }
        return null;
    }

    private BioentityPropertyDocument buildBioentityPropertyDocument(String[] csvValues) {
        if (isDesignElementStream()) {
            return new BioentityPropertyDocument(getBioentityType(), species, DESIGN_ELEMENT_PROPERTY_NAME, csvValues[0], csvValues[1]);
        }
        return null;


    }

    private String getBioentityType() {return headers.get(0);}

    @Override
    public void close() throws IOException {
        LOGGER.debug("<close> close invoked on PropertyStream");
        csvReader.close();
    }

    private boolean isDesignElementStream() {
        return DESIGN_ELEMENT_PROPERTY_NAME.equals(headers.get(1));
    }
}
