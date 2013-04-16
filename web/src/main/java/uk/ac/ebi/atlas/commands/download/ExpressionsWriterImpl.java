package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.TsvReaderBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

class ExpressionsWriterImpl implements ExpressionsWriter {


    private TsvReaderBuilder tsvReaderBuilder;
    private GeneNamesProvider geneNamesProvider;
    private CSVWriter csvWriter;

    private String fileUrlTemplate;

    private HeaderBuilder headerBuilder;

    private String experimentAccession;

    public ExpressionsWriterImpl(TsvReaderBuilder tsvReaderBuilder, GeneNamesProvider geneNamesProvider) {
        this.tsvReaderBuilder = tsvReaderBuilder;
        this.geneNamesProvider = geneNamesProvider;
    }


    public void setResponseWriter(PrintWriter responseWriter) {
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER);
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public void setFileUrlTemplate(String fileUrlTemplate) {
        this.fileUrlTemplate = fileUrlTemplate;
    }

    public void setHeaderBuilder(HeaderBuilder headerBuilder) {
        this.headerBuilder = headerBuilder;
    }

    public Long write() throws IOException {

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
        String tsvFileURL = formatUrl(fileUrlTemplate, experimentAccession);
        return tsvReaderBuilder.build(tsvFileURL);
    }

    protected String formatUrl(String fileUrlTemplate, String experimentAccession) {
        return MessageFormat.format(fileUrlTemplate, experimentAccession);
    }

    protected String[] buildHeader(String[] header) {
        return headerBuilder.buildHeader(header);
    }
}
