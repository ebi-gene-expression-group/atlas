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

package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.FilterParameters;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.RequestPreferences;

@Scope("request")
public class GeneProfilesController {

    private FilterParameters.Builder filtersParameterBuilder;
    private ExperimentsCache experimentsCache;
    private GeneExpressionPrecondition geneExpressionPrecondition;

    public GeneProfilesController(FilterParameters.Builder filtersParameterBuilder, ExperimentsCache experimentsCache,
                                  GeneExpressionPrecondition geneExpressionPrecondition) {
        this.filtersParameterBuilder = filtersParameterBuilder;
        this.experimentsCache = experimentsCache;
        this.geneExpressionPrecondition = geneExpressionPrecondition;
    }

    protected FilterParameters createFilterParameters(String experimentAccession, RequestPreferences preferences) {

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        return filtersParameterBuilder.forExperiment(experiment)
                .withFilterFactors(preferences.getSerializedFilterFactors())
                .withQueryFactorType(preferences.getQueryFactorType())
                .withQueryFactorValues(preferences.getQueryFactorValues())
                .withGeneQuery(preferences.getGeneQuery())
                .build();
    }

    protected void prepareGeneExpressionPrecondition(RequestPreferences preferences,
                                                    FilterParameters filterParameters) {
        geneExpressionPrecondition.setCutoff(preferences.getCutoff());
        geneExpressionPrecondition.setFilterFactors(filterParameters.getSelectedFilterFactors());
        String queryFactorType = preferences.getQueryFactorType();
        if (StringUtils.isBlank(queryFactorType)) {
            queryFactorType = filterParameters.getQueryFactorType();
        }
        geneExpressionPrecondition.setQueryFactorType(queryFactorType);
    }
}
