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
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;

public class BioentityPropertyStream implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(BioentityPropertyStream.class);

    private CSVReader csvReader;

    BioentityPropertyStream(CSVReader csvReader) throws IOException {
        this.csvReader = csvReader;
        csvReader.readNext();//skipping the header row
    }

    public BioentityPropertyDocument next() throws IOException {
        String[] csvValues = csvReader.readNext();
        if (csvValues == null){
            return null;
        }
        return new BioentityPropertyDocument("gene", "anopheles_gambiae", "design_element", csvValues);
    }

    @Override
    public void close() throws IOException {
        LOGGER.debug("<close> close invoked on PropertyStream");
        csvReader.close();
    }

}
