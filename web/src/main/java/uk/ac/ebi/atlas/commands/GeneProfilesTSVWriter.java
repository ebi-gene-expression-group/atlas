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

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.SortedSet;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public abstract class GeneProfilesTSVWriter<T extends GeneProfile, K> {

    private CSVWriter csvWriter;

    protected NumberUtils numberUtils;
    private GeneNamesProvider geneNamesProvider;

    @Inject
    protected GeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        this.numberUtils = numberUtils;
        this.geneNamesProvider = geneNamesProvider;
    }

    protected Long apply(ObjectInputStream<T> inputStream, SortedSet<K> conditions) throws IOException {
        long count = 0;

        csvWriter.writeNext(buildCsvHeaders(conditions));

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
        List<String> columnNames = buildColumnNames(factorValues);
        return buildCsvRow(new String[]{"Gene name", getSecondColumnName()}, columnNames.toArray(new String[columnNames.size()]));
    }

    protected String getSecondColumnName() {
        return "Gene Id";
    }

    protected String[] buildCsvRow(final T geneProfile, SortedSet<K> factors) {
        String[] expressionLevels = buildExpressionsRow(geneProfile, factors);

        String geneId = geneProfile.getGeneId();
        return buildCsvRow(new String[]{geneNamesProvider.getGeneName(geneId), getSecondColumnValue(geneProfile)}, expressionLevels);
    }

    protected String getSecondColumnValue(T geneProfile) {
        return geneProfile.getGeneId();
    }

    protected abstract List<String> buildColumnNames(SortedSet<K> conditionNames);

    protected String[] buildExpressionsRow(T geneProfile, SortedSet<K> factors) {
        String[] expressionLevels = new String[factors.size()];
        int i = 0;
        for (K factor : factors) {
            expressionLevels[i++] = numberUtils.removeTrailingZero(geneProfile.getExpressionLevel(factor));
        }
        return expressionLevels;
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {

        return ArrayUtils.addAll(rowHeaders, values);

    }

    public void setResponseWriter(PrintWriter responseWriter) {
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER);
    }

}