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

import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.io.IOException;

//ToDo: (N) to be tested

public abstract class ExperimentDesignWriter {

    public void write(String experimentAccession, CSVWriter csvWriter) throws IOException {

        ExperimentDesign experimentDesign = getMageTabParser().parse(experimentAccession);
        csvWriter.writeNext(composeHeader(experimentDesign));

        csvWriter.writeAll(experimentDesign.asTableData());


    }

    protected abstract String[] composeHeader(ExperimentDesign experimentDesign);

    protected abstract MageTabParser getMageTabParser();
}
