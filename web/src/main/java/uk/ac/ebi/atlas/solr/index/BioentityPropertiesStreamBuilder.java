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
import com.google.common.base.Charsets;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
@Scope("prototype")
public class BioentityPropertiesStreamBuilder {

    private final BioentityPropertiesBuilder bioentityPropertiesBuilder;
    private Path bioentityPropertiesFilePath;

    @Inject
    public BioentityPropertiesStreamBuilder(BioentityPropertiesBuilder bioentityPropertiesBuilder){
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder;
    }

    public BioentityPropertiesStreamBuilder forPath(Path bioentityPropertiesFilePath){
        this.bioentityPropertiesFilePath = bioentityPropertiesFilePath;
        return this;
    }

    public BioentityPropertiesStream build() throws IOException{
        Reader fileReader = Files.newBufferedReader(bioentityPropertiesFilePath, Charsets.UTF_8);
        CSVReader csvReader = new CSVReader(fileReader,'\t');
        return new BioentityPropertiesStream(csvReader, bioentityPropertiesBuilder, getSpecies());
    }

    String getFileName() {
       return bioentityPropertiesFilePath.getFileName().toString();
    }

    String getSpecies() {
        return StringUtils.substringBefore(getFileName(), ".");
    }
}
