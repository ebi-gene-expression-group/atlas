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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

/*
ToDo (B) : this needs renaming... what is this? a builder or what?? f#$*!!!

@Named
@Scope("prototype")
public class ExperimentDesignTsvWriter {

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

    public void rollback() {
        if (file.exists()) {
            boolean successfulDelete = file.delete();
            if (!successfulDelete){
                throw new IllegalStateException("Generation of ExperimentDesign file failed and also clean up of file failed");
            }
        }
    }
}

*/