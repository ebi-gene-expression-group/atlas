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

package uk.ac.ebi.atlas.streams.differential;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfileBuilder;
import uk.ac.ebi.atlas.streams.TsvInputStream;

public class RnaSeqProfilesInputStream extends TsvInputStream<RnaSeqProfile> {


    private RnaSeqProfileBuilder rnaSeqProfileBuilder;

    public RnaSeqProfilesInputStream(CSVReader csvReader, String experimentAccession
            , RnaSeqExpressionsBufferBuilder expressionsBufferBuilder
            , RnaSeqProfileBuilder rnaSeqProfileBuilder) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.rnaSeqProfileBuilder = rnaSeqProfileBuilder;
    }

    @Override
    protected RnaSeqProfile buildObjectFromTsvValues(String[] values) {

        rnaSeqProfileBuilder.forGeneId(values[GENE_ID_COLUMN]);

        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowBuffer().reload(ArrayUtils.remove(values, GENE_ID_COLUMN));

        DifferentialExpression expression;

        while ((expression = (DifferentialExpression) getTsvRowBuffer().poll()) != null) {

            rnaSeqProfileBuilder.addExpression(expression);

        }

        return rnaSeqProfileBuilder.create();

    }
}
