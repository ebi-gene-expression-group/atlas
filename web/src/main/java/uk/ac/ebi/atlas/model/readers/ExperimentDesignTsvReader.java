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

import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;

@Named("experimentDesignTsvReader")
public class ExperimentDesignTsvReader extends AbstractTsvReader {

    @Inject
    public ExperimentDesignTsvReader(ApplicationProperties a) {
        super(Logger.getLogger(ExperimentDesignTsvReader.class), a);
    }

    @Override
    public Collection<String[]> readAll(String experimentAccession) {
        Path path = FileSystems.getDefault().getPath(getApplicationProperties().getExperimentDesignTsvFilePath(experimentAccession));
        return read(path);
    }

}
