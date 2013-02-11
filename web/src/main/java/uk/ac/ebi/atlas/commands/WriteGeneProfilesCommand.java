/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.SortedSet;

@Named("streamGeneProfiles")
@Scope("prototype")
public class WriteGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private CSVWriter csvWriter;

    private NumberUtils numberUtils;

    @Inject
    protected WriteGeneProfilesCommand(NumberUtils numberUtils) {
        this.numberUtils = numberUtils;
    }

    @Override
    protected Long apply(Experiment experiment
            , ObjectInputStream<GeneProfile> inputStream) throws IOException {

        long count = 0;

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        SortedSet<Factor> factors = experimentalFactors.getFactorsByType(getFilterParameters().getQueryFactorType());

        SortedSet<String> factorValues = Factor.getValues(factors);
        csvWriter.writeNext(buildCsvHeaders(factorValues));

        GeneProfile geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            csvWriter.writeNext(buildCsvRow(geneProfile, factors));
        }
        return count;
    }

    @Override
    protected Long returnEmpty() {
        return 0L;
    }

    protected String[] buildCsvHeaders(SortedSet<String> factorValues) {
        return buildCsvRow(new String[]{"Gene name", "Gene Id"}, factorValues.toArray(new String[factorValues.size()]));
    }

    protected String[] buildCsvRow(final GeneProfile geneProfile, SortedSet<Factor> factors) {
        String[] expressionLevels = new String[factors.size()];
        int i = 0;
        for (Factor factor : factors) {
            expressionLevels[i++] = numberUtils.removeTrailingZero(geneProfile.getExpressionLevel(factor));
        }
        return buildCsvRow(new String[]{geneProfile.getGeneName(), geneProfile.getGeneId()}, expressionLevels);
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {

        return ArrayUtils.addAll(rowHeaders, values);

    }


    public void setCsvWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

}