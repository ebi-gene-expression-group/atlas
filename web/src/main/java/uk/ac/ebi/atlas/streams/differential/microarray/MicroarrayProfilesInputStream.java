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

package uk.ac.ebi.atlas.streams.differential.microarray;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile.MicroarrayProfileBuilder;
import uk.ac.ebi.atlas.streams.TsvInputStream;


public class MicroarrayProfilesInputStream extends TsvInputStream<MicroarrayProfile> {


    private MicroarrayProfileBuilder microarrayProfileBuilder;
    private final DesignElementMappingProvider designElementMappingProvider;
    private final String arrayDesignAccession;

    public MicroarrayProfilesInputStream(CSVReader csvReader,
                                         String experimentAccession,
                                         MicroarrayExpressionsBufferBuilder expressionsBufferBuilder,
                                         MicroarrayProfileBuilder microarrayProfileBuilder,
                                         DesignElementMappingProvider designElementMappingProvider,
                                         String arrayDesignAccession) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.microarrayProfileBuilder = microarrayProfileBuilder;
        this.designElementMappingProvider = designElementMappingProvider;
        this.arrayDesignAccession = arrayDesignAccession;
    }

    @Override
    protected MicroarrayProfile buildObjectFromTsvValues(String[] values) {

        String designElementName = values[GENE_ID_COLUMN];

        String geneId = designElementMappingProvider.getEnsGeneId(arrayDesignAccession,designElementName);

        if(StringUtils.isBlank(geneId)){
            return null;
        }

        microarrayProfileBuilder.forGeneId(geneId).withDesignElementName(designElementName);

        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowBuffer().reload(ArrayUtils.remove(values, GENE_ID_COLUMN));

        DifferentialExpression expression;

        while ((expression = (MicroarrayExpression)getTsvRowBuffer().poll()) != null) {

            microarrayProfileBuilder.addExpression(expression);

        }

        return microarrayProfileBuilder.create();

    }
}
