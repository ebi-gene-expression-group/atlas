
package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.io.IOException;


class ExpressionsWriterReadingFromAtlasResource implements ExpressionsWriter {


    private final AtlasResource<CSVReader> reader;

    private CSVWriter csvWriter;


    private AnalyticsDataHeaderBuilder headerBuilder;

    public ExpressionsWriterReadingFromAtlasResource(AtlasResource<CSVReader> reader, AnalyticsDataHeaderBuilder
            headerBuilder,CSVWriter csvWriter) {
        this.reader = reader;
        this.headerBuilder = headerBuilder;
        this.csvWriter = csvWriter;
    }

    @Override
    public Long write() throws IOException {

        long lineCount = 0;

        try (CSVReader csvReader = reader.get()) {
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

}
