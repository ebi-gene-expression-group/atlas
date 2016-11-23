package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExperimentDesignDownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentDesignDownloadService.class);

    private final DataFileHub dataFileHub;

    public ExperimentDesignDownloadService(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    public void writeLines(String experimentAccession, Set<String> analysedRowsAccessions, PrintWriter writer){
        List<String[]> newCsvLines = getLines(analysedRowsAccessions, dataFileHub.getExperimentFiles(experimentAccession).experimentDesign.get().readAll());

        try {
            CSVWriter csvWriter = new CSVWriter(writer, '\t', CSVWriter.NO_QUOTE_CHARACTER);
            csvWriter.writeAll(newCsvLines);

            LOGGER.debug("<downloadExperimentDesign> streamed {} rows", newCsvLines.size());

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    List<String[]> getLines(Set<String> analysedRowsAccessions, List<String[]> csvLines){

        List<String[]> newCsvLines = new ArrayList<>(csvLines.size());


        // modify header by adding new column
        String[] header = csvLines.get(0);
        String[] newHeader = new String[header.length + 1];
        System.arraycopy(header, 0, newHeader, 0, header.length);
        newHeader[header.length] = "Analysed";
        newCsvLines.add(newHeader);

        // copy content and add used field
        for (String[] array : csvLines.subList(1, csvLines.size())) {
            String[] newArray = new String[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            boolean isRunAnalysed = analysedRowsAccessions.contains(newArray[0]);
            newArray[array.length] = isRunAnalysed ? "Yes" : "No";
            newCsvLines.add(newArray);
        }

        return newCsvLines;
    }
}

