
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerBaselineBuilder;

public class BaselineExpressionsTsvInputStream extends TsvInputStream<BaselineExpressions, BaselineExpression> {

    public BaselineExpressionsTsvInputStream(CSVReader csvReader, String experimentAccession, ExpressionsRowDeserializerBaselineBuilder baselineExpressionsQueueBuilder) {
        super(csvReader, experimentAccession, baselineExpressionsQueueBuilder);
    }

    @Override
    protected BaselineExpressions buildObjectFromTsvValues(String[] values) {

        BaselineExpressions baselineExpressions = new BaselineExpressions();
        //we need to reload because the first line can only be used to extract the gene ID
        getExpressionsRowTsvDeserializer().reload(removeGeneIDAndNameColumns(values));

        BaselineExpression expression;

        while ((expression = getExpressionsRowTsvDeserializer().next()) != null) {

            baselineExpressions.addExpression(expression);
        }

        return baselineExpressions;
    }

    @Override
    public BaselineExpressions createProfile() {
        return null;
    }

    @Override
    public void addExpressionToBuilder(BaselineExpression expression) {
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
    }
}
