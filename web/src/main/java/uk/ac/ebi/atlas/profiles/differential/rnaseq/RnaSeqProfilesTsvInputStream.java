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

package uk.ac.ebi.atlas.profiles.differential.rnaseq;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.TsvInputStream;

public class RnaSeqProfilesTsvInputStream extends TsvInputStream<RnaSeqProfile, DifferentialExpression> {

    private RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder;

    public RnaSeqProfilesTsvInputStream(CSVReader csvReader, String experimentAccession
            , ExpressionsRowDeserializerRnaSeqBuilder expressionsRowDeserializerRnaSeqBuilder
            , RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder) {

        super(csvReader, experimentAccession, expressionsRowDeserializerRnaSeqBuilder);
        this.rnaSeqProfileReusableBuilder = rnaSeqProfileReusableBuilder;
    }

    @Override
    public RnaSeqProfile createProfile() {
        RnaSeqProfile profile = rnaSeqProfileReusableBuilder.create();
        return profile.isEmpty() ? null : profile;
    }

    @Override
    public void addExpressionToBuilder(DifferentialExpression expression) {
        rnaSeqProfileReusableBuilder.addExpression(expression);
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
        rnaSeqProfileReusableBuilder.beginNewInstance(values[0], values[1]);
    }


}
