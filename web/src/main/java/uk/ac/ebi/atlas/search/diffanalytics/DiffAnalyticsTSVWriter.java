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

package uk.ac.ebi.atlas.search.diffanalytics;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.utils.VisitorException;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named
@Scope("prototype")
public class DiffAnalyticsTSVWriter implements AutoCloseable, Visitor<DiffAnalytics> {
    private static final Logger LOGGER = LogManager.getLogger(DiffAnalyticsTSVWriter.class);
    private String tsvFileMastheadTemplate;
    private static final String[] HEADERS = {"Gene", "Organism", "Experiment Accession", "Comparison", "p-value", "log2foldchange", "t-statistic"};

    private CSVWriter csvWriter;
    private PrintWriter responseWriter;

    private static final int FLUSH_INTERVAL = 100;

    private int count = 0;

    @Value("classpath:/file-templates/download-headers-geneQuery.txt")
    public void setTsvFileMastheadTemplateResource(Resource tsvFileMastheadResource) {
        this.tsvFileMastheadTemplate = resourceContentsToString(tsvFileMastheadResource);
    }

    public static String resourceContentsToString(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public void setResponseWriter(PrintWriter writer) {
        this.responseWriter = writer;
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER);
    }

    public void writeHeader(GeneQuerySearchRequestParameters requestParameters) {
        responseWriter.write(getTsvFileMasthead(requestParameters) + "\n");
        csvWriter.writeNext(HEADERS);
        responseWriter.flush();
    }

    public int write(DiffAnalyticsList expressions) throws IOException {

        for (DiffAnalytics expression : expressions) {
            write(expression);
        }

        csvWriter.flush();

        return expressions.size();
    }

    private void write(DiffAnalytics expression) throws IOException {
        checkWriterNotInError();

        String[] csvRow = buildCsvRow(expression);
        csvWriter.writeNext(csvRow);
    }

    private void checkWriterNotInError() throws IOException {
        count++;

        //checkError does a flush, so only check every FLUSH_INTERVAL
        if ((count % FLUSH_INTERVAL==0) && responseWriter.checkError()) {
            // abort eg: when the client hangs up during download
            throw new IOException("responseWriter error writing expression #" + count);
        }
    }

    //will throw VisitorException if there are errors during writing
    @Override
    public void visit(DiffAnalytics value) {
        try {
            write(value);
        } catch (IOException e) {
            throw new VisitorException(e.getMessage(), e.getCause());
        }
    }

    private String[] buildCsvRow(DiffAnalytics dbExpression) {
        DifferentialExpression expression = dbExpression.getExpression();
        return new String[] {dbExpression.getBioentityName(),
                dbExpression.getSpecies(),
                dbExpression.getExperimentAccession(),
                dbExpression.getContrastDisplayName(),
                expressionValueAsString(expression.getPValue()),
                expressionValueAsString(expression.getFoldChange()),
                (expression instanceof MicroarrayExpression) ? expressionValueAsString(((MicroarrayExpression)expression).getTstatistic()) : "NA"
        };
    }

    private String expressionValueAsString(double expressionValue) {
        if (expressionValue == Double.POSITIVE_INFINITY) {
            return "Inf";
        } else if (expressionValue == Double.NEGATIVE_INFINITY) {
            return "-Inf";
        }
        return Double.toString(expressionValue);
    }

    public String getTsvFileMasthead(GeneQuerySearchRequestParameters requestParameters) {
        GeneQuery geneQuery = requestParameters.getGeneQuery();
        String geneQueryHeader = !geneQuery.isEmpty() ? "Genes matching: '" + geneQuery.description() + "'" : "";
        String exactMatch = !geneQuery.isEmpty() && requestParameters.isExactMatch() ? " exactly" : "";
        String comma = !geneQuery.isEmpty() ? ", " : "";

        boolean hasCondition = requestParameters.hasCondition();
        String condition = hasCondition ? " in condition matching '" + requestParameters.getConditionQuery().asString() + "'": "";
        String organism = StringUtils.isNotEmpty(requestParameters.getOrganism()) ? (hasCondition ? " and" : "")  + " in organism '" + requestParameters.getOrganism() + "'": "";
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, geneQueryHeader, exactMatch, comma, condition, organism, timeStamp);
    }

    @Override
    public void close() throws IOException {
        csvWriter.close();
    }

}