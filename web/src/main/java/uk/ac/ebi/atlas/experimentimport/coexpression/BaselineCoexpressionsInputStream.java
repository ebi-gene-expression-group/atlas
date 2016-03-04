/*
 * Copyright 2008-2016 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MinMaxPriorityQueue;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Reads TSV input of:
 *
 * \t g1   g2   g3   g4   g5
 * g1 0    0.12 0.54 0.87 0
 * g2 0.12 0    ...
 *
 * and returns BaselineCoexpression of:
 *
 * g1, g2, 0.12
 * g1, g3, 0.54
 * g1, g4, 0.87
 * ...
 * g2, g1, 0.12
 * ...
 */
public class BaselineCoexpressionsInputStream implements ObjectInputStream<BaselineCoexpression> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionsInputStream.class);

    private static final int COEXPRESSION_PROFILE_SIZE = 100;

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_CE_STATISTIC_INDEX = 1;

    private final CSVReader csvReader;
    private final String fileName;
    private final String[] geneIDsHeader;
    private final Queue<BaselineCoexpression> queue = new LinkedList<>();
    private int lineNumber = 0;


    public BaselineCoexpressionsInputStream(CSVReader csvReader, String fileName) {
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
    public BaselineCoexpression readNext() {
        if (queue.isEmpty()) {
            ImmutableList<BaselineCoexpression> baselineCoexpressions = readNextNonZeroLine();

            if (baselineCoexpressions == null) {
                //EOF
                return null;
            }

            queue.addAll(baselineCoexpressions);
        }

        return queue.remove();
    }


    private ImmutableList<BaselineCoexpression> readNextNonZeroLine() {

        String[] line = readCsvLine();
        if (line == null) {
            // EOF
            return null;
        }

        String geneID = line[GENE_ID_COLUMN_INDEX];
        String[] baselineCoexpressionsLine = (String[]) ArrayUtils.subarray(line, FIRST_CE_STATISTIC_INDEX, line.length);
        ImmutableList<BaselineCoexpression> baselineCoexpressions = createList(geneID, baselineCoexpressionsLine);

        if (baselineCoexpressions.isEmpty()) {
            return readNextNonZeroLine();
        }

        return baselineCoexpressions;
    }

    private ImmutableList<BaselineCoexpression> createList(String geneID, String[] baselineCoexpressionsLine) {
        checkArgument(StringUtils.isNotBlank(geneID), "Cannot load baseline coexpressions - gene ID is blank");
        checkArgument(
                baselineCoexpressionsLine.length == geneIDsHeader.length,
                String.format(
                        "Cannot load baseline coexpressions - expecting [%s] expressions but got [%s] instead.",
                        Joiner.on(", ").join(geneIDsHeader), Joiner.on(", ").join(baselineCoexpressionsLine)));

        ImmutableList.Builder<BaselineCoexpression> builder = ImmutableList.builder();

        for (int i = 0; i < baselineCoexpressionsLine.length; i++) {
            String ceGeneID = geneIDsHeader[i];
            builder.add(
                    BaselineCoexpression.create(
                            geneID, Double.parseDouble(baselineCoexpressionsLine[i]), ceGeneID));
        }

        return builder.build();
    }

}
