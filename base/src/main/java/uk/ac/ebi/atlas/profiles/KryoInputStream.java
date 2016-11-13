package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;

public abstract class KryoInputStream<T, K extends Expression> implements ExpressionProfileInputStream<T, K> {

    private BaselineExpressionsKryoReader baselineExpressionsKryoReader;

    private ExpressionsRowDeserializer<BaselineExpression, K> expressionsRowRawDeserializer;

    protected KryoInputStream(BaselineExpressionsKryoReader baselineExpressionsKryoReader, String experimentAccession, ExpressionsRowDeserializerBuilder<BaselineExpression, K> expressionsRowDeserializerBuilder) {
        this.baselineExpressionsKryoReader = baselineExpressionsKryoReader;

        String[] firstLine = baselineExpressionsKryoReader.rewindAndReadAssays();
        String[] headersWithoutGeneIdColumn = removeGeneIDAndNameColumns(firstLine);
        expressionsRowRawDeserializer = expressionsRowDeserializerBuilder.forExperiment(experimentAccession).withHeaders(headersWithoutGeneIdColumn).build();
    }

    @Override
    public T readNext() {
        T geneProfile;

        do {
            if (!baselineExpressionsKryoReader.readLine()) {
                return null;
            }
            geneProfile =  buildObjectFromValues(baselineExpressionsKryoReader.getGeneId(), baselineExpressionsKryoReader.getGeneName(), baselineExpressionsKryoReader.getExpressions());

        } while (geneProfile == null);

        return geneProfile;
    }

    protected T buildObjectFromValues(String geneId, String geneName, BaselineExpression[] expressions) {
        addGeneInfoValueToBuilder(new String[]{geneId, geneName});

        //we need to reload because the first line can only be used to extract the gene ID
        expressionsRowRawDeserializer.reload(expressions);

        K expression;

        while ((expression = expressionsRowRawDeserializer.next()) != null) {
             addExpressionToBuilder(expression);
        }

        return createProfile();
    }

    // Used by BaselineExpressionsInputStream
    protected ExpressionsRowDeserializer<BaselineExpression, K> getExpressionsRowRawDeserializer() {
         return expressionsRowRawDeserializer;
    }

    @Override
    public void close() throws IOException {
        baselineExpressionsKryoReader.close();
    }

    protected String[] removeGeneIDAndNameColumns(String[] columns) {
        return (String[]) ArrayUtils.subarray(columns, 2, columns.length);
    }

}
