package uk.ac.ebi.atlas.search.diffanalytics;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;

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
public class DiffAnalyticsTSVWriter implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffAnalyticsTSVWriter.class);

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

    private static String resourceContentsToString(Resource resource) {
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

//    public void writeHeader(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
//        responseWriter.write(getTsvFileMasthead(geneQuery, conditionQuery, species) + "\n");
//        csvWriter.writeNext(HEADERS);
//        responseWriter.flush();
//    }

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

    public void visit(DiffAnalytics value) {
        try {
            write(value);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
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

    public String getTsvFileMasthead(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String geneQueryHeader = geneQuery.isNotEmpty() ? "Genes matching: '" + SearchDescription.get(geneQuery) + "'" : "";
        String comma = geneQuery.isNotEmpty() ? ", " : "";

        boolean hasCondition = conditionQuery.isNotEmpty();
        String condition = hasCondition ? " in condition matching '" + SearchDescription.get(conditionQuery) + "'": "";
        String organism = StringUtils.isNotEmpty(species) ? (hasCondition ? " and" : "")  + " in organism '" + species + "'": "";
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, geneQueryHeader, comma, condition, organism, timeStamp);
    }

    @Override
    public void close() throws IOException {
        csvWriter.close();
    }

}