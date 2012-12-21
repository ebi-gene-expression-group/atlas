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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import static java.lang.System.arraycopy;

@Named("streamGeneProfiles")
@Scope("prototype")
public class WriteGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private CSVWriter csvWriter;

    private NumberUtils numberUtils;

    @Inject
    protected WriteGeneProfilesCommand(NumberUtils numberUtils){
        this.numberUtils = numberUtils;
    }

    @Override
    protected Long apply(RequestPreferences requestPreferences, Experiment experiment
            , ObjectInputStream<GeneProfile> inputStream) throws IOException {

        long count = 0;

        SortedSet<String> organismParts = experiment.getAllExperimentalFactors();

        csvWriter.writeNext(buildCsvHeaders(organismParts));

        GeneProfile geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            csvWriter.writeNext(buildCsvRow(geneProfile, organismParts));
        }
        return count;
    }

    @Override
    protected Long returnEmpty() {
        return 0L;
    }

    protected String[] buildCsvHeaders(Set<String> organismParts) {
        return buildCsvRow(new String[]{"Gene name", "Gene Id"}, organismParts.toArray(new String[organismParts.size()]));
    }

    protected String[] buildCsvRow(final GeneProfile geneProfile, SortedSet<String> organismParts) {
        String[] expressionLevels = new String[organismParts.size()];
        int i = 0;
        for (String organismPart : organismParts) {
            expressionLevels[i++] = numberUtils.removeTrailingZero(geneProfile.getExpressionLevel(organismPart));
        }
        return buildCsvRow(new String[]{geneProfile.getGeneName(), geneProfile.getGeneId()}, expressionLevels);
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {

        int rowHeadersCount = rowHeaders.length;
        String[] csvRow = new String[rowHeadersCount + values.length];

        arraycopy(rowHeaders, 0, csvRow, 0, rowHeadersCount);
        arraycopy(values, 0, csvRow, 0 + rowHeadersCount, values.length);
        return csvRow;
    }


    public void setCsvWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

}