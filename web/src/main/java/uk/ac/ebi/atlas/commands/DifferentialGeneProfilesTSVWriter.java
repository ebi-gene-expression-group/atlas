package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Named("differentialProfileWriter")
@Scope("prototype")
public class DifferentialGeneProfilesTSVWriter extends GeneProfilesTSVWriter<DifferentialProfile, Contrast> {

    @Inject
    protected DifferentialGeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<String> conditionNames) {
        List<String> columnNames = new ArrayList<>();
        for (String conditionName : conditionNames) {
            columnNames.add(conditionName + ".p-value");
            columnNames.add(conditionName + ".log2foldchange");
        }

        return columnNames;
    }

    @Override
    protected String[] buildExpressionsRow(final DifferentialProfile geneProfile, SortedSet<Contrast> contrasts) {
        String[] expressionLevels = new String[contrasts.size()*2];
        int i = 0;
        for (Contrast contrast : contrasts) {
            DifferentialExpression expression = geneProfile.getExpression(contrast);
            expressionLevels[i++] = getValueToString(expression.getLevel());
            expressionLevels[i++] = getValueToString(expression.getFoldChange());
        }
        return expressionLevels;
    }

    private String getValueToString(double value) {
        if (Double.isInfinite(value)) {
            return "NA";
        }
        return Double.toString(value);
    }

}
