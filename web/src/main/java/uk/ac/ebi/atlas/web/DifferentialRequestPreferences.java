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


import uk.ac.ebi.atlas.model.differential.Regulation;

import javax.validation.constraints.Min;

public class DifferentialRequestPreferences extends ExperimentPageRequestPreferences {

    public static final double DEFAULT_CUTOFF = 0.05d;

    public static final double DEFAULT_FOLD_CHANGE_CUTOFF = 1d;

    private Regulation regulation = Regulation.UP_DOWN;

    @Min(value = 0, message = "Log2-fold change cut off is an absolute amount, and so must be greater than zero")
    private double foldChangeCutOff = DEFAULT_FOLD_CHANGE_CUTOFF;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    // exposed as JavaBean getter so JSP can read it
    public double getDefaultFoldChangeCutOff() {
        return DEFAULT_FOLD_CHANGE_CUTOFF;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }

    public Double getFoldChangeCutOff() {
        return foldChangeCutOff;
    }

    public void setFoldChangeCutOff(Double foldChangeCutOff) {
        // handle no value case, eg: when textbox is left empty
        if (foldChangeCutOff != null) {
            this.foldChangeCutOff = foldChangeCutOff;
        }
    }
}
