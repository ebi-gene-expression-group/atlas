/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import uk.ac.ebi.atlas.commands.SessionContext;
import uk.ac.ebi.atlas.commands.SessionContextBuilder;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.RequestPreferences;

@Scope("request")
public class GeneProfilesController {

    private SessionContextBuilder sessionContextBuilder;
    private ExperimentsCache experimentsCache;
    private GeneExpressionPrecondition geneExpressionPrecondition;

    public GeneProfilesController(SessionContextBuilder sessionContextBuilder, ExperimentsCache experimentsCache,
                                  GeneExpressionPrecondition geneExpressionPrecondition) {
        this.sessionContextBuilder = sessionContextBuilder;
        this.experimentsCache = experimentsCache;
        this.geneExpressionPrecondition = geneExpressionPrecondition;
    }

    protected SessionContext updateSessionContext(String experimentAccession, RequestPreferences preferences) {

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        return sessionContextBuilder.forExperiment(experiment)
                .withFilterFactors(preferences.getSerializedFilterFactors())
                .withQueryFactorType(preferences.getQueryFactorType())
                .withQueryFactorValues(preferences.getQueryFactorValues())
                .withGeneQuery(preferences.getGeneQuery())
                .build();
    }

    protected void prepareGeneExpressionPrecondition(String experimentAccession, RequestPreferences preferences,
                                                    SessionContext sessionContext) {
        geneExpressionPrecondition.setCutoff(preferences.getCutoff());
        geneExpressionPrecondition.setFilterFactors(sessionContext.getSelectedFilterFactors());
        String queryFactorType = preferences.getQueryFactorType();
        if (StringUtils.isBlank(queryFactorType)) {
            queryFactorType = sessionContext.getQueryFactorType();
        }
        geneExpressionPrecondition.setQueryFactorType(queryFactorType);
        geneExpressionPrecondition.setSelectedQueryFactors(sessionContext.getSelectedQueryFactors());
        geneExpressionPrecondition.setSpecific(preferences.isSpecific());
        geneExpressionPrecondition.setExperimentalFactors(experimentsCache.getExperiment(experimentAccession).getExperimentalFactors().getFilteredFactors(sessionContext.getSelectedFilterFactors()));
    }
}
