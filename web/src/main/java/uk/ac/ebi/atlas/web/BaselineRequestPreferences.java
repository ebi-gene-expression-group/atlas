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

package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;


public class BaselineRequestPreferences extends ExperimentPageRequestPreferences {

    public static final double DEFAULT_CUTOFF = 0.5d;
    private static final String DEFAULT_GENE_QUERY = "protein_coding";

    private BaselineExpressionLevelRounder baselineExpressionLevelRounder = new BaselineExpressionLevelRounder();

    @Override
    protected String getDefaultGeneQuery() {
        return DEFAULT_GENE_QUERY;
    }

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    @Override
    public void setCutoff(Double cutoff) {
        if (cutoff != null) {
            super.setCutoff(baselineExpressionLevelRounder.round(cutoff));
        } else {
            super.setCutoff(cutoff);
        }
    }

}
