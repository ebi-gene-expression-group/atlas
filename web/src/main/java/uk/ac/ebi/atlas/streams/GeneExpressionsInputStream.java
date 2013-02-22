package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.GeneExpressions;
import uk.ac.ebi.atlas.model.Expression;

public class GeneExpressionsInputStream extends AbstractGeneExpressionsInputStream<GeneExpressions> {

    protected GeneExpressionsInputStream(CSVReader csvReader, String experimentAccession, ExpressionsBuffer.Builder expressionsBufferBuilder) {
        super(csvReader, experimentAccession, expressionsBufferBuilder);
    }

    @Override
    protected GeneExpressions buildGeneProfile(String[] values) {

        GeneExpressions geneProfile = new GeneExpressions();
        //we need to reload because the first line can only be used to extract the gene ID
        getExpressionsBuffer().reload(values);

        Expression expression;

        while ((expression = getExpressionsBuffer().poll()) != null) {

            geneProfile.addExpression(expression);
        }

        return geneProfile;
    }
}
