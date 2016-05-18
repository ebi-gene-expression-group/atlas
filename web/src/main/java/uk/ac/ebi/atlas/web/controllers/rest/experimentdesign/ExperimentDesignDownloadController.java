
package uk.ac.ebi.atlas.web.controllers.rest.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ExperimentDesignDownloadController<T extends Experiment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialDesignDownloadController.class);

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

        LOGGER.debug("<downloadExperimentDesign> streamed {} rows", newCsvLines.size());

        csvWriter.flush();
        csvWriter.close();
    }

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

}