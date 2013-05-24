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
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.SortedSet;

import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public abstract class GeneProfilesTSVWriter<T extends Profile, K> {

    private CSVWriter csvWriter;
    private PrintWriter responseWriter;

    private GeneNamesProvider geneNamesProvider;

    @Inject
    public void setGeneNamesProvider(GeneNamesProvider geneNamesProvider){
        this.geneNamesProvider = geneNamesProvider;
    }

    public Long write(GeneProfilesList<T> geneProfilesList, SortedSet<K> conditions) throws IOException {

        responseWriter.write(getTsvFileMasthead() + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions));

        for (T profile: geneProfilesList){
            String[] csvRow = buildCsvRow(profile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();
        csvWriter.close();

        return (long)geneProfilesList.size();
    }

    public Long write(ObjectInputStream<T> inputStream, SortedSet<K> conditions) throws IOException {

        responseWriter.write(getTsvFileMasthead() + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions));

        long count = 0;
        T geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            String[] csvRow = buildCsvRow(geneProfile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();
        csvWriter.close();

        return count;
    }

    protected abstract String[] getConditionColumnHeaders(SortedSet<K> conditions);

    protected abstract String getTsvFileMasthead();

    protected abstract String[] getProfileIdColumnHeaders();

    protected String[] buildCsvColumnHeaders(SortedSet<K> factorValues) {
        String[] profileIdColumnHeaders = getProfileIdColumnHeaders();
        String[] conditionColumnHeaders = getConditionColumnHeaders(factorValues);
        return buildCsvRow(profileIdColumnHeaders, conditionColumnHeaders);
    }

    String[] buildCsvRow(final T geneProfile, SortedSet<K> factors) {
        String[] expressionLevels = extractConditionLevels(geneProfile, factors);

        String[] rowHeaders = getRowHeaders(geneProfile);
        return buildCsvRow(rowHeaders, expressionLevels);
    }

    String[] getRowHeaders(T geneProfile){
        String primaryRowHeader = geneNamesProvider.getGeneName(geneProfile.getId());
        String secondaryRowHeader = getSecondaryRowHeader(geneProfile);
        if  (secondaryRowHeader != null){
            return new String[]{primaryRowHeader, secondaryRowHeader};
        }
        return new String[]{primaryRowHeader};
    }

    protected abstract String getSecondaryRowHeader(T profile);

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
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER);
    }

    String removeTrailingZero(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

}