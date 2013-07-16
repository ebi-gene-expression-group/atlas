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

package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkState;

/*
ToDo (B) : this needs to be reanamed... what is this? a builder or what?? f#$*!!!
 */
@Named("expDesignTsvWriter")
@Scope("prototype")
public class ExpDesignTsvWriter {

    private String pathTemplate;

    private File file;

    @Inject
    void setPathTemplate(@Value("#{configuration['experiment.experiment-design.path.template']}") String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    public CSVWriter forExperimentAccession(String experimentAccession) throws IOException {
        file = FileSystems.getDefault().getPath(getPathString(experimentAccession)).toFile();
        FileWriter writer = new FileWriter(file);
        return new CSVWriter(writer, '\t');
    }

    //ToDo (B) remove this...
    public String getFileAbsolutePath() {
        checkState(file != null, "Please invoke forExperimentAccession first !");
        return file.getAbsolutePath();
    }

    String getPathString(String experimentAccession) {
        return MessageFormat.format(pathTemplate, experimentAccession);
    }

}