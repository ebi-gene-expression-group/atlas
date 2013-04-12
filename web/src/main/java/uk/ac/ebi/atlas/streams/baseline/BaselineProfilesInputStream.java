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

package uk.ac.ebi.atlas.streams.baseline;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileBuilder;
import uk.ac.ebi.atlas.streams.TsvInputStream;

public class BaselineProfilesInputStream extends TsvInputStream<BaselineProfile> {

    private BaselineProfileBuilder baselineProfileBuilder;


    public BaselineProfilesInputStream(CSVReader csvReader, String experimentAccession
            , BaselineExpressionsBufferBuilder expressionsBufferBuilder
            , BaselineProfileBuilder baselineProfileBuilder) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.baselineProfileBuilder = baselineProfileBuilder;
    }

    protected BaselineProfile buildObjectFromTsvValues(String[] values) {

        baselineProfileBuilder.forGeneId(values[GENE_ID_COLUMN]);

        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowBuffer().reload(ArrayUtils.remove(values, GENE_ID_COLUMN));

        BaselineExpression expression;

        while ((expression = (BaselineExpression) getTsvRowBuffer().poll()) != null) {

            baselineProfileBuilder.addExpression(expression);
        }

        return baselineProfileBuilder.create();

    }

}
