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

package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.text.MessageFormat;


class ExpressionsWriterImpl implements ExpressionsWriter {


    private CsvReaderFactory csvReaderFactory;

    private CSVWriter csvWriter;

    private String fileUrlTemplate;

    private String experimentAccession;
    private AnalyticsDataHeaderBuilder headerBuilder;
    private String arrayDesignAccession;

    public ExpressionsWriterImpl(CsvReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }

    public void setResponseWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public void setFileUrlTemplate(String fileUrlTemplate) {
        this.fileUrlTemplate = fileUrlTemplate;
    }

    @Override
    public Long write() throws IOException {

        long lineCount = 0;

        try (CSVReader csvReader = getCsvReader()) {
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

    CSVReader getCsvReader() {
        String tsvFileURL = formatUrl(fileUrlTemplate);
        return csvReaderFactory.createTsvReader(tsvFileURL);
    }

    String formatUrl(String fileUrlTemplate) {
        if (StringUtils.isNotBlank(arrayDesignAccession)){
            return MessageFormat.format(fileUrlTemplate, experimentAccession, arrayDesignAccession);
        }
        return MessageFormat.format(fileUrlTemplate, experimentAccession);
    }

    public void setHeaderBuilder(AnalyticsDataHeaderBuilder headerBuilder) {
        this.headerBuilder = headerBuilder;
    }

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }
}
