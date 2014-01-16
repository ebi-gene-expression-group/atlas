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

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import java.util.Set;
import java.util.SortedSet;

public class DifferentialRequestContextBuilder<T extends DifferentialRequestContext, K extends DifferentialRequestPreferences> {
    private T requestContext;
    private K requestPreferences;
    private DifferentialExperiment experiment;

    public DifferentialRequestContextBuilder(T requestContext) {
        this.requestContext = requestContext;
    }

    public DifferentialRequestContextBuilder<T, K> forExperiment(DifferentialExperiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public DifferentialRequestContextBuilder<T, K> withPreferences(K requestPreferences) {
        this.requestPreferences = requestPreferences;
        return this;
    }

    public T build() {

        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        getRequestContext().setRequestPreferences(getRequestPreferences());

        getRequestContext().setSelectedQueryFactors(getSelectedQueryContrasts(experiment));

        getRequestContext().setFilteredBySpecies(experiment.getFirstSpecies().toLowerCase());

        getRequestContext().setAllQueryFactors(experiment.getContrasts());

        getRequestContext().setExperiment(experiment);

        return getRequestContext();
    }

    Set<Contrast> getSelectedQueryContrasts(DifferentialExperiment experiment) {
        if (CollectionUtils.isEmpty(getRequestPreferences().getQueryFactorValues())) {
            return Sets.newHashSet();
        }

        SortedSet<Contrast> selectedQueryContrasts = Sets.newTreeSet();
        for (String queryContrastId : getRequestPreferences().getQueryFactorValues()) {

            try {
                Contrast contrast = experiment.getContrast(queryContrastId);
                selectedQueryContrasts.add(contrast);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException(e);
            }

        }
        return selectedQueryContrasts;
    }

    protected T getRequestContext() {
        return requestContext;
    }

    protected DifferentialRequestPreferences getRequestPreferences() {
        return requestPreferences;
    }
}
