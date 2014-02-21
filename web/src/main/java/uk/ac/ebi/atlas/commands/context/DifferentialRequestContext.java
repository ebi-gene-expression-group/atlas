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

package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class DifferentialRequestContext<T extends DifferentialExperiment> extends RequestContext<Contrast, DifferentialRequestPreferences> implements DifferentialProfileStreamOptions {

    private T experiment;

    public T getExperiment() {
        return experiment;
    }

    void setExperiment(T experiment) {
        this.experiment = experiment;
    }

    public Regulation getRegulation() {
        return getRequestPreferences().getRegulation();
    }

    @Override
    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    @Override
    protected void setRequestPreferences(DifferentialRequestPreferences requestPreferences) {
        super.setRequestPreferences(requestPreferences);
    }

    @Override
    protected DifferentialRequestPreferences getRequestPreferences() {
        return super.getRequestPreferences();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("regulation", getRegulation())
                .add("experiment", getExperiment())
                .toString();
    }

}
