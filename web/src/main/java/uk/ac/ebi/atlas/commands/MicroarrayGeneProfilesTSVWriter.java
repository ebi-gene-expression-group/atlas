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

package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Named("microarrayProfileWriter")
@Scope("prototype")
public class MicroarrayGeneProfilesTSVWriter extends GeneProfilesTSVWriter<MicroarrayProfile, Contrast> {

    @Inject
    protected MicroarrayGeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<Contrast> conditions) {
        List<String> columnNames = new ArrayList<>();
        for (Contrast condition : conditions) {
            columnNames.add(condition.getDisplayName() + ".p-value");
            columnNames.add(condition.getDisplayName() + ".log2foldchange");
            columnNames.add(condition.getDisplayName() + ".t-statistic");
        }

        return columnNames;
    }

    @Override
    protected String[] buildExpressionsRow(final MicroarrayProfile geneProfile, SortedSet<Contrast> contrasts) {
        String[] expressionLevels = new String[contrasts.size() * 3];
        int i = 0;
        for (Contrast contrast : contrasts) {
            MicroarrayExpression expression = geneProfile.getExpression(contrast);
            if (expression != null) {
                expressionLevels[i++] = getValueToString(expression.getLevel());
                expressionLevels[i++] = getValueToString(expression.getFoldChange());
                expressionLevels[i++] = getValueToString(expression.getTstatistic());
            } else {
                expressionLevels[i++] = "NA";
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
