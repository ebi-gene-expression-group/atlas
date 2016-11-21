package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Reader;

class ExpressionsWriterImpl implements ExpressionsWriter {

    private CSVWriter csvWriter;

    private Reader reader;

    private AnalyticsDataHeaderBuilder headerBuilder;

    public void setResponseWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Long write() throws IOException {

        long lineCount = 0;

        try (CSVReader csvReader = new CSVReader(reader, '\t')) {
            String[] headers = buildHeader(csvReader.readNext());

            csvWriter.writeNext(headers);

            String[] inputLine;
            while ((inputLine = csvReader.readNext()) != null) {
                csvWriter.writeNext(inputLine);
                lineCount++;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
        }

        csvWriter.flush();

        return lineCount;
    }

    String[] buildHeader(String[] headers) {
        //write header
        if (headerBuilder != null) {
            headers = headerBuilder.buildHeader(headers);
        }
        return headers;
    }

    @Override
    public void close() throws IOException {
        csvWriter.close();
    }

    public void setHeaderBuilder(AnalyticsDataHeaderBuilder headerBuilder) {
        this.headerBuilder = headerBuilder;
    }

}
