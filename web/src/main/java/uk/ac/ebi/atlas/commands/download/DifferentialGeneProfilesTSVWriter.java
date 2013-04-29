package uk.ac.ebi.atlas.commands.download;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Named("differentialProfileWriter")
@Scope("prototype")
public class DifferentialGeneProfilesTSVWriter extends GeneProfilesTSVWriter<RnaSeqProfile, Contrast> {

    @Inject
    public DifferentialGeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<Contrast> conditions) {
        List<String> columnNames = new ArrayList<>();
        for (Contrast condition : conditions) {
            columnNames.add(condition.getDisplayName() + ".p-value");
            columnNames.add(condition.getDisplayName() + ".log2foldchange");
        }

        return columnNames;
    }

    @Override
    protected String[] buildHeaders() {
        return new String[0];
    }

    @Override
    protected String[] buildExpressionsRow(final RnaSeqProfile geneProfile, SortedSet<Contrast> contrasts) {
        String[] expressionLevels = new String[contrasts.size() * 2];
        int i = 0;
        for (Contrast contrast : contrasts) {
            DifferentialExpression expression = geneProfile.getExpression(contrast);
            if (expression != null) {
                expressionLevels[i++] = getValueToString(expression.getLevel());
                expressionLevels[i++] = getValueToString(expression.getFoldChange());
            } else {
                expressionLevels[i++] = "NA";
                expressionLevels[i++] = "NA";
            }
        }
        return expressionLevels;
    }

    protected String getValueToString(double value) {
        if (Double.isInfinite(value)) {
            return "NA";
        }
        return Double.toString(value);
    }

}
