package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.CompleteGeneProfile;
import uk.ac.ebi.atlas.model.Expression;

public class CompleteGeneProfileInputStream extends AbstractGeneProfilesInputStream<CompleteGeneProfile> {

    protected CompleteGeneProfileInputStream(CSVReader csvReader, String experimentAccession, ExpressionsBuffer.Builder expressionsBufferBuilder) {
        super(csvReader, experimentAccession, expressionsBufferBuilder);
    }

    @Override
    protected CompleteGeneProfile buildGeneProfile(String[] values) {

        CompleteGeneProfile geneProfile = new CompleteGeneProfile();
        //we need to reload because the first line can only be used to extract the gene ID
        getExpressionsBuffer().reload(values);

        Expression expression;

        while ((expression = getExpressionsBuffer().poll()) != null) {

            geneProfile.addExpression(expression);
        }

        return geneProfile;
    }
}
