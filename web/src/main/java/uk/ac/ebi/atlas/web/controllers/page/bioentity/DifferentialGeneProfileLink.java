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

package uk.ac.ebi.atlas.web.controllers.page.bioentity;

import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;

public class DifferentialGeneProfileLink {

    private String geneQuery;

    private Contrast contrast;

    private String experimentAccession;

    private DifferentialExpression expression;

    public DifferentialGeneProfileLink(String geneQuery, Contrast contrast, String experimentAccession, DifferentialExpression expression) {
        this.geneQuery = geneQuery;
        this.contrast = contrast;
        this.experimentAccession = experimentAccession;
        this.expression = expression;
    }

    public String getContrastDisplayName() {
        return expression.getContrast().getDisplayName();
    }

    public String getContrastId() {
        return expression.getContrast().getId();
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getUrl() {
        return experimentAccession + "?geneQuery=" + geneQuery + "&queryFactorValues=" + contrast.getId();
    }

    public DifferentialExpression getExpression() {
        return expression;
    }

    public double getValue() {
        return expression.getLevel();
    }
}