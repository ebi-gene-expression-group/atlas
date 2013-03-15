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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedSet;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named("streamGeneProfiles")
@Scope("prototype")
public class WriteGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private CSVWriter csvWriter;

    private NumberUtils numberUtils;
    private GeneNamesProvider geneNamesProvider;

    @Inject
    protected WriteGeneProfilesCommand(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        this.numberUtils = numberUtils;
        this.geneNamesProvider = geneNamesProvider;
    }

    @Override
    protected Long apply(BaselineRequestContext requestContext, ObjectInputStream<BaselineProfile> inputStream) throws IOException {
        long count = 0;
        SortedSet<String> factorValues = Factor.getValues(requestContext.getAllQueryFactors());

        csvWriter.writeNext(buildCsvHeaders(factorValues));

        BaselineProfile baselineProfile;
        while ((baselineProfile = inputStream.readNext()) != null) {
            ++count;
            csvWriter.writeNext(buildCsvRow(baselineProfile, requestContext.getAllQueryFactors()));
        }

        csvWriter.flush();
        csvWriter.close();

        return count;
    }

    protected String[] buildCsvHeaders(SortedSet<String> factorValues) {
        return buildCsvRow(new String[]{"Gene name", "Gene Id"}, factorValues.toArray(new String[factorValues.size()]));
    }

    protected String[] buildCsvRow(final BaselineProfile baselineProfile, SortedSet<Factor> factors) {
        String[] expressionLevels = new String[factors.size()];
        int i = 0;
        for (Factor factor : factors) {
            expressionLevels[i++] = numberUtils.removeTrailingZero(baselineProfile.getExpressionLevel(factor));
        }
        String geneId = baselineProfile.getGeneId();
        return buildCsvRow(new String[]{geneNamesProvider.getGeneName(geneId), geneId}, expressionLevels);
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {

        return ArrayUtils.addAll(rowHeaders, values);

    }

    public void setResponseWriter(PrintWriter responseWriter) {
        csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER);
    }

}