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

package uk.ac.ebi.atlas.web.controllers.rest.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ExperimentDesignDownloadController<T extends Experiment> {

    private static final Logger LOGGER = Logger.getLogger(DifferentialDesignDownloadController.class);

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String pathTemplate;

    private FileTsvReaderBuilder fileTsvReaderBuilder;

    @PostConstruct
    protected void initializeTsvReader() {
        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(pathTemplate);
    }

    public ExperimentDesignDownloadController(FileTsvReaderBuilder fileTsvReaderBuilder) {
        this.fileTsvReaderBuilder = fileTsvReaderBuilder;
    }

    protected void extractExperimentDesign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        TsvReader tsvReader = fileTsvReaderBuilder.withExperimentAccession(experiment.getAccession()).build();

        // read contents from file
        List<String[]> csvLines = new ArrayList<>(tsvReader.readAll());
        List<String[]> newCsvLines = new ArrayList<>(csvLines.size());

        // modify header by adding new column
        String[] header = csvLines.remove(0);
        String[] newHeader = new String[header.length + 1];
        System.arraycopy(header, 0, newHeader, 0, header.length);
        newHeader[header.length] = "Analysed";
        newCsvLines.add(newHeader);

        // copy content and add used field
        for (String[] array : csvLines) {
            String[] newArray = new String[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            boolean isRunAnalysed = getAnalysedRowsAccessions(experiment).contains(newArray[0]);
            newArray[array.length] = isRunAnalysed ? "Yes" : "No";
            newCsvLines.add(newArray);
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-experiment-design.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        CSVWriter csvWriter = new CSVWriter(response.getWriter(), '\t', CSVWriter.NO_QUOTE_CHARACTER);
        csvWriter.writeAll(newCsvLines);

        LOGGER.debug("<downloadExperimentDesign> streamed " + newCsvLines.size() + " rows");

        csvWriter.flush();
        csvWriter.close();
    }

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

}