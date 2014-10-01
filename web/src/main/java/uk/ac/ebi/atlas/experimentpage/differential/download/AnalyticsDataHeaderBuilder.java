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

package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
class AnalyticsDataHeaderBuilder {
    private DifferentialExperiment experiment;

    public String[] buildHeader(String[] header) {
        checkNotNull(experiment, "Experiment should be not null!");

        List<String> result = new ArrayList<>();
        for (int i = 0; i < getFixedColumnNumber(); i++) {
            result.add(header[i]);
        }

        for (int i = getFixedColumnNumber(); i < header.length; i++) {
            result.add(replaceContrastIdWithName(header[i]));
        }

        return result.toArray(new String[result.size()]);
    }

    protected String replaceContrastIdWithName(String columnHeader) {
        String contrastId = StringUtils.substringBefore(columnHeader, ".");
        String displayName = experiment.getContrast(contrastId).getDisplayName();
        return StringUtils.replace(columnHeader, contrastId, displayName);
    }

    public void setExperiment(DifferentialExperiment experiment) {
        this.experiment = experiment;
    }

    protected int getFixedColumnNumber() {
        return 2;
    }
}
