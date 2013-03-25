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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.impl.DifferentialRequestContextImpl;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialRequestContextBuilder {

    private DifferentialRequestContextImpl requestContext;

    private DifferentialExperiment experiment;

    protected DifferentialRequestPreferences preferences;

    @Inject
    public DifferentialRequestContextBuilder(DifferentialRequestContextImpl requestContext) {
        this.requestContext = requestContext;
    }

    public DifferentialRequestContextBuilder forExperiment(DifferentialExperiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public DifferentialRequestContextBuilder withPreferences(DifferentialRequestPreferences preferences) {
        this.preferences = preferences;
        return this;
    }

    public DifferentialRequestContext build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setRequestPreferences(preferences);

        requestContext.setSelectedQueryFactors(getSelectedQueryContrasts());

        requestContext.setRegulation(preferences.getRegulation());

        requestContext.setFilteredBySpecies(experiment.getFirstSpecies());

        requestContext.setAllQueryFactors(experiment.getContrasts());

        return requestContext;
    }

    Set<Contrast> getSelectedQueryContrasts() {
        if(CollectionUtils.isEmpty(preferences.getQueryFactorValues())){
            return Sets.newHashSet();
        }

        Set<Contrast> selectedQueryContrasts = Sets.newHashSet();
        for (String queryContrastId : preferences.getQueryFactorValues()){
            selectedQueryContrasts.add(experiment.getContrast(queryContrastId));
        }
        return selectedQueryContrasts;
    }

}