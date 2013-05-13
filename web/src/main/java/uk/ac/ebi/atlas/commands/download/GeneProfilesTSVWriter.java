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

package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.Profile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.SortedSet;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public abstract class GeneProfilesTSVWriter<T extends Profile, K> {

    private CSVWriter csvWriter;
    private PrintWriter responseWriter;

    private GeneNamesProvider geneNamesProvider;

    @Inject
    public void setGeneNamesProvider(GeneNamesProvider geneNamesProvider){
        this.geneNamesProvider = geneNamesProvider;
    }

    public Long apply(ObjectInputStream<T> inputStream, SortedSet<K> conditions) throws IOException {

        responseWriter.write(buildHeaders() + "\n");
        csvWriter.writeNext(buildCsvHeaders(conditions));

        long count = 0;
        T geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            csvWriter.writeNext(buildCsvRow(geneProfile, conditions));
        }

        csvWriter.flush();
        csvWriter.close();

        return count;
    }

    protected String[] buildCsvHeaders(SortedSet<K> factorValues) {
        List<String> columnNames = buildConditionExpressionsHeaders(factorValues);
        return buildCsvRow(new String[]{HeaderBuilder.GENE_NAME, getSecondColumnName()}, columnNames.toArray(new String[columnNames.size()]));
    }

    protected String getSecondColumnName() {
        return HeaderBuilder.GENE_ID;
    }

    protected String[] buildCsvRow(final T geneProfile, SortedSet<K> factors) {
        String[] expressionLevels = extractConditionLevels(geneProfile, factors);

        String geneId = geneProfile.getId();
        String[] rowHeaders = new String[]{geneNamesProvider.getGeneName(geneId), getSecondColumnValue(geneProfile)};
        return buildCsvRow(rowHeaders, expressionLevels);
    }

    protected String getSecondColumnValue(T geneProfile) {
        return geneProfile.getId();
    }

    protected abstract List<String> buildConditionExpressionsHeaders(SortedSet<K> conditionNames);

    protected abstract String buildHeaders();

    protected String[] extractConditionLevels(T geneProfile, SortedSet<K> factors) {
        String[] expressionLevels = new String[factors.size()];
        int i = 0;
        for (K factor : factors) {
            expressionLevels[i++] = removeTrailingZero(geneProfile.getExpressionLevel(factor));
        }
        return expressionLevels;
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

    public void setResponseWriter(PrintWriter responseWriter) {
        this.responseWriter = responseWriter;
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER);
    }

    String removeTrailingZero(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

}