package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public abstract class ExpressionsWriter {

    private CsvReaderBuilder csvReaderBuilder;
    private GeneNamesProvider geneNamesProvider;
    private CSVWriter csvWriter;

    private String fileUrlTemplate;

    @Inject
    public ExpressionsWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider) {
        this.csvReaderBuilder = csvReaderBuilder;
        this.geneNamesProvider = geneNamesProvider;
    }

    public void setResponseWriter(PrintWriter responseWriter) {
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER);
    }

    public Long write(String experimentAccession) throws IOException {

        long lineCount = 0;

        try (CSVReader csvReader = getCsvReader(experimentAccession)) {
            //write header
            csvWriter.writeNext(buildHeader(csvReader.readNext()));

            String[] inputLine;
            while ((inputLine = csvReader.readNext()) != null) {
                String[] outputLine = ArrayUtils.addAll(new String[]{getGeneName(inputLine[0])}, inputLine);
                csvWriter.writeNext(outputLine);
                lineCount++;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
        }

        csvWriter.flush();
        csvWriter.close();

        return lineCount;
    }

    protected String getGeneName(String accession) {
        return geneNamesProvider.getGeneName(accession);
    }

    protected CSVReader getCsvReader(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(fileUrlTemplate, experimentAccession);
        return csvReaderBuilder.buildCsvReader(tsvFileURL);
    }

    protected abstract String[] buildHeader(String[] header);

    public void setFileUrlTemplate(String fileUrlTemplate) {
        this.fileUrlTemplate = fileUrlTemplate;
    }
}
