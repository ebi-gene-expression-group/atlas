
package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ExperimentDesignDownloadController<T extends Experiment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialDesignDownloadController.class);

    private final ExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;


    public ExperimentDesignDownloadController(DataFileHub dataFileHub, ExperimentTrader experimentTrader) {
        this.dataFileHub = dataFileHub;
        this.experimentTrader =experimentTrader;
    }

    protected void extractExperimentDesign(String experimentAccession, HttpServletResponse response, String
            accessKey) throws IOException {
        T experiment = (T) experimentTrader.getExperiment(experimentAccession,accessKey);

        TsvReader tsvReader = dataFileHub.getExperimentFiles(experimentAccession).experimentDesign.get();

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

        LOGGER.debug("<downloadExperimentDesign> streamed {} rows", newCsvLines.size());

        csvWriter.flush();
        csvWriter.close();
    }

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

}