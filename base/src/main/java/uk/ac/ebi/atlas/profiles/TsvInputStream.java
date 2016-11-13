package uk.ac.ebi.atlas.profiles;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.Expression;

import java.io.IOException;

public abstract class TsvInputStream<T, K extends Expression> implements ExpressionProfileInputStream<T, K> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsvInputStream.class);

    private CSVReader csvReader;

    private ExpressionsRowDeserializer<String, K> expressionsRowTsvDeserializer;

    protected TsvInputStream(CSVReader csvReader, String experimentAccession, ExpressionsRowDeserializerBuilder<String, K> expressionsRowDeserializerBuilder) {
        this.csvReader = csvReader;

        String[] firstCsvLine = readCsvLine();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstCsvLine);
        expressionsRowTsvDeserializer = expressionsRowDeserializerBuilder.forExperiment(experimentAccession).withHeaders(headersWithoutGeneIdColumn).build();
    }

    @Override
    public T readNext() {
        T geneProfile;

        do {
            String[] values = readCsvLine();

            if (values == null) {
                return null;
            }
            geneProfile = buildObjectFromTsvValues(values);

        } while (geneProfile == null);

        return geneProfile;
    }

    private String[] readCsvLine() {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    protected T buildObjectFromTsvValues(String[] values) {
        addGeneInfoValueToBuilder(values);

        //we need to reload because the first line can only be used to extract the gene ID
        expressionsRowTsvDeserializer.reload(removeGeneIDAndNameColumns(values));

        K expression;

        while ((expression = expressionsRowTsvDeserializer.next()) != null) {
            addExpressionToBuilder(expression);
        }

        return createProfile();
    }

    protected ExpressionsRowDeserializer<String, K> getExpressionsRowTsvDeserializer() {
        return expressionsRowTsvDeserializer;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }

}
