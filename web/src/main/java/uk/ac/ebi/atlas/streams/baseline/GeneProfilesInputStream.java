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

package uk.ac.ebi.atlas.streams.baseline;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;
import uk.ac.ebi.atlas.streams.TsvInputStream;

import static uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsBuffer.GENE_ID_COLUMN;

public class GeneProfilesInputStream extends TsvInputStream<GeneProfile> {

    private GeneProfile.Builder geneProfileBuilder;


    public GeneProfilesInputStream(CSVReader csvReader, String experimentAccession
            , BaselineExpressionsBuffer.Builder expressionsBufferBuilder
            , GeneProfile.Builder geneProfileBuilder) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.geneProfileBuilder = geneProfileBuilder;
    }

    protected GeneProfile buildObjectFromTsvValues(String[] values) {

        geneProfileBuilder.forGeneId(values[GENE_ID_COLUMN]);

        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowBuffer().reload(values);

        BaselineExpression expression;

        while ((expression = (BaselineExpression)getTsvRowBuffer().poll()) != null) {

            geneProfileBuilder.addExpression(expression);
        }

        return geneProfileBuilder.create();

    }

}
