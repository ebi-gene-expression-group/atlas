/*
 * Copyright 2008-2016 Gene Expression Team, EMBL-European Bioinformatics Institute
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
 * Expression Atlas:
 * https://www.ebi.ac.uk/gxa
 *
 * For further details of the Expression Atlas project, including source code,
 * downloads and documentation, please see:
 * https://github.com/gxa/atlas
 */

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 04/03/2016.
 */

package uk.ac.ebi.atlas.experimentimport.coexpression;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MinMaxPriorityQueue;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

public class BaselineCoexpressionProfileInputStream implements ObjectInputStream<BaselineCoexpressionProfile> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionProfileInputStream.class);

    private static final int COEXPRESSION_PROFILE_SIZE = 100;

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_CE_STATISTIC_INDEX = 1;

    private final CSVReader csvReader;
    private final String fileName;
    private final String[] geneIDsHeader;
    private final Queue<BaselineCoexpressionProfile> queue = new LinkedList<>();
    private int lineNumber = 0;


    public BaselineCoexpressionProfileInputStream(CSVReader csvReader, String fileName) {
        this.fileName = fileName;
        this.csvReader = csvReader;
        this.geneIDsHeader = (String[]) ArrayUtils.remove(readCsvLine(), 0);
    }


    @Override
    public void close() throws IOException {
        csvReader.close();
    }


    private String[] readCsvLine() {
        lineNumber++;
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(String.format("%s exception thrown while reading line %s", fileName, lineNumber), e);
        }
    }

    @Override
    public BaselineCoexpressionProfile readNext() {
        if (queue.isEmpty()) {
            BaselineCoexpressionProfile baselineCoexpressionProfile = readNextNonZeroLine();

            if (baselineCoexpressionProfile == null) {
                //EOF
                return null;
            }

            queue.add(baselineCoexpressionProfile);
        }

        return queue.remove();
    }

    private BaselineCoexpressionProfile readNextNonZeroLine() {

        String[] line = readCsvLine();
        if (line == null) {
            // EOF
            return null;
        }

        String geneID = line[GENE_ID_COLUMN_INDEX];
        String[] baselineCoexpressionsLine = (String[]) ArrayUtils.subarray(line, FIRST_CE_STATISTIC_INDEX, line.length);
        MinMaxPriorityQueue<BaselineCoexpression> coexpressionProfile = createProfile(geneID, baselineCoexpressionsLine);

        if (coexpressionProfile.isEmpty()) {
            return readNextNonZeroLine();
        }

        ImmutableList.Builder<String> geneIDsBuilder = new ImmutableList.Builder<>();
        for (BaselineCoexpression baselineCoexpression : coexpressionProfile) {
            geneIDsBuilder.add(baselineCoexpression.ceGeneID());
        }
        return new BaselineCoexpressionProfile(geneID, coexpressionProfile);
    }


    private MinMaxPriorityQueue<BaselineCoexpression> createProfile(String geneID, String[] baselineCoexpressionsLine) {
        checkArgument(StringUtils.isNotBlank(geneID), "Cannot load baseline coexpressions profile - gene ID is blank");
        checkArgument(baselineCoexpressionsLine[lineNumber - 2].equals("0"), "Profile does not contain own gene ID in line " + lineNumber);
        checkArgument(
                baselineCoexpressionsLine.length == geneIDsHeader.length,
                String.format(
                        "Cannot load baseline coexpressions profile - expecting %d expressions but got %d instead.",
                        geneIDsHeader.length, baselineCoexpressionsLine.length));


        baselineCoexpressionsLine = (String[]) ArrayUtils.remove(baselineCoexpressionsLine, lineNumber - 2);

        MinMaxPriorityQueue<BaselineCoexpression> coexpressionProfile = MinMaxPriorityQueue.expectedSize(COEXPRESSION_PROFILE_SIZE).maximumSize(COEXPRESSION_PROFILE_SIZE).create();
        for (int i = 0; i < baselineCoexpressionsLine.length; i++) {
            String ceGeneID = geneIDsHeader[i];
            coexpressionProfile.add(
                    BaselineCoexpression.create(geneID, Double.parseDouble(baselineCoexpressionsLine[i]), ceGeneID));
        }

        return coexpressionProfile;
    }

}
